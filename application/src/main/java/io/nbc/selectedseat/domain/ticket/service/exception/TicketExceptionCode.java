package io.nbc.selectedseat.domain.ticket.service.exception;

import lombok.Getter;

@Getter
public enum TicketExceptionCode {
    NOT_FOUND("잘못된 접근입니다");

    private final String message;

    TicketExceptionCode(String message) {
        this.message = message;
    }
}
