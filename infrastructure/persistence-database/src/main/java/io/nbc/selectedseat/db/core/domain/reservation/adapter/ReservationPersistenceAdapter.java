package io.nbc.selectedseat.db.core.domain.reservation.adapter;

import io.nbc.selectedseat.db.core.domain.reservation.entity.ReservationDocument;
import io.nbc.selectedseat.db.core.domain.reservation.entity.ReservationEntity;
import io.nbc.selectedseat.db.core.domain.reservation.repository.ReservationJpaRepository;
import io.nbc.selectedseat.db.core.domain.reservation.repository.ReservationMongoRepository;
import io.nbc.selectedseat.domain.reservation.model.Reservation;
import io.nbc.selectedseat.domain.reservation.repository.ReservationRepository;
import io.nbc.selectedseat.domain.ticket.model.Ticket;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationPersistenceAdapter implements ReservationRepository {

    private final ReservationJpaRepository reservationJpaRepository;
    private final ReservationMongoRepository reservationMongoRepository;

    @Override
    public Long createReservation(final Reservation reservation) {
        return reservationJpaRepository.save(ReservationEntity.from(reservation))
            .getReservationId();
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
        ReservationDocument reservationDocument = new ReservationDocument(
            reservationId,
            memberEmail,
            concertName,
            hall,
            ticketNumber,
            startedAt
        );

        return reservationMongoRepository.save(reservationDocument).getId();
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

    @Override
    public List<Reservation> getReservationByMemberId(final Long memberId) {
        return reservationJpaRepository.findByMemberId(memberId)
            .stream().map(ReservationEntity::toModel)
            .toList();
    }
}
