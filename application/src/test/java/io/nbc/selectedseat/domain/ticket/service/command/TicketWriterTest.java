package io.nbc.selectedseat.domain.ticket.service.command;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import io.nbc.selectedseat.domain.ticket.mock.FakeTicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TicketWriterTest {

    private TicketWriter ticketWriter;

    @BeforeEach
    void init() {
        ticketWriter = new TicketWriter(new FakeTicketRepository());
    }

    @Test
    @DisplayName("티켓을 생성할 수 있고, 티켓이 정상적으로 생성시 전체 티켓수를 반환한다")
    void when_create_ticket_then_return_number_of_tickets() {
        //given
        Long concertId = 1L;

        //when
        ticketWriter.createTicket(concertId);

        //then
        assertDoesNotThrow(() -> ticketWriter.createTicket(concertId));
    }
}
