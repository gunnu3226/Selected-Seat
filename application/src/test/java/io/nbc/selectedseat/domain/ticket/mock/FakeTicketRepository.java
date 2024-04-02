package io.nbc.selectedseat.domain.ticket.mock;

import io.nbc.selectedseat.domain.ticket.model.Ticket;
import io.nbc.selectedseat.domain.ticket.model.TicketAndPrice;
import io.nbc.selectedseat.domain.ticket.repository.TicketRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FakeTicketRepository implements TicketRepository {

    private final List<Ticket> tickets = new ArrayList<>();

    public FakeTicketRepository() {
    }

    @Override
    public void save(Ticket ticket) {
    }

    @Override
    public Optional<Ticket> getTicket(Long id) {
        return tickets.stream()
            .filter(t -> Objects.equals(t.getTicketId(), id))
            .findFirst();
    }

    @Override
    public List<Ticket> getTickets() {
        return tickets;
    }

    @Override
    public List<Ticket> getTicketsByConvertId(Long convertId) {
        return tickets.stream()
            .filter(t -> t.getConcertId().equals(convertId))
            .toList();
    }

    @Override
    public void deleteById(Long id) {
    }

    @Override
    public List<TicketAndPrice> getTicketsAndPriceByTicketIds(List<Long> ticketIds) {
        return null;
    }

    public void saveTicket(Ticket ticket) {
        this.tickets.add(ticket);
    }
}
