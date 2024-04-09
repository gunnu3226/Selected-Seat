package io.nbc.selectedseat.web.domain.ticket.dto.request;

import jakarta.validation.constraints.NotNull;

public record TicketPriceRequestDto(
    @NotNull(message = "콘서트 아이디는 필수 입력값 입니다")
    Long concertId,
    @NotNull(message = "좌석 등급은 필수 입력값 입니다")
    String ticketRating,
    @NotNull(message = "가격은 필수 입력값 입니다")
    Long price
) {

}
