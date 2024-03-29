package io.nbc.selectedseat.domain.reservation.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    private Long reservationId;
    private Long concertId;
    private Long memberId;
    private Long ticketId;
    private Long ticketPriceId;
    private LocalDateTime reservedAt;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime deletedAt;
}
