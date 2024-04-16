package io.nbc.selectedseat.web.domain.artist.user;

import io.nbc.selectedseat.domain.artist.dto.GetArtistResponseDTO;
import io.nbc.selectedseat.domain.artist.service.query.ArtistReader;
import io.nbc.selectedseat.web.common.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/artists")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistReader artistReader;

    @GetMapping("/{artistId}")
    public ResponseEntity<ResponseDTO<GetArtistResponseDTO>> getArtist(
        @PathVariable Long artistId
    ) {
        GetArtistResponseDTO responseDTO = artistReader.getArtist(artistId);
        return ResponseEntity.ok().body(
            ResponseDTO.<GetArtistResponseDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("아티스트 조회가 완료되었습니다")
                .data(responseDTO)
                .build()
        );
    }
}
