package com.gascharge.taemin.app.controller.reservation;

import com.gascharge.taemin.app.controller.reservation.dto.ForceUpdateReservationRequestDto;
import com.gascharge.taemin.app.controller.reservation.dto.PostReserveRequestDto;
import com.gascharge.taemin.app.controller.reservation.dto.UpdateReservationRequestDto;
import com.gascharge.taemin.app.controller.reservation.dto.response.ReservationAppResponseDto;
import com.gascharge.taemin.common.util.DtoFieldSpreader;
import com.gascharge.taemin.domain.enums.reservation.ReservationStatus;
import com.gascharge.taemin.domain.enums.user.UserAuthority;
import com.gascharge.taemin.security.entity.CurrentUser;
import com.gascharge.taemin.security.entity.UserPrincipal;
import com.gascharge.taemin.service.reservation.ReservationService;
import com.gascharge.taemin.service.reservation.dto.ReservationServiceResponseDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.gascharge.taemin.app.controller.reservation.dto.response.ReservationAppResponseDto.getReservationAppResponseDto;
import static com.gascharge.taemin.domain.entity.charge.ChargeTestData.CHARGE_TEST_ID;
import static com.gascharge.taemin.domain.entity.reservation.ReservationTestData.RESERVATION_TEST_UUID;
import static com.gascharge.taemin.domain.entity.user.UserTestData.USER_TEST_EMAIL;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    @ApiOperation(
            value = "예약 생성", notes = "인증된 사용자가 ChargePlaceId 와 LocalDateTime 을 통해 예약을 생성한다."
    )
    @PostMapping("")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ReservationAppResponseDto> reserve(
            @CurrentUser UserPrincipal userPrincipal,
            @RequestBody @Valid PostReserveRequestDto requestDto) {

        ReservationServiceResponseDto responseDto = reservationService.reserve(userPrincipal.getId(), requestDto.getChargePlaceId(), requestDto.getTime());

        return new ResponseEntity(getReservationAppResponseDto(responseDto), HttpStatus.CREATED);
    }

    @ApiOperation(
            value = "본인 예약 수정", notes = "인증되었고 수정 요청한 예약의 이메일과 인증 사용자의 이메일이 같거나, ADMIN 역할일 경우 예약 시간을 수정한다."
    )
    @PreAuthorize("isAuthenticated() and ((#requestDto.email == principal.email) or hasRole('ADMIN'))")
    @PatchMapping("")
    public ResponseEntity<ReservationAppResponseDto> updateSelfReservationTime(
            @RequestBody @Valid UpdateReservationRequestDto requestDto) {

        ReservationServiceResponseDto responseDto = reservationService.updateTime(requestDto.getReservationValidationId(), requestDto.getUpdateTime());

        return ResponseEntity.ok(getReservationAppResponseDto(responseDto));
    }

    @ApiOperation(
            value = "예약 상태 강제 업데이트", notes = "어드민 권한 유저가 모든 예약의 상태와 시간을 강제 수정할 수 있습니다."
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{reservationValidationId}")
    public ResponseEntity<ReservationAppResponseDto> forceUpdate(
            @PathVariable @NotBlank String reservationValidationId,
            @RequestBody ForceUpdateReservationRequestDto requestDto) {

        Map<String, Object> attributesMap = DtoFieldSpreader.of(requestDto);

        ReservationServiceResponseDto responseDto = reservationService.updateDynamicField(reservationValidationId, attributesMap);

        return ResponseEntity.ok(getReservationAppResponseDto(responseDto));
    }

    @ApiOperation(
            value = "예약 단건 검색", notes = "reservationValidationId 로 예약 단건 검색한다."
    )
    @ApiImplicitParam(
            name = "reservationValidationId",
            value = "예약 식별 값",
            required = true,
            paramType = "path",
            defaultValue = RESERVATION_TEST_UUID
    )
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{reservationValidationId}")
    public ResponseEntity<ReservationAppResponseDto> getById(
            @PathVariable @NotBlank String reservationValidationId) {

        ReservationServiceResponseDto responseDto = reservationService.findByReservationValidationId(reservationValidationId);

        return ResponseEntity.ok(getReservationAppResponseDto(responseDto));
    }

    @ApiOperation(
            value = "예약 조회", notes = "전체 조회일 경우 인증 유저의 권한이 ADMIN 이어야 하고, 다건 조회일 경우 인증 유저의 이메일이 검색 조건의 이메일과 같아야 한다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "email",
                    value = "검색 조건 이메일, 어드민일 경우 생략가능(전체조회)",
                    defaultValue = USER_TEST_EMAIL
            ),
            @ApiImplicitParam(
                    name = "chargePlaceId",
                    value = "충전소 식별 아이디",
                    defaultValue = CHARGE_TEST_ID
            )
    })
    @GetMapping("")
    @PreAuthorize("isAuthenticated() and ((#email == principal.email) or hasRole('ADMIN'))")
    public ResponseEntity<Page<ReservationAppResponseDto>> getList(
            @RequestParam(value = "email", required = false) @Email String email,
            @RequestParam(value = "chargePlaceId", required = false) String chargePlaceId,
            @NotNull Pageable pageable) {
        List<ReservationServiceResponseDto> all = reservationService.findAll(email, chargePlaceId, pageable);

        List<ReservationAppResponseDto> dtoList = all.stream().map(ReservationAppResponseDto::getReservationAppResponseDto).collect(Collectors.toList());

        return ResponseEntity.ok(
                new PageImpl<>(dtoList, pageable, dtoList.size())
        );
    }

    @ApiOperation(
            value = "예약 취소", notes = "인증된 사용자가 입력한 이메일과 같거나, 어드민 역할을 가지고 있을때 예약 상태를 취소로 변경합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "email",
                    value = "본인 확인용 이메일",
                    defaultValue = USER_TEST_EMAIL
            ),
            @ApiImplicitParam(
                    name = "reservationValidationId",
                    value = "충전소 식별 아이디",
                    defaultValue = CHARGE_TEST_ID
            )
    })
    @PostMapping("/cancel")
    @PreAuthorize("isAuthenticated() and ((#email == principal.email) or hasRole('ADMIN'))")
    public ResponseEntity<ReservationAppResponseDto> cancel(
            @CurrentUser UserPrincipal userPrincipal,
            @RequestParam(value = "email", required = false) @Email String email,
            @RequestParam(value = "reservationValidationId") @NotBlank String reservationValidationId) {

        boolean isAdminRole = userPrincipal.getAuthorities().stream().anyMatch(i -> i.getAuthority().equals(UserAuthority.ROLE_ADMIN.toString()));

        if (!isAdminRole) {
            boolean isSameEmail = reservationService.checkSameEmail(email, reservationValidationId);

            if (!isSameEmail) {
                throw new AccessDeniedException(
                        "ReservationController cancelReservation 메서드에서 "
                                + userPrincipal.getEmail() + " " + userPrincipal.getAuthorities().toString() + " 역할의 유저가 예약 번호 "
                                + reservationValidationId + " 의 예약을 변경하려 시도하였으나 권한 없음으로 예외 처리 되었다.");
            }
        }

        ReservationServiceResponseDto responseDto = reservationService.updateStatus(reservationValidationId, ReservationStatus.CANCEL);

        return new ResponseEntity(getReservationAppResponseDto(responseDto), HttpStatus.OK);
    }
}












