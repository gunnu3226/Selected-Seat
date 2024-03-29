package io.nbc.selectedseat.domain.artist.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import io.nbc.selectedseat.db.core.domain.Artist.entity.ArtistEntity;
import io.nbc.selectedseat.db.core.domain.Artist.repository.ArtistJpaRepository;
import io.nbc.selectedseat.domain.artist.dto.CreateArtistRequestDTO;
import io.nbc.selectedseat.domain.artist.dto.GetArtistResponseDTO;
import io.nbc.selectedseat.domain.artist.model.Artist;
import io.nbc.selectedseat.domain.artist.repository.ArtistRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class ArtistServiceTest {

    @Mock
    ArtistRepository artistRepository;

    @Mock
    ArtistJpaRepository artistJpaRepository;

    @InjectMocks
    ArtistService artistService;


    @Test
    @DisplayName("아티스트 등록 테스트")
    public void createArtistTest() {
        //given
        String name = "name";
        String profile = "profile";

        CreateArtistRequestDTO requestDTO = new CreateArtistRequestDTO(name, profile);

        Artist artist = new Artist(name, profile);

        ReflectionTestUtils.setField(artist, "artistId", 1L);

        given(artistRepository.save(any(Artist.class))).willReturn(artist);
        //when
        Long id = artistService.createArtist(requestDTO);
        //then
        assertEquals(1L, id);
    }

    @Test
    @DisplayName("아티스트 조회 테스트")
    public void getArtistTest() {
        //given
        Long artistId = 1L;
        String name = "name";
        String profile = "profile";

        Artist artist = new Artist(name, profile);
        ReflectionTestUtils.setField(artist, "artistId", 1L);

        ArtistEntity artistEntity = new ArtistEntity(1L, name, profile);

        given(artistJpaRepository.findById(artistId)).willReturn(Optional.of(artistEntity));
        //when
        GetArtistResponseDTO responseDTO = artistService.getArtist(artistId);
        //then
        assertEquals(1L, responseDTO.artistId());
        assertEquals(name, responseDTO.name());
        assertEquals(profile, responseDTO.profile());
    }
}
