package io.nbc.selectedseat.domain.reservation.mock;

import io.nbc.selectedseat.domain.reservation.model.Reservation;
import io.nbc.selectedseat.domain.reservation.repository.ReservationRepository;
import io.nbc.selectedseat.domain.ticket.model.Ticket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FakeReservationRepository implements ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();

    public FakeReservationRepository() {
    }

    @Override
    public Long createReservation(final Reservation reservation) {
        return 1L;
    }

    @Override
    public String createReservationDocument(
        final Long reservationId,
        final String hall,
        final String concertName,
        final String memberEmail,
        final String ticketNumber,
        final LocalDateTime startedAt
    ) {

        return null;
    }

    @Override
    public Optional<Reservation> getReservation(final Long id) {
        return reservations.stream()
            .filter(r -> r.getReservationId().equals(id))
            .findFirst();
    }

    @Override
    public List<Reservation> getReservations() {
        return new ArrayList<>(reservations);
    }

    @Override
    public void deleteReservation(final Long id) {
    }

    @Override
    public List<Reservation> getReservationByMemberId(Long memberId) {
        return null;
    }

    public void saveReservation(final Reservation reservation) {
        this.reservations.add(reservation);
    }
}
