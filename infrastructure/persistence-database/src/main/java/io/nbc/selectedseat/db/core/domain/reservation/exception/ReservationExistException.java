package io.nbc.selectedseat.db.core.domain.reservation.exception;

public class ReservationExistException extends RuntimeException {
    private final static String MESSAGE = "예약이 존재하지 않습니다";
    public ReservationExistException() {
        super(MESSAGE);
    }
}
