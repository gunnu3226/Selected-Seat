package io.nbc.selectedseat.domain.ticket.dto;

import io.nbc.selectedseat.domain.ticket.model.TicketRating;

public record TicketAndPriceInfo(
    Long ticketId,
    Long concertId,
    String ticketNumber,
    TicketRating ticketRating,
    Long price
) {

}
