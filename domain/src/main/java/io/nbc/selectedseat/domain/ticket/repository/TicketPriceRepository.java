package io.nbc.selectedseat.domain.ticket.repository;

import io.nbc.selectedseat.domain.ticket.model.TicketPrice;
import io.nbc.selectedseat.domain.ticket.model.TicketRating;
import java.util.Optional;

public interface TicketPriceRepository {

    TicketPrice save(final TicketPrice ticketPrice);

    Optional<TicketPrice> findByConcertIdAndTicketRating(
        final Long concertId,
        final TicketRating ticketRating);
}
