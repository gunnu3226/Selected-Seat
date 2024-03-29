package io.nbc.selectedseat.domain.reservation.repository;

import io.nbc.selectedseat.domain.reservation.model.Reservation;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    Long createReservation(Reservation reservation);

    Optional<Reservation> getReservation(final Long id);

    List<Reservation> getReservations();

    void deleteReservation(final Long id);
}
