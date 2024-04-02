package io.nbc.selectedseat.domain.member.facade;

import io.nbc.selectedseat.domain.artist.model.Artist;
import io.nbc.selectedseat.domain.artist.service.query.ArtistReader;
import io.nbc.selectedseat.domain.concert.model.Concert;
import io.nbc.selectedseat.domain.concert.service.query.ConcertReader;
import io.nbc.selectedseat.domain.member.facade.dto.FollowArtistsInfo;
import io.nbc.selectedseat.domain.member.facade.dto.ReservationDetailInfo;
import io.nbc.selectedseat.domain.member.facade.dto.TicketDetailInfo;
import io.nbc.selectedseat.domain.member.service.query.FollowReader;
import io.nbc.selectedseat.domain.reservation.model.Reservation;
import io.nbc.selectedseat.domain.reservation.service.query.ReservationReader;
import io.nbc.selectedseat.domain.ticket.model.TicketAndPrice;
import io.nbc.selectedseat.domain.ticket.model.TicketPrice;
import io.nbc.selectedseat.domain.ticket.service.query.TicketPriceReader;
import io.nbc.selectedseat.domain.ticket.service.query.TicketReader;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberFacade {

    private final FollowReader followReader;
    private final ArtistReader artistReader;
    private final ReservationReader reservationReader;
    private final TicketReader ticketReader;
    private final TicketPriceReader ticketPriceReader;
    private final ConcertReader concertReader;

    @Transactional(readOnly = true)
    public List<FollowArtistsInfo> getFollowArtists(final Long memberId) {
        List<Long> followArtistIds = followReader.getFollowArtistIds(memberId);
        List<Artist> artists = artistReader.getArtistsByIds(followArtistIds);
        return artists.stream()
            .map(FollowArtistsInfo::from)
            .toList();
    }

    @Transactional(readOnly = true)
    public List<TicketDetailInfo> getTicketsByMemberId(final Long memberId) {
        List<Reservation> reservations = reservationReader.getReservationByMemberId(
            memberId);

        List<Long> ticketIds = reservations.stream()
            .map(Reservation::getTicketId)
            .toList();

        List<TicketAndPrice> ticketAndPrices = ticketReader.getTicketsAndPriceByTicketIds(
            ticketIds);

        List<Long> concertIds = reservations.stream()
            .map(Reservation::getConcertId)
            .toList();

        List<Concert> concerts = concertReader.getConcertsByConcertIds(concertIds);

        Map<Long, Concert> concertMap = concerts.stream()
            .collect(Collectors.toMap(Concert::getConcertId, Function.identity()));

        return ticketAndPrices.stream()
            .map(ticketAndPrice -> TicketDetailInfo.from(
                ticketAndPrice,
                concertMap.get(ticketAndPrice.getConcertId())))
            .toList();
    }

    @Transactional(readOnly = true)
    public List<ReservationDetailInfo> getReservationsByMemberId(final Long memberId) {
        List<Reservation> reservations = reservationReader.getReservationByMemberId(
            memberId);

        List<Long> ticketPriceIds = reservations.stream()
            .map(Reservation::getTicketPriceId)
            .toList();

        List<TicketPrice> ticketPrices = ticketPriceReader.getTicketPricesByIds(
            ticketPriceIds);

        Map<Long, TicketPrice> ticketPriceMap = ticketPrices.stream()
            .collect(Collectors.toMap(TicketPrice::getTicketPriceId, Function.identity()));

        List<Long> concertIds = reservations.stream()
            .map(Reservation::getConcertId)
            .toList();

        List<Concert> concerts = concertReader.getConcertsByConcertIds(concertIds);

        Map<Long, Concert> concertMap = concerts.stream()
            .collect(Collectors.toMap(Concert::getConcertId, Function.identity()));

        return reservations.stream()
            .map(reservation -> ReservationDetailInfo.from(
                reservation,
                ticketPriceMap.get(reservation.getTicketPriceId()),
                concertMap.get(reservation.getConcertId())))
            .toList();
    }
}
