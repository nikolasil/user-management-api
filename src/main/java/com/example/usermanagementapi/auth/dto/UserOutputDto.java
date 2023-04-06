package com.example.usermanagementapi.auth.dto;

import com.example.usermanagementapi.auth.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserOutputDto {
    private String username;
    private Set<Role> roles;
}
