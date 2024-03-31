package io.nbc.selectedseat.domain.ticket.repository;

import io.nbc.selectedseat.domain.ticket.model.Ticket;
import io.nbc.selectedseat.domain.ticket.model.TicketAndPrice;
import java.util.List;
import java.util.Optional;

public interface TicketRepository {

    void save(final Ticket ticket);

    Optional<Ticket> getTicket(final Long id);

    List<Ticket> getTickets();

    List<Ticket> getTicketsByConvertId(final Long convertId);

    void deleteById(final Long id);

    List<TicketAndPrice> getTicketsAndPriceByTicketIds(final List<Long> ticketIds);
}
