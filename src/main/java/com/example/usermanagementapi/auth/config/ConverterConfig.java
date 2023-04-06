package com.example.usermanagementapi.auth.config;

import com.example.usermanagementapi.auth.converter.UserToUserOutputDtoConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConverterConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new UserToUserOutputDtoConverter());
    }
}