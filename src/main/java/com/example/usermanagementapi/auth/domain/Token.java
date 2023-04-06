package com.example.usermanagementapi.auth.domain;

import com.example.usermanagementapi.auth.enums.TokenType;
import com.example.usermanagementapi.general.domain.BaseTableImpl;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token extends BaseTableImpl {

    @Column(unique = true)
    public String jwt;

    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;

    public boolean revoked;

    public boolean expired;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;
}
