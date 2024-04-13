package io.nbc.selectedseat.domain.ticket.dto;

import io.nbc.selectedseat.domain.ticket.model.Ticket;
import io.nbc.selectedseat.domain.ticket.model.TicketRating;
import java.time.LocalDateTime;

public record TicketInfo(
    Long ticketId,
    Long concertId,
    Long concertDateId,
    TicketRating ticketRating,
    String ticketNumber,
    LocalDateTime createdAt
) {

    public static TicketInfo from(final Ticket ticket) {
        return new TicketInfo(
            ticket.getTicketId(),
            ticket.getConcertId(),
            ticket.getConcertDateId(),
            ticket.getTicketRating(),
            ticket.getTicketNumber(),
            ticket.getCreatedAt()
        );
    }

    public String ticketRatingToString(){
        return ticketRating.toString();
    }
}
