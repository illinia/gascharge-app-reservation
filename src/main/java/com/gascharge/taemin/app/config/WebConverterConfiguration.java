package com.gascharge.taemin.app.config;

import com.gascharge.taemin.app.config.converter.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConverterConfiguration implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        var converters = new ArrayList<>(List.of(
                new StringToUserEmailVerifiedConverter(),
                new StringToAuthProviderConverter(),
                new StringToUserAuthorityConverter(),
                new StringToChargePlaceMembershipConverter()
        ));

        converters.forEach(registry::addConverter);
    }
}

