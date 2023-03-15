package com.gascharge.taemin.app.error.exception.jpa;

public class DuplicateReservationException extends DuplicateEntityException {
    public DuplicateReservationException() {
        super();
    }

    public DuplicateReservationException(Class clazz, String chargePlaceId, String reservationTime) {
        super(clazz.getName() + "의 엔티티에 " + chargePlaceId + "의 충전소 id 와 " + reservationTime + "의 시간에 해당하는 예약이 이미 존재합니다.");
    }
}
