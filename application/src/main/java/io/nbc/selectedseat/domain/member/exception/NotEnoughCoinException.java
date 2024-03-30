package io.nbc.selectedseat.domain.member.exception;

public class NotEnoughCoinException extends RuntimeException {

    public NotEnoughCoinException(String message) {
        super(message);
    }
}
