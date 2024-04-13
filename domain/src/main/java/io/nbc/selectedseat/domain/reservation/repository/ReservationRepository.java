package io.nbc.selectedseat.domain.reservation.repository;

import io.nbc.selectedseat.domain.reservation.model.Reservation;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    Long createReservation(final Reservation reservation);

    String createReservationDocument(
        final Long reservationId,
        final String hall,
        final String concertName,
        final String memberEmail,
        final String ticketNumber,
        final LocalDateTime startedAt
    );

    Optional<Reservation> getReservation(final Long id);

    List<Reservation> getReservations();

    void deleteReservation(final Long id);

    List<Reservation> getReservationByMemberId(final Long memberId);
}
