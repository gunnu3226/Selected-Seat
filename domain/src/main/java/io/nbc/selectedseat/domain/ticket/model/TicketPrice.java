package io.nbc.selectedseat.domain.ticket.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketPrice {

    private Long ticketPriceId;
    private Long concertId;
    private TicketRating ticketRating;
    private Long price;
}
