package com.example.usermanagementapi.auth.service

import com.example.usermanagementapi.auth.config.PasswordEncoderImpl
import com.example.usermanagementapi.auth.domain.Token
import com.example.usermanagementapi.auth.dto.AuthResponseDto
import com.example.usermanagementapi.auth.dto.UserInputLoginDto
import com.example.usermanagementapi.auth.dto.UserInputRegisterDto
import com.example.usermanagementapi.auth.service.AuthService
import com.example.usermanagementapi.auth.service.TokenService
import com.example.usermanagementapi.auth.service.UserService
import com.example.usermanagementapi.auth.service.impl.AuthServiceImpl
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

class AuthServiceTest extends Specification {
    AuthService authService;
    UserService userService;
    TokenService tokenService;
    PasswordEncoderImpl passwordEncoder;
    AuthenticationConfiguration authenticationConfiguration;
    
    def "setup"(){
        userService = Mock(UserService)
        tokenService = Mock(TokenService)
        passwordEncoder = Mock(PasswordEncoderImpl)
        authenticationConfiguration = Mock(AuthenticationConfiguration)
        authService = new AuthServiceImpl(
                userService,
                tokenService,
                passwordEncoder,
                authenticationConfiguration
        )
    }

    def "Register"() {
        given:
        UserInputRegisterDto request = new UserInputRegisterDto("username","password")
        tokenService.saveUserToken(_,_) >> Token.builder().jwt("tokeeeeen").build()
        when:
        AuthResponseDto authResponseDto = authService.register(request)
        then:
        authResponseDto.token != null
        authResponseDto.token == "tokeeeeen"
        noExceptionThrown()
    }

    def "Login"() {
        given:
        UserInputLoginDto request = new UserInputLoginDto("username","password")
        tokenService.saveUserToken(_,_) >> Token.builder().jwt("tokeeeeen").build()
        authenticationConfiguration.getAuthenticationManager() >> new AuthenticationManager() {
            @Override
            Authentication authenticate(Authentication authentication) throws AuthenticationException {
                return null
            }
        }
        when:
        AuthResponseDto authResponseDto = authService.login(request)
        then:
        authResponseDto.token != null
        authResponseDto.token == "tokeeeeen"
        noExceptionThrown()
    }

    def "Logout"() {
        given:
        HttpServletRequest request = Mock(HttpServletRequest)
        request.getHeader("Authorization") >> authHeader
        HttpServletResponse response = Mock(HttpServletResponse)
        Authentication authentication = Mock(Authentication)

        tokenService.findByJwt("authHeader") >> storedToken
        when:
        authService.logout(request, response, authentication)
        then:
        noExceptionThrown()
        where:
        authHeader << [null, "Bearer authHeader"]
        storedToken << [null, Optional.of(Token.builder().jwt("tokeeeeen").build())]
    }
}
