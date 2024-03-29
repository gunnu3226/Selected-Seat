package io.nbc.selectedseat.domain.ticket.service.exception;

public class CustomTicketException extends RuntimeException {
    public CustomTicketException(TicketExceptionCode code) {
        super(code.getMessage());
    }
}
