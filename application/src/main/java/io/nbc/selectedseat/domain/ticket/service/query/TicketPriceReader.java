package io.nbc.selectedseat.domain.ticket.service.query;

import io.nbc.selectedseat.domain.ticket.dto.TicketPriceInfo;
import io.nbc.selectedseat.domain.ticket.model.TicketPrice;
import io.nbc.selectedseat.domain.ticket.model.TicketRating;
import io.nbc.selectedseat.domain.ticket.repository.TicketPriceRepository;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TicketPriceReader {

    private final TicketPriceRepository ticketPriceRepository;

    public List<TicketPriceInfo> getTicketPriceByConcertId(
        final Long concertId) {
        return ticketPriceRepository.getTicketPriceByConcertId(concertId)
            .stream().map(TicketPriceInfo::from).toList();
    }

    public List<TicketPrice> getTicketPricesByIds(final List<Long> ticketIds) {
        return ticketPriceRepository.getTicketPricesByIds(ticketIds);
    }

    public TicketPriceInfo getTicketPriceByConcertAndRating(
        final Long concertId,
        final TicketRating rating
    ) {
        TicketPrice ticketPrice = ticketPriceRepository
            .findByConcertIdAndTicketRating(concertId, rating)
            .orElseThrow(()
                -> new NoSuchElementException("티켓 가격 정보가 존재하지 않습니다"));

        return TicketPriceInfo.from(ticketPrice);
    }
}
