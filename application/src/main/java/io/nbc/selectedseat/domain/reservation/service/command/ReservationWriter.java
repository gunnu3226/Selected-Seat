package io.nbc.selectedseat.domain.reservation.service.command;

import io.nbc.selectedseat.domain.reservation.model.Reservation;
import io.nbc.selectedseat.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationWriter {

    private final ReservationRepository reservationRepository;

    public Long createReservation(
        final Long concertId,
        final Long memberId,
        final Long ticketId,
        final Long ticketPriceId
    ) {
        return reservationRepository.createReservation(
            Reservation.builder()
                .concertId(concertId)
                .memberId(memberId)
                .ticketId(ticketId)
                .ticketPriceId(ticketPriceId)
                .build()
        );
    }

    public void deleteReservation(final Long reservationId) {
        reservationRepository.deleteReservation(reservationId);
    }
}
