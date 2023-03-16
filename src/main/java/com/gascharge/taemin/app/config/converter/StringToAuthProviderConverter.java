package com.gascharge.taemin.app.config.converter;

import com.gascharge.taemin.common.oauth.AuthProvider;
import org.springframework.core.convert.converter.Converter;

public class StringToAuthProviderConverter implements Converter<String, AuthProvider> {
    @Override
    public AuthProvider convert(String source) {
        return AuthProvider.valueOf(source.toLowerCase());
    }
}
