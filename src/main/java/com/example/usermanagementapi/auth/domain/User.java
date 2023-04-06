package com.example.usermanagementapi.auth.domain;

import com.example.usermanagementapi.auth.enums.Role;
import com.example.usermanagementapi.general.domain.BaseTableImpl;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@Entity
@Table(schema = "public", name = "users")
public class User extends BaseTableImpl implements UserDetails {
    private String username;
    private String password;
    private Boolean accountNonLocked;
    private Boolean isEnabled;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public User(Long id, Timestamp dateUpdated, Timestamp dateCreation, String username, String password, Boolean accountNonLocked, Boolean isEnabled, Set<Role> roles) {
        super(id, dateUpdated, dateCreation);
        this.username = username;
        this.password = password;
        this.accountNonLocked = accountNonLocked;
        this.isEnabled = isEnabled;
        this.roles = roles;
    }

    public User(String username, String password, Boolean accountNonLocked, Boolean isEnabled, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.accountNonLocked = accountNonLocked;
        this.isEnabled = isEnabled;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.name())).collect(Collectors.toList());
    }

    public Boolean hasRole(Role role) {
        return roles.contains(role);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
