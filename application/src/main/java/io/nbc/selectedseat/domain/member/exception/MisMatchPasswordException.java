package io.nbc.selectedseat.domain.member.exception;

public class MisMatchPasswordException extends RuntimeException {

    public MisMatchPasswordException(String message) {
        super(message);
    }
}
