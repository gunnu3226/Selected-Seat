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
    private ReservationState reservationState;
    private LocalDateTime reservedAt;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime deletedAt;

    public Reservation complete() {
        return Reservation.builder()
            .reservationId(this.reservationId)
            .concertId(this.concertId)
            .memberId(this.memberId)
            .ticketId(this.ticketId)
            .ticketPriceId(this.ticketPriceId)
            .reservationState(ReservationState.COMPLETED)
            .reservedAt(this.reservedAt)
            .createdAt(this.createdAt)
            .modifiedAt(this.modifiedAt)
            .deletedAt(this.deletedAt)
            .build();
    }
}
