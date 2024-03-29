package io.nbc.selectedseat.domain.reservation.dto;

import io.nbc.selectedseat.domain.reservation.model.Reservation;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ReservationInfoDTO(
    Long reservationId,
    Long concertId,
    Long memberId,
    Long ticketId,
    Long ticketPriceId,
    LocalDateTime reservedAt
) {

    public static ReservationInfoDTO from(final Reservation reservation) {
        return ReservationInfoDTO.builder()
            .reservationId(reservation.getReservationId())
            .concertId(reservation.getConcertId())
            .memberId(reservation.getMemberId())
            .ticketId(reservation.getTicketId())
            .ticketPriceId(reservation.getTicketPriceId())
            .reservedAt(reservation.getReservedAt())
            .build();
    }
}
