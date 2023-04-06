package com.example.usermanagementapi.auth.converter;

import com.example.usermanagementapi.auth.domain.User;
import com.example.usermanagementapi.auth.dto.UserOutputDto;
import org.springframework.core.convert.converter.Converter;

public class UserToUserOutputDtoConverter implements Converter<User, UserOutputDto> {
    @Override
    public UserOutputDto convert(User source) {
        return new UserOutputDto(source.getUsername(), source.getRoles());
    }
}
