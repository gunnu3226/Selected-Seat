package io.nbc.selectedseat.db.core.domain.ticket.repository.ticket;

import io.nbc.selectedseat.domain.ticket.model.TicketAndPrice;
import java.util.List;

public interface TicketQueryRepository {

    List<TicketAndPrice> getTicketsAndPriceByTicketIds(final List<Long> ticketIds);
}
