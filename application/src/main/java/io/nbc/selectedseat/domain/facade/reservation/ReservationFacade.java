package io.nbc.selectedseat.domain.facade.reservation;

import io.nbc.selectedseat.domain.concert.dto.ConcertDateResponseDTO;
import io.nbc.selectedseat.domain.concert.dto.GetConcertResponseDTO;
import io.nbc.selectedseat.domain.concert.service.query.ConcertReader;
import io.nbc.selectedseat.domain.member.dto.MemberInfo;
import io.nbc.selectedseat.domain.member.service.query.MemberReader;
import io.nbc.selectedseat.domain.reservation.service.command.ReservationWriter;
import io.nbc.selectedseat.domain.ticket.dto.TicketInfo;
import io.nbc.selectedseat.domain.ticket.dto.TicketPriceInfo;
import io.nbc.selectedseat.domain.ticket.service.query.TicketPriceReader;
import io.nbc.selectedseat.domain.ticket.service.query.TicketReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationFacade {

    private final ConcertReader concertReader;
    private final MemberReader memberReader;
    private final TicketReader ticketReader;
    private final TicketPriceReader ticketPriceReader;
    private final ReservationWriter reservationWriter;

    @Transactional
    public Long createReservation(
        final Long concertId,
        final Long memberId,
        final Long ticketId,
        final Long ticketPriceId,
        final Long concertDateId
    ) {
        GetConcertResponseDTO concert = concertReader.getConcert(concertId);

        ConcertDateResponseDTO concertDate
            = concertReader.getConcertDate(concertDateId);

        MemberInfo memberInfo = memberReader.getMemberById(memberId);

        TicketInfo ticket = ticketReader.getTicket(ticketId);
        System.out.println(ticket.concertId() + " : " + ticket.ticketRating());
        TicketPriceInfo priceInfo = ticketPriceReader.getTicketPriceInfoByConcertIdAndRating(
            ticket.concertId(),
            ticket.ticketRating()
        );

        Long reservationId = reservationWriter.createReservation(
            concertId,
            memberId,
            ticketId,
            ticketPriceId
        );

        reservationWriter.createReservationDocument(
            reservationId,
            memberInfo.email(),
            concert.name(),
            concert.hall(),
            ticket.ticketNumber(),
            priceInfo.price(),
            concertDate.concertDate()
        );

        return reservationId;
    }
}
