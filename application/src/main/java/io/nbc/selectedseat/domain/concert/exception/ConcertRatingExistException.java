package io.nbc.selectedseat.domain.concert.exception;

public class ConcertRatingExistException extends RuntimeException {
    private static final String ERROR_MESSAGE = "등급 정보가 존재하지 않습니다";

    public ConcertRatingExistException() {
        super(ERROR_MESSAGE);
    }
}
