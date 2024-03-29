package io.nbc.selectedseat.domain.reservation.mock;

import io.nbc.selectedseat.domain.reservation.model.Reservation;
import io.nbc.selectedseat.domain.reservation.repository.ReservationRepository;
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

    public void saveReservation(final Reservation reservation) {
        this.reservations.add(reservation);
    }
}
