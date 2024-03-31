package io.nbc.selectedseat.web.domain.ticket.dto.response;

import io.nbc.selectedseat.domain.ticket.dto.TicketPriceInfo;

public record TicketPriceIdResponseDto(
    Long ticketPriceId
) {

    public static TicketPriceIdResponseDto from(
        final TicketPriceInfo ticketPriceInfo
    ) {
        return new TicketPriceIdResponseDto(
            ticketPriceInfo.ticketPriceId()
        );
    }
}
