package io.nbc.selectedseat.domain.ticket.mock;

import io.nbc.selectedseat.domain.ticket.model.Ticket;
import io.nbc.selectedseat.domain.ticket.repository.TicketRepository;
import java.util.List;
import java.util.Optional;

public class FakeTicketRepository implements TicketRepository {

    private Ticket ticket;

    public FakeTicketRepository() {
    }

    public FakeTicketRepository(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public void save(Ticket ticket) {
    }

    @Override
    public Optional<Ticket> findById(Long id) {
        return Optional.of(this.ticket);
    }

    @Override
    public Long update(Ticket updateTicket) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
    }

    @Override
    public void saveAll(List<Ticket> tickets) {
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
