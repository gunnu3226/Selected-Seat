package io.nbc.selectedseat.domain.concert.exception;

public class ConcertDateExistException extends RuntimeException {
    private static final String ERROR_MESSAGE = "공연 일정 정보가 존재하지 않습니다";

    public ConcertDateExistException() {
        super(ERROR_MESSAGE);
    }
}
