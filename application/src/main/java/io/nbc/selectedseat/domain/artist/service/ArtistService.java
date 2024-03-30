package io.nbc.selectedseat.domain.artist.service;

import io.nbc.selectedseat.domain.artist.dto.CreateArtistRequestDTO;
import io.nbc.selectedseat.domain.artist.dto.GetArtistResponseDTO;
import io.nbc.selectedseat.domain.artist.model.Artist;
import io.nbc.selectedseat.domain.artist.repository.ArtistRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;

    public Long createArtist(final CreateArtistRequestDTO artistRequestDTO) {
        String name = artistRequestDTO.name();
        String profile = artistRequestDTO.profile();

        Artist artist = new Artist(name, profile);

        return artistRepository.save(artist);
    }

    @Transactional(readOnly = true)
    public GetArtistResponseDTO getArtist(final Long artistId) {
        Artist artist = getArtistById(artistId);
        return GetArtistResponseDTO.from(artist);
    }

    public Long updateArtist(
        final Long artistId,
        final String name,
        final String profile
    ) {
        return artistRepository.update(artistId, name, profile);
    }

    public void deleteArtist(final Long artistId) {
        artistRepository.delete(artistId);
    }

    @Transactional(readOnly = true)
    public List<Artist> getArtistsByIds(final List<Long> artistIds) {
        return artistRepository.findArtistsByIdList(artistIds);
    }

    public Artist getArtistById(final Long artistId) {
        return artistRepository.getArtist(artistId);
    }
}
