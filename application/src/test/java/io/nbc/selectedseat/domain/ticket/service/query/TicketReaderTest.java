package io.nbc.selectedseat.domain.ticket.service.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.nbc.selectedseat.domain.ticket.dto.TicketInfo;
import io.nbc.selectedseat.domain.ticket.mock.FakeTicketRepository;
import io.nbc.selectedseat.domain.ticket.model.Ticket;
import io.nbc.selectedseat.domain.ticket.service.exception.CustomTicketException;
import io.nbc.selectedseat.domain.ticket.service.exception.TicketExceptionCode;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TicketReaderTest {

    private TicketReader ticketReader;
    private FakeTicketRepository ticketRepository;

    @BeforeEach
    void init() {
        ticketRepository = new FakeTicketRepository();
        ticketReader = new TicketReader(ticketRepository);
    }

    @Test
    @DisplayName("티켓의 전체 발행 내역을 조회할 수 있다")
    void when_find_tickets_then_return_list_of_tickets() {
        //given
        ticketRepository.saveTicket(new Ticket());
        ticketRepository.saveTicket(new Ticket());
        ticketRepository.saveTicket(new Ticket());
        ticketRepository.saveTicket(new Ticket());

        //when
        List<TicketInfo> tickets = ticketReader.getTickets();

        //then
        assertThat(tickets.size()).isEqualTo(4);
    }

    @Test
    @DisplayName("콘서트에 발행된 전체 티켓 내역을 조회할 수 있다")
    void when_find_tickets_by_concertId_then_return_list_of_tickets() {
        //given
        Ticket ticketA = Ticket.builder().concertId(1L).build();
        Ticket ticketB = Ticket.builder().concertId(2L).build();
        ticketRepository.saveTicket(ticketA);
        ticketRepository.saveTicket(ticketB);
        ticketRepository.saveTicket(ticketB);
        ticketRepository.saveTicket(ticketB);

        //when
        List<TicketInfo> tickets = ticketReader.getTicketsByConcertId(2L);

        //then
        assertThat(tickets.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("티켓의 단 건 내역을 조회할 수 있다")
    void when_find_ticket_by_id_then_return_ticket() {
        //given
        ticketRepository.saveTicket(Ticket.builder()
            .concertId(1L)
            .ticketId(1L)
            .ticketNumber("R:1:4")
            .build());

        //when
        TicketInfo ticket = ticketReader.getTicket(1L);

        //then
        assertThat(ticket.concertId()).isEqualTo(1L);
        assertThat(ticket.ticketId()).isEqualTo(1L);
        assertThat(ticket.ticketNumber()).isEqualTo("R:1:4");
    }

    @Test
    @DisplayName("티켓의 단 건 조회시 없는 티켓의 경우에는 예외가 발생한다")
    void when_find_ticket_by_invalid_id_then_throw_exception() {
        //given
        ticketRepository.saveTicket(Ticket.builder()
            .concertId(1L)
            .ticketId(1L)
            .ticketNumber("R:1:4")
            .build());

        //when & then
        assertThatThrownBy(() -> ticketReader.getTicket(100L))
            .isInstanceOf(CustomTicketException.class)
            .hasMessage(TicketExceptionCode.NOT_FOUND.getMessage());
    }
}
