package io.nbc.selectedseat.domain.reservation.service.exception;

public class CustomReservationException extends RuntimeException {

    public CustomReservationException(ReservationExceptionCode reservationExceptionCode) {
        super(reservationExceptionCode.getMessage());
    }
}
