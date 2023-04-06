package com.example.usermanagementapi.auth.service.impl;

import com.example.usermanagementapi.auth.domain.Token;
import com.example.usermanagementapi.auth.domain.User;
import com.example.usermanagementapi.auth.enums.TokenType;
import com.example.usermanagementapi.auth.properties.AuthProperties;
import com.example.usermanagementapi.auth.repository.TokenRepository;
import com.example.usermanagementapi.auth.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final AuthProperties authProperties;
    private final TokenRepository tokenRepository;

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    @Override
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + authProperties.getDurationOfToken()))
                .signWith(getSignInKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    @Override
    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    @Override
    public Optional<Token> findByJwt(String jwt) {
        return tokenRepository.findByJwt(jwt);
    }

    @Override
    public void save(Token token) {
        tokenRepository.save(token);
    }

    @Override
    public List<Token> revokeAllUserTokens(User user) {
        List<Token> validTokens = tokenRepository.findByUser(user).stream().filter(token -> !token.expired && !token.revoked).toList();
        validTokens.forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
        });
        return tokenRepository.saveAll(validTokens);
    }

    @Override
    public Token saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .jwt(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        return tokenRepository.save(token);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(authProperties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
