package io.nbc.selectedseat.batch.job.ticket;

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

    private Long concertId;
    private String ticketRating;
    private String ticketNumber;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
