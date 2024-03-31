package io.nbc.selectedseat.domain.ticket.dto;

import io.nbc.selectedseat.domain.ticket.model.TicketPrice;
import io.nbc.selectedseat.domain.ticket.model.TicketRating;

public record TicketPriceInfo(
    Long ticketPriceId,
    Long concertId,
    TicketRating ticketRating,
    Long price
) {

    public static TicketPriceInfo from(final TicketPrice ticketPrice) {
        return new TicketPriceInfo(
            ticketPrice.getTicketPriceId(),
            ticketPrice.getConcertId(),
            ticketPrice.getTicketRating(),
            ticketPrice.getPrice()
        );
    }
}
