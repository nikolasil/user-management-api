package com.example.usermanagementapi.auth.service;

import com.example.usermanagementapi.auth.domain.Token;
import com.example.usermanagementapi.auth.domain.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public interface TokenService {
    String extractUsername(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    String generateToken(UserDetails userDetails);

    String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    );

    Boolean isTokenValid(String token, UserDetails userDetails);

    Optional<Token> findByJwt(String jwt);

    void save(Token token);

    List<Token> revokeAllUserTokens(User user);

    Token saveUserToken(User user, String jwtToken);
}
