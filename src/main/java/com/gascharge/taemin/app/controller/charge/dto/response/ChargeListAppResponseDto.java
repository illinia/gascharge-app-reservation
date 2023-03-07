package com.gascharge.taemin.app.controller.charge.dto.response;

import com.gascharge.taemin.service.charge.dto.ChargeServiceResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChargeListAppResponseDto {
    private List<ChargeAppResponseDto> responseDtoList;

    public static ChargeListAppResponseDto getChargeListResponseDto(List<ChargeServiceResponseDto> chargeServiceResponseDtoList) {
        return new ChargeListAppResponseDto(
                chargeServiceResponseDtoList.stream()
                .map(ChargeAppResponseDto::getChargeAppReturnDto)
                .collect(Collectors.toList()));
    }


}
