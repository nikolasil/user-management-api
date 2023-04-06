package com.example.usermanagementapi.auth.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@Getter
@Setter
@ConfigurationProperties(prefix = "auth")
@ConfigurationPropertiesScan
public class AuthProperties {
    private String SecretKey;
    private Long DurationOfToken;
}
