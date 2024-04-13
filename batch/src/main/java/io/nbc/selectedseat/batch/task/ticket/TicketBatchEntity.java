package io.nbc.selectedseat.batch.task.ticket;

import io.nbc.selectedseat.db.core.domain.ticket.entity.TicketEntity;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketBatchEntity {

    private Long ticketId;
    private Long concertId;
    private String ticketRating;
    private String ticketNumber;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime deletedAt;

    public static TicketBatchEntity from(TicketEntity ticket) {
        return new TicketBatchEntity(
            ticket.getTicketId(),
            ticket.getConcertId(),
            ticket.getTicketRating().toString(),
            ticket.getTicketNumber(),
            ticket.getCreatedAt(),
            ticket.getModifiedAt(),
            ticket.getDeletedAt()
        );
    }
}
