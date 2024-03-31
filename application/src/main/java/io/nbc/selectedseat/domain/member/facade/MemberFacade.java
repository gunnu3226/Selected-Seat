package io.nbc.selectedseat.domain.member.facade;

import io.nbc.selectedseat.domain.artist.model.Artist;
import io.nbc.selectedseat.domain.artist.service.ArtistService;
import io.nbc.selectedseat.domain.concert.model.Concert;
import io.nbc.selectedseat.domain.concert.service.query.ConcertReader;
import io.nbc.selectedseat.domain.member.facade.dto.FollowArtistsInfo;
import io.nbc.selectedseat.domain.member.facade.dto.TicketDetailInfo;
import io.nbc.selectedseat.domain.member.service.FollowService;
import io.nbc.selectedseat.domain.reservation.model.Reservation;
import io.nbc.selectedseat.domain.reservation.service.query.ReservationReader;
import io.nbc.selectedseat.domain.ticket.model.TicketAndPrice;
import io.nbc.selectedseat.domain.ticket.service.query.TicketReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberFacade {

    private final FollowService followService;
    private final ArtistService artistService;
    private final ReservationReader reservationReader;
    private final TicketReader ticketReader;
    private final ConcertReader concertReader;

    @Transactional(readOnly = true)
    public List<FollowArtistsInfo> getFollowArtists(final Long memberId) {
        List<Long> followArtistIds = followService.getFollowArtistIds(memberId);
        List<Artist> artists = artistService.getArtistsByIds(followArtistIds);
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

        Map<Long, Concert> concertMap = new HashMap<>();
        for (Concert concert : concerts) {
            concertMap.put(concert.getConcertId(), concert);
        }

        return ticketAndPrices.stream()
            .map(ticketAndPrice -> TicketDetailInfo.from(
                ticketAndPrice,
                concertMap.get(ticketAndPrice.getConcertId())))
            .collect(Collectors.toList());
    }
}
