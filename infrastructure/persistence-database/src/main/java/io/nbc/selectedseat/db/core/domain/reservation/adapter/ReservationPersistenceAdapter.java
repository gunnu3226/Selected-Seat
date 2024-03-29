package io.nbc.selectedseat.db.core.domain.reservation.adapter;

import io.nbc.selectedseat.db.core.domain.reservation.entity.ReservationEntity;
import io.nbc.selectedseat.db.core.domain.reservation.repository.ReservationJpaRepository;
import io.nbc.selectedseat.domain.reservation.model.Reservation;
import io.nbc.selectedseat.domain.reservation.repository.ReservationRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class  ReservationPersistenceAdapter implements ReservationRepository {

    private final ReservationJpaRepository reservationJpaRepository;


    @Override
    public Long createReservation(final Reservation reservation) {
        return reservationJpaRepository.save(ReservationEntity.from(reservation))
            .getReservationId();
    }

    @Override
    public Optional<Reservation> getReservation(final Long id) {
        return reservationJpaRepository.findById(id)
            .map(ReservationEntity::toModel);
    }

    @Override
    public List<Reservation> getReservations() {
        return reservationJpaRepository.findAll().stream()
            .map(ReservationEntity::toModel)
            .toList();
    }

    @Override
    public void deleteReservation(final Long id) {
        reservationJpaRepository.deleteById(id);
    }
}
