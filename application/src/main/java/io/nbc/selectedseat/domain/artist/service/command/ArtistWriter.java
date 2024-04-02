package io.nbc.selectedseat.domain.artist.service.command;

import io.nbc.selectedseat.domain.artist.dto.CreateArtistRequestDTO;
import io.nbc.selectedseat.domain.artist.model.Artist;
import io.nbc.selectedseat.domain.artist.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ArtistWriter {

    private final ArtistRepository artistRepository;

    public Long createArtist(final CreateArtistRequestDTO artistRequestDTO) {
        String name = artistRequestDTO.name();
        String profile = artistRequestDTO.profile();

        Artist artist = new Artist(name, profile);

        return artistRepository.save(artist);
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

    public Artist getArtistById(final Long artistId) {
        return artistRepository.getArtist(artistId);
    }

}
