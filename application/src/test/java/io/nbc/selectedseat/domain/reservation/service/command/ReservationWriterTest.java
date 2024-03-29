package io.nbc.selectedseat.domain.reservation.service.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import io.nbc.selectedseat.domain.reservation.mock.FakeReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationWriterTest {

    private ReservationWriter reservationWriter;

    @BeforeEach
    void init() {
        reservationWriter = new ReservationWriter(new FakeReservationRepository());
    }

    @Test
    @DisplayName("예약을 생성할 수 있고, 예약이 정상적으로 생성되면 예약번호를 반환한다")
    void when_create_reservation_then_return_reservation_id() {
        //given
        Long concertId = 1L;
        Long memberId = 1L;
        Long ticketId = 1L;
        Long ticketPriceId = 1L;

        //when
        Long reservationNumber = reservationWriter.createReservation(
            concertId,
            memberId,
            ticketId,
            ticketPriceId
        );

        //then
        assertThat(reservationNumber).isEqualTo(1L);
    }

    @Test
    @DisplayName("예약은 번호를 통해서 취소할 수 있다")
    void when_delete_reservation_by_id_then_return_void() {

        //when & then
        assertDoesNotThrow(() -> reservationWriter.deleteReservation(1L));
    }
}
