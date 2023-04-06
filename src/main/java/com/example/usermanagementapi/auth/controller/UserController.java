package com.example.usermanagementapi.auth.controller;

import com.example.usermanagementapi.auth.dto.UserOutputDto;
import com.example.usermanagementapi.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ConversionService conversionService;

    @GetMapping("/me")
    public ResponseEntity<UserOutputDto> me() {
        return ResponseEntity.ok(
//                conversionService.convert(
//                    userService.loadUserByUsername(authentication.getName()),
//                    UserOutputDto.class
//                )
                new UserOutputDto()
        );
    }
}
