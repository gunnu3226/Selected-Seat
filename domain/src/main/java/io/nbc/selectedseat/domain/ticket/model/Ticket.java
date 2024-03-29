package io.nbc.selectedseat.domain.ticket.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    private Long ticketId;
    private Long concertId;
    private TicketRating ticketRating;
    private String ticketNumber;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
