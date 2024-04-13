package io.nbc.selectedseat.domain.reservation.service.command;

import io.nbc.selectedseat.domain.reservation.model.Reservation;
import io.nbc.selectedseat.domain.reservation.repository.ReservationRepository;
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
                .build()
        );
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
        final Long ticketPrice,
        final LocalDateTime concertDate
    ){
        reservationRepository.createReservationDocument(
            reservationId,
            memberEmail,
            concertName,
            hall,
            ticketNumber,
            ticketPrice,
            concertDate
        );
    }
}
