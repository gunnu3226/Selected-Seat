package io.nbc.selectedseat.domain.artist.service;

import io.nbc.selectedseat.domain.artist.dto.CreateArtistRequestDTO;
import io.nbc.selectedseat.domain.artist.model.Artist;
import io.nbc.selectedseat.domain.artist.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;

    @Transactional
    public Long createArtist(CreateArtistRequestDTO artistRequestDTO) {
        String name = artistRequestDTO.name();
        String profile = artistRequestDTO.profile();
        Artist artist = new Artist(name, profile);
        Artist savedArtist = artistRepository.save(artist);
        return savedArtist.getArtistId();
    }

    public void getArtist() {

    }


    public void updateArtist() {

    }


    public void deleteArtist() {

    }
}
