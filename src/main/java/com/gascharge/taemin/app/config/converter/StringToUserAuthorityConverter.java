package com.gascharge.taemin.app.config.converter;

import com.gascharge.taemin.domain.enums.user.UserAuthority;
import org.springframework.core.convert.converter.Converter;

public class StringToUserAuthorityConverter implements Converter<String, UserAuthority> {
    @Override
    public UserAuthority convert(String source) {
        return UserAuthority.valueOf(source.toUpperCase());
    }
}
