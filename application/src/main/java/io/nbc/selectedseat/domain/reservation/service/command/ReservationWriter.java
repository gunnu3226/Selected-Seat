package io.nbc.selectedseat.domain.reservation.service.command;

import io.nbc.selectedseat.db.core.domain.reservation.exception.ReservationExistException;
import io.nbc.selectedseat.domain.reservation.model.Reservation;
import io.nbc.selectedseat.domain.reservation.model.ReservationState;
import io.nbc.selectedseat.domain.reservation.repository.ReservationRepository;
import io.nbc.selectedseat.domain.ticket.model.TicketRating;
import io.nbc.selectedseat.redis.distributedlock.DistributedLock;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationWriter {

    private final ReservationRepository reservationRepository;

    @DistributedLock(key = "'concert:' + #concertId + ':ticketId:' + #ticketId")
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
                .reservationState(ReservationState.PROGRESS)
                .build()
        );
    }

    public Long completeReservation(final Long reservationId) {
        Reservation reservation = reservationRepository.getReservation(
                reservationId)
            .orElseThrow(ReservationExistException::new);

        Reservation completed = reservation.complete();
        return reservationRepository.createReservation(completed);
    }

    public void deleteReservation(final Long reservationId) {
        reservationRepository.deleteReservation(reservationId);
    }

    public void createReservationDocument(
        final Long reservationId,
        final String memberEmail,
        final String concertName,
        final String hall,
        final String ticketNumber,
        final TicketRating ticketRating,
        final Long ticketPrice,
        final LocalDateTime concertDate
    ){
        reservationRepository.createReservationDocument(
            reservationId,
            hall,
            concertName,
            memberEmail,
            ticketNumber,
            ticketRating,
            ticketPrice,
            concertDate
        );
    }
}
