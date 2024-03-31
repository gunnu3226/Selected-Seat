package io.nbc.selectedseat.db.core.domain.ticket.adapter;

import io.nbc.selectedseat.db.core.domain.ticket.entity.TicketPriceEntity;
import io.nbc.selectedseat.db.core.domain.ticket.repository.ticketPrice.TicketPriceJpaRepository;
import io.nbc.selectedseat.db.core.domain.ticket.repository.ticketPrice.TicketPriceQueryRepository;
import io.nbc.selectedseat.domain.ticket.model.TicketPrice;
import io.nbc.selectedseat.domain.ticket.model.TicketRating;
import io.nbc.selectedseat.domain.ticket.repository.TicketPriceRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketPricePersistenceAdapter implements TicketPriceRepository {

    private final TicketPriceJpaRepository ticketPriceJpaRepository;
    private final TicketPriceQueryRepository ticketPriceQueryRepository;

    @Override
    public TicketPrice save(final TicketPrice ticketPrice) {
        return ticketPriceJpaRepository.save(TicketPriceEntity.from(ticketPrice))
            .toModel();
    }

    @Override
    public Optional<TicketPrice> findByConcertIdAndTicketRating(
        final Long concertId,
        final TicketRating ticketRating
    ) {
        return ticketPriceJpaRepository.findByConcertIdAndTicketRating(concertId, ticketRating)
            .map(TicketPriceEntity::toModel);
    }

    @Override
    public List<TicketPrice> getTicketPricesByIds(List<Long> ticketIds) {
        return ticketPriceJpaRepository.findAllById(ticketIds)
            .stream().map(TicketPriceEntity::toModel)
            .toList();
    }
}
