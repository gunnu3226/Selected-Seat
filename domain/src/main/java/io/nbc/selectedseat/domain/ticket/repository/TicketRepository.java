package io.nbc.selectedseat.domain.ticket.repository;

import io.nbc.selectedseat.domain.ticket.model.Ticket;
import java.util.List;
import java.util.Optional;

public interface TicketRepository {

    void save(Ticket ticket);

    Optional<Ticket> findById(Long id);

    Long update(Ticket updateTicket);

    Long deleteById(Long id);

    void saveAll(List<Ticket> tickets);
}
