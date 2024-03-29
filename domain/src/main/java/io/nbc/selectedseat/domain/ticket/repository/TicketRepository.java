package io.nbc.selectedseat.domain.ticket.repository;

import io.nbc.selectedseat.domain.ticket.model.Ticket;
import java.util.List;
import java.util.Optional;

public interface TicketRepository {

    void save(final Ticket ticket);

    Optional<Ticket> findById(final Long id);

    Long update(final Ticket updateTicket);

    void deleteById(final Long id);

    void saveAll(final List<Ticket> tickets);
}
