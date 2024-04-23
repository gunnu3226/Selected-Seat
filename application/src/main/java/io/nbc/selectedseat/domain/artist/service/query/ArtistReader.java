package io.nbc.selectedseat.domain.artist.service.query;

import io.nbc.selectedseat.domain.artist.dto.GetArtistResponseDTO;
import io.nbc.selectedseat.domain.artist.model.Artist;
import io.nbc.selectedseat.domain.artist.repository.ArtistRepository;
import io.nbc.selectedseat.domain.concert.model.Performer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArtistReader {

    private final ArtistRepository artistRepository;

    public List<Artist> getArtistsByIds(final List<Long> artistIds) {
        return artistRepository.findArtistsByIdList(artistIds);
    }

    public GetArtistResponseDTO getArtist(final Long artistId) {
        Artist artist = getArtistById(artistId);
        return GetArtistResponseDTO.from(artist);
    }

    public Artist getArtistById(final Long artistId) {
        return artistRepository.getArtist(artistId);
    }

    public List<Artist> getArtistsByPerformerId(final List<Performer> performers) {
        return performers.stream()
            .map(Performer::getArtistId)
            .map(artistRepository::getArtist)
            .toList();
    }
}
