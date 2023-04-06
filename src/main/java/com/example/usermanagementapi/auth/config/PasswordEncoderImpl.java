package com.example.usermanagementapi.auth.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class PasswordEncoderImpl extends BCryptPasswordEncoder {
    public PasswordEncoderImpl() {
    }

    public PasswordEncoderImpl(int strength) {
        super(strength);
    }

    public PasswordEncoderImpl(BCryptVersion version) {
        super(version);
    }

    public PasswordEncoderImpl(BCryptVersion version, SecureRandom random) {
        super(version, random);
    }

    public PasswordEncoderImpl(int strength, SecureRandom random) {
        super(strength, random);
    }

    public PasswordEncoderImpl(BCryptVersion version, int strength) {
        super(version, strength);
    }

    public PasswordEncoderImpl(BCryptVersion version, int strength, SecureRandom random) {
        super(version, strength, random);
    }
}
