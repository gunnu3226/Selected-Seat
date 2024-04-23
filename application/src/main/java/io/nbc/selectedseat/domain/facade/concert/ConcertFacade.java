package io.nbc.selectedseat.domain.facade.concert;

import io.nbc.selectedseat.domain.artist.model.Artist;
import io.nbc.selectedseat.domain.artist.service.query.ArtistReader;
import io.nbc.selectedseat.domain.concert.dto.PerformerInfo;
import io.nbc.selectedseat.domain.concert.model.Performer;
import io.nbc.selectedseat.domain.concert.service.query.ConcertReader;
import io.nbc.selectedseat.domain.concert.service.query.PerformerReader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class ConcertFacade {

    private final ConcertReader concertReader;
    private final PerformerReader performerReader;
    private final ArtistReader artistReader;

    public List<PerformerInfo> getConcertPerformers(final Long concertId) {
        concertReader.getConcertPerformers(concertId);

        List<Performer> performers = performerReader.getPerformerByConcertId(concertId);
        List<Artist> artists = artistReader.getArtistsByPerformerId(performers);

        return artists.stream()
            .map(artist -> new PerformerInfo(artist.getName(), artist.getProfile()))
            .toList();
    }
}
