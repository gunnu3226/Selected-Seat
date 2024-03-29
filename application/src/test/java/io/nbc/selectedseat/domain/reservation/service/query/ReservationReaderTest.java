package io.nbc.selectedseat.domain.reservation.service.query;

import static io.nbc.selectedseat.domain.reservation.service.exception.ReservationExceptionCode.NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.nbc.selectedseat.domain.reservation.dto.ReservationInfoDTO;
import io.nbc.selectedseat.domain.reservation.mock.FakeReservationRepository;
import io.nbc.selectedseat.domain.reservation.model.Reservation;
import io.nbc.selectedseat.domain.reservation.service.exception.CustomReservationException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationReaderTest {

    private ReservationReader reservationReader;
    private FakeReservationRepository fakeReservationRepository;

    @BeforeEach
    void init() {
        fakeReservationRepository = new FakeReservationRepository();
        reservationReader = new ReservationReader(fakeReservationRepository);
    }

    @Test
    @DisplayName("예약의 단 건 내역을 조회할 수 있다")
    void when_get_reservation_by_id_then_return_reservation() {
        //given
        fakeReservationRepository.saveReservation(Reservation.builder()
            .reservationId(1L)
            .concertId(1L)
            .memberId(1L)
            .ticketId(1L)
            .ticketPriceId(1L)
            .build()
        );

        //when
        ReservationInfoDTO reservation = reservationReader.getReservation(1L);

        //then
        assertThat(reservation).isNotNull();
        assertThat(reservation.concertId()).isEqualTo(1L);
        assertThat(reservation.memberId()).isEqualTo(1L);
        assertThat(reservation.ticketId()).isEqualTo(1L);
        assertThat(reservation.ticketPriceId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("예약의 단 건 내역을 조회시 유효하지 않은 예약의 경우에는 예외가 발생한다")
    void when_get_reservation_by_invalid_id_then_return_reservation() {
        //given
        fakeReservationRepository.saveReservation(Reservation.builder()
            .reservationId(1L)
            .concertId(1L)
            .memberId(1L)
            .ticketId(1L)
            .ticketPriceId(1L)
            .build()
        );

        //when & then
        assertThatThrownBy(() -> reservationReader.getReservation(100L))
            .isInstanceOf(CustomReservationException.class)
            .hasMessage(NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("접수된 전체 예약 내역을 조회할 수 있다")
    void when_get_reservations_then_return_list_of_reservation() {
        //given
        fakeReservationRepository.saveReservation(new Reservation());
        fakeReservationRepository.saveReservation(new Reservation());
        fakeReservationRepository.saveReservation(new Reservation());

        //when
        List<ReservationInfoDTO> reservations = reservationReader.getReservations();

        //then
        assertThat(reservations).isNotNull();
        assertThat(reservations.size()).isEqualTo(3);
    }
}
