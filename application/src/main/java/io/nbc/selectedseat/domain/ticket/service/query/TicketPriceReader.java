package io.nbc.selectedseat.domain.ticket.service.query;

import io.nbc.selectedseat.domain.ticket.dto.TicketPriceInfo;
import io.nbc.selectedseat.domain.ticket.model.TicketPrice;
import io.nbc.selectedseat.domain.ticket.repository.TicketPriceRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TicketPriceReader {

    private final TicketPriceRepository ticketPriceRepository;

    public List<TicketPriceInfo> getTicketPriceByConcertId(final Long concertId) {
        return ticketPriceRepository.getTicketPriceByConcertId(concertId)
            .stream().map(TicketPriceInfo::from).toList();
    }

    public List<TicketPrice> getTicketPricesByIds(final List<Long> ticketIds) {
        return ticketPriceRepository.getTicketPricesByIds(ticketIds);
    }
}
