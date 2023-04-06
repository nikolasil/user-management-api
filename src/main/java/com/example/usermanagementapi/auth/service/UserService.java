package com.example.usermanagementapi.auth.service;

import com.example.usermanagementapi.auth.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User save(User user);
}
