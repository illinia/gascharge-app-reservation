package com.gascharge.taemin.app.controller.reservation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gascharge.taemin.domain.enums.reservation.ReservationStatus;
import com.gascharge.taemin.service.reservation.dto.ReservationServiceResponseDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationAppResponseDto {
    private String reservationValidationId;
    private String userEmail;
    private String chargePlaceId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime reserveTime;
    private ReservationStatus status;

    @Builder
    public ReservationAppResponseDto(String reservationValidationId, String userEmail, String chargePlaceId, LocalDateTime reserveTime, ReservationStatus status) {
        this.reservationValidationId = reservationValidationId;
        this.userEmail = userEmail;
        this.chargePlaceId = chargePlaceId;
        this.reserveTime = reserveTime;
        this.status = status;
    }

    public static ReservationAppResponseDto getReservationAppResponseDto(ReservationServiceResponseDto dto) {
        return ReservationAppResponseDto.builder()
                .reservationValidationId(dto.getReservationValidationId())
                .userEmail(dto.getUserEmail())
                .chargePlaceId(dto.getChargePlaceId())
                .reserveTime(dto.getReserveTime())
                .status(dto.getStatus())
                .build();
    }
}
