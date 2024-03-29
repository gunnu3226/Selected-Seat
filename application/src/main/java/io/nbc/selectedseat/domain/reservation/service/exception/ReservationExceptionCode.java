package io.nbc.selectedseat.domain.reservation.service.exception;

import lombok.Getter;

@Getter
public enum ReservationExceptionCode {
    NOT_FOUND("잘못된 접근입니다");

    private final String message;

    ReservationExceptionCode(final String message) {
        this.message = message;
    }
}
