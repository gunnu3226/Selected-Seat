package io.nbc.selectedseat.domain.member.exception;

public class NoSuchMemberException extends RuntimeException {

    public NoSuchMemberException(String message) {
        super(message);
    }
}
