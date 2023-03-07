package com.gascharge.taemin.app.controller.charge.dto;

import com.gascharge.taemin.domain.enums.charge.ChargePlaceMembership;
import com.gascharge.taemin.service.charge.dto.ChargeServiceRequestDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddChargeRequestDto {

    @NotBlank
    @ApiModelProperty(example = "testId")
    private String chargePlaceId;

    @NotBlank
    @ApiModelProperty(example = "testName")
    private String name;

    @ApiModelProperty(example = "5")
    @NotNull
    @Min(value = 0)
    private Long totalCount;

    @ApiModelProperty(example = "2")
    @NotNull
    @Min(value = 0)
    private Long currentCount;

    @ApiModelProperty(example = "is-membership")
    private ChargePlaceMembership chargeMembership;

    public ChargeServiceRequestDto toChargeServiceRequestDto() {
        return ChargeServiceRequestDto.builder()
                .chargePlaceId(this.chargePlaceId)
                .name(this.name)
                .totalCount(this.totalCount)
                .currentCount(this.currentCount)
                .membership(this.chargeMembership.getMembershipString())
                .build();
    }
}
