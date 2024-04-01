package io.nbc.selectedseat.domain.ticket.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketAndPrice {

    private Long ticketId;
    private Long concertId;
    private String ticketNumber;
    private TicketRating ticketRating;
    private Long price;
}
