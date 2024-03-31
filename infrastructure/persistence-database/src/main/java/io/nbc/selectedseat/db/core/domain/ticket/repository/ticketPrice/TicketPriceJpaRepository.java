package io.nbc.selectedseat.db.core.domain.ticket.repository.ticketPrice;

import io.nbc.selectedseat.db.core.domain.ticket.entity.TicketPriceEntity;
import io.nbc.selectedseat.domain.ticket.model.TicketRating;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketPriceJpaRepository extends JpaRepository<TicketPriceEntity, Long> {

    Optional<TicketPriceEntity> findByConcertIdAndTicketRating(
        final Long concertId,
        final TicketRating ticketRating);
}
