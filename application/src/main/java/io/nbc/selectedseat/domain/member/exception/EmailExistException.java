package io.nbc.selectedseat.domain.member.exception;

public class EmailExistException extends RuntimeException {

    public EmailExistException(String message) {
        super(message);
    }
}
