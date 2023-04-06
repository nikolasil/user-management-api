package com.example.usermanagementapi.auth.service.impl;

import com.example.usermanagementapi.auth.config.PasswordEncoderImpl;
import com.example.usermanagementapi.auth.domain.Token;
import com.example.usermanagementapi.auth.domain.User;
import com.example.usermanagementapi.auth.dto.AuthResponseDto;
import com.example.usermanagementapi.auth.dto.UserInputLoginDto;
import com.example.usermanagementapi.auth.dto.UserInputRegisterDto;
import com.example.usermanagementapi.auth.enums.Role;
import com.example.usermanagementapi.auth.service.AuthService;
import com.example.usermanagementapi.auth.service.TokenService;
import com.example.usermanagementapi.auth.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final TokenService tokenService;
    private final PasswordEncoderImpl passwordEncoder;
    private final AuthenticationConfiguration authenticationConfiguration;


    @Override
    public AuthResponseDto register(UserInputRegisterDto request) {
        try {
            userService.loadUserByUsername(request.getUsername());
        } catch (UsernameNotFoundException e) {
            User user = User.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .accountNonLocked(true)
                    .isEnabled(true)
                    .roles(Set.of(Role.ADMIN))
                    .build();
            User savedUser = userService.save(user);
            String jwtToken = tokenService.generateToken(user);
            Token userToken = tokenService.saveUserToken(savedUser, jwtToken);
            return AuthResponseDto.builder()
                    .token(userToken.getJwt())
                    .build();
        }
        return AuthResponseDto.builder()
                .token("")
                .build();
    }

    @Override
    public AuthResponseDto login(UserInputLoginDto request) throws Exception {
        authenticationConfiguration.getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        User user = (User) userService.loadUserByUsername(request.getUsername());
        String jwtToken = tokenService.generateToken(user);
        tokenService.revokeAllUserTokens(user);
        Token userToken = tokenService.saveUserToken(user, jwtToken);
        return AuthResponseDto.builder()
                .token(userToken.getJwt())
                .build();
    }

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7);
        Token storedToken = tokenService.findByJwt(jwt)
                .orElse(null);
        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenService.save(storedToken);
            SecurityContextHolder.clearContext();
        }
    }
}
