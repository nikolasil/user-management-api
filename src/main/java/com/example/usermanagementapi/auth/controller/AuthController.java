package com.example.usermanagementapi.auth.controller;

import com.example.usermanagementapi.auth.dto.AuthResponseDto;
import com.example.usermanagementapi.auth.dto.UserInputLoginDto;
import com.example.usermanagementapi.auth.dto.UserInputRegisterDto;
import com.example.usermanagementapi.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(
            @RequestBody UserInputRegisterDto request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(
            @RequestBody UserInputLoginDto request
    ) throws Exception {
        return ResponseEntity.ok(authService.login(request));
    }
}
