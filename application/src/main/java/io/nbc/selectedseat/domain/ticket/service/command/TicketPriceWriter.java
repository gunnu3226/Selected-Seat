package io.nbc.selectedseat.domain.ticket.service.command;

import io.nbc.selectedseat.domain.ticket.dto.TicketPriceInfo;
import io.nbc.selectedseat.domain.ticket.model.TicketPrice;
import io.nbc.selectedseat.domain.ticket.model.TicketRating;
import io.nbc.selectedseat.domain.ticket.repository.TicketPriceRepository;
import io.nbc.selectedseat.domain.ticket.service.exception.ExistTicketPriceException;
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
        final TicketRating ticketRating,
        final Long price
    ) {
        if (findTicketPrice(concertId, ticketRating).isPresent()) {
            throw new ExistTicketPriceException("이미 등록된 등급의 콘서트 티켓 가격입니다. 수정을 이용해주세요");
        }
        ;
        TicketPrice ticketPrice = ticketPriceRepository.save(
            TicketPrice.builder()
                .concertId(concertId)
                .ticketRating(ticketRating)
                .price(price)
                .build()
        );
        return TicketPriceInfo.from(ticketPrice);
    }

    private Optional<TicketPrice> findTicketPrice(
        final Long concertId,
        final TicketRating ticketRating
    ) {
        return ticketPriceRepository.findByConcertIdAndTicketRating(concertId, ticketRating);
    }
}
