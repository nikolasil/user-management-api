package com.example.usermanagementapi.auth.service;

import com.example.usermanagementapi.auth.dto.AuthResponseDto;
import com.example.usermanagementapi.auth.dto.UserInputLoginDto;
import com.example.usermanagementapi.auth.dto.UserInputRegisterDto;
import org.springframework.security.web.authentication.logout.LogoutHandler;

public interface AuthService extends LogoutHandler {
    AuthResponseDto register(UserInputRegisterDto request);

    AuthResponseDto login(UserInputLoginDto request) throws Exception;
}
