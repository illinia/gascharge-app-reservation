package com.gascharge.taemin.app.config.converter;

import com.gascharge.taemin.domain.enums.charge.ChargePlaceMembership;
import org.springframework.core.convert.converter.Converter;

public class StringToChargePlaceMembershipConverter implements Converter<String, ChargePlaceMembership> {
    @Override
    public ChargePlaceMembership convert(String source) {
        return ChargePlaceMembership.getChargePlaceMembership(source);
    }
}
