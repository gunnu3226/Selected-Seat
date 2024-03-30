package io.nbc.selectedseat.domain.artist.service;

import io.nbc.selectedseat.domain.artist.dto.CreateArtistRequestDTO;
import io.nbc.selectedseat.domain.artist.dto.GetArtistResponseDTO;
import io.nbc.selectedseat.domain.artist.model.Artist;
import io.nbc.selectedseat.domain.artist.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;

    @Transactional
    public Long createArtist(final CreateArtistRequestDTO artistRequestDTO) {
        String name = artistRequestDTO.name();
        String profile = artistRequestDTO.profile();

        Artist artist = new Artist(name, profile);
        Artist savedArtist = artistRepository.save(artist);

        return savedArtist.getArtistId();
    }

    public GetArtistResponseDTO getArtist(final Long artistId) {
        Artist artist = getArtistById(artistId);
        return GetArtistResponseDTO.from(artist);
    }


    @Transactional
    public Long updateArtist(
        final Long artistId,
        final String name,
        final String profile
    ) {
        return artistRepository.update(artistId, name, profile);
    }

    @Transactional
    public void deleteArtist(final Long artistId) {
        artistRepository.delete(artistId);
    }

    private Artist getArtistById(final Long artistId) {
        return artistRepository.getArtist(artistId);
    }
}
