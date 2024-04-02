package io.nbc.selectedseat.domain.ticket.repository;

import io.nbc.selectedseat.domain.ticket.model.TicketPrice;
import io.nbc.selectedseat.domain.ticket.model.TicketRating;
import java.util.List;
import java.util.Optional;

public interface TicketPriceRepository {

    TicketPrice save(final TicketPrice ticketPrice);

    Optional<TicketPrice> findByConcertIdAndTicketRating(
        final Long concertId,
        final TicketRating ticketRating
    );

    List<TicketPrice> getTicketPricesByIds(final List<Long> ticketIds);

    Optional<TicketPrice> findById(final Long ticketId);

    TicketPrice updateTicketPrice(final Long ticketId, final Long changePrice);

    void deleteTicketPrice(final Long ticketId);
}
