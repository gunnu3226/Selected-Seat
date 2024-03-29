package io.nbc.selectedseat.domain.reservation.service.query;

import static io.nbc.selectedseat.domain.reservation.service.exception.ReservationExceptionCode.NOT_FOUND;

import io.nbc.selectedseat.domain.reservation.dto.ReservationInfoDTO;
import io.nbc.selectedseat.domain.reservation.model.Reservation;
import io.nbc.selectedseat.domain.reservation.repository.ReservationRepository;
import io.nbc.selectedseat.domain.reservation.service.exception.CustomReservationException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationReader {

    private final ReservationRepository reservationRepository;

    public List<ReservationInfoDTO> getReservations() {
        return reservationRepository.getReservations().stream()
            .map(ReservationInfoDTO::from)
            .toList();
    }

    public ReservationInfoDTO getReservation(final Long reservationId) {
        Reservation reservation = reservationRepository.getReservation(reservationId)
            .orElseThrow(() ->
                new CustomReservationException(NOT_FOUND));

        return ReservationInfoDTO.from(reservation);
    }
}
