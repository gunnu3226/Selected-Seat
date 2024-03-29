package io.nbc.selectedseat.domain.artist.service;

import io.nbc.selectedseat.db.core.domain.Artist.entity.ArtistEntity;
import io.nbc.selectedseat.db.core.domain.Artist.repository.ArtistJpaRepository;
import io.nbc.selectedseat.domain.artist.dto.CreateArtistRequestDTO;
import io.nbc.selectedseat.domain.artist.dto.GetArtistResponseDTO;
import io.nbc.selectedseat.domain.artist.model.Artist;
import io.nbc.selectedseat.domain.artist.repository.ArtistRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final ArtistJpaRepository artistJpaRepository;

    @Transactional
    public Long createArtist(final CreateArtistRequestDTO artistRequestDTO) {
        String name = artistRequestDTO.name();
        String profile = artistRequestDTO.profile();

        Artist artist = new Artist(name, profile);
        Artist savedArtist = artistRepository.save(artist);

        return savedArtist.getArtistId();
    }

    public GetArtistResponseDTO getArtist(final Long artistId) {
        Artist artist = getArtistById(artistId).toModel();
        return GetArtistResponseDTO.from(artist);
    }


    @Transactional
    public Long updateArtist(
        final Long artistId,
        final String name,
        final String profile
    ) {
        ArtistEntity artistEntity = getArtistById(artistId);
        artistEntity.update(name, profile);
        return artistEntity.toModel().getArtistId();
    }

    private ArtistEntity getArtistById(final Long artistId) {
        return artistJpaRepository.findById(artistId).orElseThrow(
            () -> new EntityNotFoundException("아티스트가 존재하지 않습니다")
        );
    }

    public void deleteArtist() {

    }
}
