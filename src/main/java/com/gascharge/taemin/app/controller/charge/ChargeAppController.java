package com.gascharge.taemin.app.controller.charge;

import com.gascharge.taemin.app.controller.charge.dto.AddChargeRequestDto;
import com.gascharge.taemin.app.controller.charge.dto.UpdateChargeRequestDto;
import com.gascharge.taemin.app.controller.charge.dto.response.ChargeAppResponseDto;
import com.gascharge.taemin.app.controller.charge.dto.response.ChargeListAppResponseDto;
import com.gascharge.taemin.common.util.DtoFieldSpreader;
import com.gascharge.taemin.domain.enums.charge.ChargePlaceMembership;
import com.gascharge.taemin.service.charge.ChargeService;
import com.gascharge.taemin.service.charge.dto.ChargeServiceResponseDto;
import com.gascharge.taemin.service.charge.dto.FindAllChargeSearchStatusDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/charge")
public class ChargeAppController {
    private final ChargeService chargeService;

    @ApiOperation(
            value = "충전소 페이징, 정렬 검색", notes = "충전소 이름(포함), 가맹점 여부, Pageable 객체 이용한 조회입니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "is-membership",
                    value = "가맹점 여부",
                    defaultValue = "NOT_MEMBERSHIP"
            ),
            @ApiImplicitParam(
                    name = "name",
                    value = "충전소 이름",
                    defaultValue = "인천"
            )
    })
    @GetMapping("")
    public ResponseEntity<ChargeListAppResponseDto> getChargeList(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "is-membership", required = false) ChargePlaceMembership isMembership,
            @NotNull Pageable pageable) {

        FindAllChargeSearchStatusDto dto = new FindAllChargeSearchStatusDto(name, isMembership, pageable);
        List<ChargeServiceResponseDto> all = chargeService.findAll(dto);

        ChargeListAppResponseDto responseDto = ChargeListAppResponseDto.getChargeListResponseDto(all);

        return ResponseEntity.ok(responseDto);
    }

    @ApiOperation(
            value = "충전소 단건 검색", notes = "ChargePlaceId 로 충전소 단건 검색한다."
    )
    @ApiImplicitParam(
            name = "chargePlaceId",
            value = "충전소 고유값",
            required = true,
            paramType = "path",
            defaultValue = "1150020121HS2022025"
    )
    @GetMapping("/{chargePlaceId}")
    public ResponseEntity<ChargeAppResponseDto> getChargeByChargePlaceId(
            @PathVariable @NotBlank String chargePlaceId) {
        ChargeServiceResponseDto byChargePlaceId = chargeService.findByChargePlaceId(chargePlaceId);

        ChargeAppResponseDto chargeResponseDto = ChargeAppResponseDto.getChargeAppReturnDto(byChargePlaceId);
        return ResponseEntity.ok(chargeResponseDto);
    }

    @ApiOperation(
            value = "충전소 등록", notes = "새로운 충전소를 등록합니다."
    )
    @PostMapping("")
    public ResponseEntity<ChargeAppResponseDto> addCharge(
            @RequestBody @Valid AddChargeRequestDto addChargeRequestDto) {
        ChargeServiceResponseDto responseDto = chargeService.saveCharge(addChargeRequestDto.toChargeServiceRequestDto());

        ChargeAppResponseDto returnDto = ChargeAppResponseDto.getChargeAppReturnDto(responseDto);

        return new ResponseEntity(returnDto, HttpStatus.CREATED);
    }

    @ApiOperation(
            value = "충전소 정보 수정", notes = "충전소 이름, 총 충전 가능 차량수, 현재 대기 인원수를 업데이트 합니다."
    )
    @PatchMapping("/{chargePlaceId}")
    public ResponseEntity<ChargeAppResponseDto> updateCharge(
            @PathVariable @NotBlank String chargePlaceId,
            @RequestBody UpdateChargeRequestDto requestDto) {
        Map<String, Object> attributesMap = DtoFieldSpreader.of(requestDto);

        ChargeServiceResponseDto responseDto = chargeService.updateDynamicField(chargePlaceId, attributesMap);

        return ResponseEntity.ok(ChargeAppResponseDto.getChargeAppReturnDto(responseDto));
    }

    @ApiOperation(
            value = "충전소 삭제", notes = "충전소 정보를 삭제합니다."
    )
    @ApiImplicitParam(
            name = "chargePlaceId",
            value = "충전소 고유값",
            required = true,
            paramType = "path",
            defaultValue = "testId"
    )
    @DeleteMapping("/{chargePlaceId}")
    public ResponseEntity<String> deleteCharge(
            @PathVariable @NotBlank String chargePlaceId) {
        String result = chargeService.deleteCharge(chargePlaceId);

        return new ResponseEntity(result, HttpStatus.NO_CONTENT);
    }
}













