package io.nbc.selectedseat.domain.facade.reservation;

import io.nbc.selectedseat.domain.concert.dto.ConcertDateResponseDTO;
import io.nbc.selectedseat.domain.concert.dto.GetConcertResponseDTO;
import io.nbc.selectedseat.domain.concert.service.query.ConcertReader;
import io.nbc.selectedseat.domain.member.dto.MemberInfo;
import io.nbc.selectedseat.domain.member.service.query.MemberReader;
import io.nbc.selectedseat.domain.reservation.dto.ReservationInfoDTO;
import io.nbc.selectedseat.domain.reservation.service.command.ReservationWriter;
import io.nbc.selectedseat.domain.reservation.service.query.ReservationReader;
import io.nbc.selectedseat.domain.seat.command.SeatWriter;
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
@Transactional
@RequiredArgsConstructor
public class ReservationFacade {

    private final ConcertReader concertReader;
    private final MemberReader memberReader;
    private final TicketReader ticketReader;
    private final TicketPriceReader ticketPriceReader;
    private final ReservationWriter reservationWriter;
    private final ReservationReader reservationReader;
    private final SeatWriter seatWriter;

    public Long createReservation(
        final Long concertId,
        final Long memberId,
        final Long ticketId,
        final Long concertDateId
    ) {
        TicketInfo ticket = ticketReader.getTicket(ticketId);
        TicketPriceInfo priceInfo = ticketPriceReader.getTicketPriceByConcertAndRating(
            concertId,
            ticket.ticketRating()
        );

        Long reservationId = reservationWriter.createReservation(
            concertId,
            memberId,
            ticketId,
            priceInfo.ticketPriceId()
        );

        seatWriter.selectedSeat(
            concertId,
            concertDateId,
            ticketId,
            ticket.ticketRating().name(),
            ticket.ticketNumber()
        );

        return reservationId;
    }

    public void createReservationDocument(
        final Long reservationId
    ) {
        Long completedId = reservationWriter.completeReservation(reservationId);

        ReservationInfoDTO reservation = reservationReader.getReservation(
            completedId);

        GetConcertResponseDTO concert = concertReader.getConcert(
            reservation.concertId());

        TicketInfo ticket = ticketReader.getTicket(reservation.ticketId());

        MemberInfo memberInfo = memberReader.getMemberById(
            reservation.memberId());

        ConcertDateResponseDTO concertDate = concertReader.getConcertDate(
            ticket.concertDateId());

        TicketPriceInfo priceInfo = ticketPriceReader.getTicketPriceByConcertAndRating(
            ticket.concertId(),
            ticket.ticketRating()
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
    }
}
