package com.gascharge.taemin.app.controller.charge.dto.response;

import com.gascharge.taemin.service.charge.dto.ChargeServiceResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ChargeAppResponseDto {
    private String chargePlaceId;
    private String name;
    private Long totalCount;
    private Long currentCount;
    private String membership;

    @Builder
    public ChargeAppResponseDto(String chargePlaceId, String name, Long totalCount, Long currentCount, String membership) {
        this.chargePlaceId = chargePlaceId;
        this.name = name;
        this.totalCount = totalCount;
        this.currentCount = currentCount;
        this.membership = membership;
    }

    public static ChargeAppResponseDto getChargeAppReturnDto(ChargeServiceResponseDto chargeServiceResponseDto) {
        return ChargeAppResponseDto.builder()
                .chargePlaceId(chargeServiceResponseDto.getChargePlaceId())
                .name(chargeServiceResponseDto.getName())
                .totalCount(chargeServiceResponseDto.getTotalCount())
                .currentCount(chargeServiceResponseDto.getCurrentCount())
                .membership(chargeServiceResponseDto.getMembership())
                .build();
    }
}
