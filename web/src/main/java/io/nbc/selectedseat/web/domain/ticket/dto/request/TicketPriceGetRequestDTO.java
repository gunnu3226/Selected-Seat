package io.nbc.selectedseat.web.domain.ticket.dto.request;

import jakarta.validation.constraints.NotNull;

public record TicketPriceGetRequestDTO(

    @NotNull(message = "티켓 가격을 조회 할 콘서트ID를 입력해주세요")
    Long concertId
) {

}
