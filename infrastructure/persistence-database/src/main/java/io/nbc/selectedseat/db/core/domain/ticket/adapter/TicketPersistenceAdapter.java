package io.nbc.selectedseat.db.core.domain.ticket.adapter;

import io.nbc.selectedseat.db.core.domain.ticket.entity.TicketEntity;
import io.nbc.selectedseat.db.core.domain.ticket.repository.ticket.TicketJpaRepository;
import io.nbc.selectedseat.db.core.domain.ticket.repository.ticket.TicketQueryRepository;
import io.nbc.selectedseat.domain.ticket.model.Ticket;
import io.nbc.selectedseat.domain.ticket.model.TicketAndPrice;
import io.nbc.selectedseat.domain.ticket.repository.TicketRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketPersistenceAdapter implements TicketRepository {

    private final TicketJpaRepository ticketJpaRepository;
    private final TicketQueryRepository ticketQueryRepository;

    @Override
    public void save(final Ticket ticket) {
        ticketJpaRepository.save(TicketEntity.from(ticket));
    }

    @Override
    public Optional<Ticket> getTicket(final Long id) {
        return ticketJpaRepository.findById(id).map(TicketEntity::toModel);
    }

    @Override
    public List<Ticket> getTickets() {
        return ticketJpaRepository.findAll().stream()
            .map(TicketEntity::toModel).toList();
    }

    @Override
    public List<Ticket> getTicketsByConvertId(final Long convertId) {
        return ticketJpaRepository.findAllByConcertId(convertId).stream()
            .map(TicketEntity::toModel).toList();
    }

    @Override
    public void deleteById(final Long id) {
        ticketJpaRepository.deleteById(id);
    }

    @Override
    public List<TicketAndPrice> getTicketsAndPriceByTicketIds(final List<Long> ticketIds) {
        return ticketQueryRepository.getTicketsAndPriceByTicketIds(ticketIds);
    }
}
