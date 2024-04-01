package io.nbc.selectedseat.domain.member.facade.dto;

import io.nbc.selectedseat.domain.concert.model.Concert;
import io.nbc.selectedseat.domain.reservation.model.Reservation;
import io.nbc.selectedseat.domain.ticket.model.TicketPrice;
import io.nbc.selectedseat.domain.ticket.model.TicketRating;
import java.time.LocalDateTime;

public record ReservationDetailInfo(
    Long reservationId,
    Long ticketId,
    Long ticketPrice,
    TicketRating ticketRating,
    LocalDateTime reserved_at,
    ConcertInfo concertInfo
) {

    public static ReservationDetailInfo from(
        final Reservation reservation,
        final TicketPrice ticketPrice,
        final Concert concert
    ) {
        return new ReservationDetailInfo(
            reservation.getReservationId(),
            reservation.getTicketId(),
            ticketPrice.getPrice(),
            ticketPrice.getTicketRating(),
            reservation.getReservedAt(),
            ConcertInfo.from(concert));
    }
}
