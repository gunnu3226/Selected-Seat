package io.nbc.selectedseat.domain.concert.exception;

public class ConcertExistException extends RuntimeException {

    public ConcertExistException(final String message) {
        super(message);
    }

}
