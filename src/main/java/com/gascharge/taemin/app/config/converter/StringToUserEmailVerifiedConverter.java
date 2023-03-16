package com.gascharge.taemin.app.config.converter;


import com.gascharge.taemin.domain.enums.user.UserEmailVerified;
import org.springframework.core.convert.converter.Converter;

public class StringToUserEmailVerifiedConverter implements Converter<String, UserEmailVerified> {
    @Override
    public UserEmailVerified convert(String source) {
        return UserEmailVerified.valueOf(source.toUpperCase());
    }
}
