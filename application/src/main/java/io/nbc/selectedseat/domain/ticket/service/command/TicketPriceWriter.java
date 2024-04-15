package io.nbc.selectedseat.domain.ticket.service.command;

import io.nbc.selectedseat.domain.ticket.dto.TicketPriceInfo;
import io.nbc.selectedseat.domain.ticket.model.TicketPrice;
import io.nbc.selectedseat.domain.ticket.model.TicketRating;
import io.nbc.selectedseat.domain.ticket.repository.TicketPriceRepository;
import io.nbc.selectedseat.domain.ticket.service.exception.ExistTicketPriceException;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketPriceWriter {

    private final TicketPriceRepository ticketPriceRepository;

    public TicketPriceInfo createTicketPrice(
        final Long concertId,
        final String ticketRatingString,
        final Long price
    ) {
        TicketRating ticketRating = TicketRating.valueOf(ticketRatingString);

        if (findTicketPrice(concertId, ticketRating).isPresent()) {
            throw new ExistTicketPriceException("이미 등록된 등급의 콘서트 티켓 가격입니다. 수정을 이용해주세요");
        }

        TicketPrice ticketPrice = ticketPriceRepository.save(
            TicketPrice.builder()
                .concertId(concertId)
                .ticketRating(ticketRating)
                .price(price)
                .build()
        );
        return TicketPriceInfo.from(ticketPrice);
    }

    public TicketPriceInfo updateTicketPrice(final Long ticketId, final Long changePrice) {
        findTicketPriceById(ticketId);
        TicketPrice ticketPrice = ticketPriceRepository.updateTicketPrice(ticketId, changePrice);
        return TicketPriceInfo.from(ticketPrice);
    }

    public void deleteTicketPrice(final Long ticketId) {
        findTicketPriceById(ticketId);
        ticketPriceRepository.deleteTicketPrice(ticketId);
    }

    public TicketPrice findTicketPriceById(final Long ticketId) {
        return ticketPriceRepository.findById(ticketId)
            .orElseThrow(() -> new NoSuchElementException("존재하지 않는 ticketPrice 입니다"));
    }

    private Optional<TicketPrice> findTicketPrice(
        final Long concertId,
        final TicketRating ticketRating
    ) {
        return ticketPriceRepository.findByConcertIdAndTicketRating(concertId, ticketRating);
    }
}
