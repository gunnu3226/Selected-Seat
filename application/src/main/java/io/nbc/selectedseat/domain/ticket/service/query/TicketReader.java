package io.nbc.selectedseat.domain.ticket.service.query;

import io.nbc.selectedseat.domain.ticket.dto.TicketInfo;
import io.nbc.selectedseat.domain.ticket.model.Ticket;
import io.nbc.selectedseat.domain.ticket.model.TicketAndPrice;
import io.nbc.selectedseat.domain.ticket.repository.TicketRepository;
import io.nbc.selectedseat.domain.ticket.service.exception.CustomTicketException;
import io.nbc.selectedseat.domain.ticket.service.exception.TicketExceptionCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TicketReader {

    private final TicketRepository ticketRepository;

    public List<TicketInfo> getTickets() {
        return ticketRepository.getTickets()
            .stream()
            .map(TicketInfo::from)
            .toList();
    }

    public TicketInfo getTicket(final Long ticketId) {
        Ticket ticket = ticketRepository.getTicket(ticketId)
            .orElseThrow(() -> new CustomTicketException(TicketExceptionCode.NOT_FOUND));
        return TicketInfo.from(ticket);
    }

    public List<TicketInfo> getTicketsByConcertId(final Long concertId) {
        return ticketRepository.getTicketsByConvertId(concertId)
            .stream()
            .map(TicketInfo::from)
            .toList();
    }

    public List<TicketAndPrice> getTicketsAndPriceByMemberId(final List<Long> ticketIds) {
        return ticketRepository.getTicketsAndPriceByMemberId(ticketIds);
    }
}
