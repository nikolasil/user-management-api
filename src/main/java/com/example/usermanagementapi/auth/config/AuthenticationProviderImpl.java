package com.example.usermanagementapi.auth.config;

import com.example.usermanagementapi.auth.service.UserService;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationProviderImpl extends DaoAuthenticationProvider {

    public AuthenticationProviderImpl(UserService userService, PasswordEncoderImpl passwordEncoder) {
        setUserDetailsService(userService);
        setPasswordEncoder(passwordEncoder);
    }
}
