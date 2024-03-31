package io.nbc.selectedseat.domain.ticket.service.exception;

public class ExistTicketPriceException extends RuntimeException {

    public ExistTicketPriceException(String message) {
        super(message);
    }
}
