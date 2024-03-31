package io.nbc.selectedseat.domain.member.facade.dto;

import io.nbc.selectedseat.domain.concert.model.Concert;
import io.nbc.selectedseat.domain.ticket.model.TicketAndPrice;
import io.nbc.selectedseat.domain.ticket.model.TicketRating;

public record TicketDetailInfo(

    Long ticketId,
    String ticketNumber,
    TicketRating ticketRating,
    Long price,
    ConcertInfo concertInfo
) {

    public static TicketDetailInfo from(
        final TicketAndPrice ticketAndPrice,
        final Concert concert
    ) {
        return new TicketDetailInfo(
            ticketAndPrice.getTicketId(),
            ticketAndPrice.getTicketNumber(),
            ticketAndPrice.getTicketRating(),
            ticketAndPrice.getPrice(),
            ConcertInfo.from(concert));
    }
}
