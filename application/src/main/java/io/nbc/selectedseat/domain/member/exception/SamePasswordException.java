package io.nbc.selectedseat.domain.member.exception;

public class SamePasswordException extends RuntimeException {

    public SamePasswordException(String message) {
        super(message);
    }
}
