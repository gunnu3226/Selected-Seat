package io.nbc.selectedseat.db.core.domain.ticket.adapter;

import io.nbc.selectedseat.db.core.domain.ticket.entity.TicketEntity;
import io.nbc.selectedseat.db.core.domain.ticket.repository.TicketJpaRepository;
import io.nbc.selectedseat.domain.ticket.model.Ticket;
import io.nbc.selectedseat.domain.ticket.repository.TicketRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketPersistenceAdapter implements TicketRepository {

    private final TicketJpaRepository ticketJpaRepository;

    @Override
    public void save(final Ticket ticket) {
        ticketJpaRepository.save(TicketEntity.from(ticket));
    }

    @Override
    public Optional<Ticket> findById(final Long id) {
        return ticketJpaRepository.findById(id).map(TicketEntity::toModel);
    }

    @Override
    public Long update(final Ticket updateTicket) {
        return ticketJpaRepository.saveAndFlush(TicketEntity.from(updateTicket)).ticketId;
    }

    @Override
    public Long deleteById(final Long id) {
        ticketJpaRepository.deleteById(id);
        return id;
    }

    @Override
    public void saveAll(final List<Ticket> tickets) {
        ticketJpaRepository.saveAll(tickets.stream().map(TicketEntity::from).toList());
    }
}
