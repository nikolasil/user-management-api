package com.example.usermanagementapi.auth.repository;

import com.example.usermanagementapi.auth.domain.Token;
import com.example.usermanagementapi.auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    List<Token> findByUser(User user);

    Optional<Token> findByJwt(String token);
}