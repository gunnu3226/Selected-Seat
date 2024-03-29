package io.nbc.selectedseat.web.domain.artist;

import io.nbc.selectedseat.domain.artist.dto.CreateArtistRequestDTO;
import io.nbc.selectedseat.domain.artist.dto.GetArtistResponseDTO;
import io.nbc.selectedseat.domain.artist.service.ArtistService;
import io.nbc.selectedseat.web.common.dto.ResponseDTO;
import io.nbc.selectedseat.web.domain.artist.dto.ArtistRequestDTO;
import io.nbc.selectedseat.web.domain.artist.dto.ArtistResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/artists")
@RequiredArgsConstructor
public class ArtistController {

    public final ArtistService artistService;

    @PostMapping
    public ResponseEntity<ResponseDTO<ArtistResponseDTO>> createArtist(
        @RequestBody @Valid ArtistRequestDTO artistRequestDTO
    ) {
        CreateArtistRequestDTO requestDTO = new CreateArtistRequestDTO(artistRequestDTO.name(),
            artistRequestDTO.profile());

        ArtistResponseDTO responseDTO = new ArtistResponseDTO(
            artistService.createArtist(requestDTO));

        return ResponseEntity.status(HttpStatus.CREATED).body(
            ResponseDTO.<ArtistResponseDTO>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("아티스트 등록이 완료되었습니다")
                .data(responseDTO)
                .build()
        );
    }

    @GetMapping("/{artistId}")
    public ResponseEntity<ResponseDTO<GetArtistResponseDTO>> getArtist(
        @PathVariable Long artistId
    ) {
        GetArtistResponseDTO responseDTO = artistService.getArtist(artistId);
        return ResponseEntity.ok().body(
            ResponseDTO.<GetArtistResponseDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("아티스트 조회가 완료되었습니다")
                .data(responseDTO)
                .build()
        );
    }

    @PutMapping("/{artistId}")
    public ResponseEntity<ResponseDTO<ArtistResponseDTO>> updateArtist(
        @PathVariable Long artistId,
        @RequestBody ArtistRequestDTO requestDTO
    ) {
        ArtistResponseDTO responseDTO = new ArtistResponseDTO(
            artistService.updateArtist(artistId, requestDTO.name(), requestDTO.profile()));

        return ResponseEntity.status(HttpStatus.OK).body(
            ResponseDTO.<ArtistResponseDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("아티스트가 수정되었습니다")
                .data(responseDTO)
                .build()
        );
    }

    @DeleteMapping("/{artistId}")
    public ResponseEntity<ResponseDTO<Void>> deleteArtist(
        @PathVariable Long artistId
    ) {
        artistService.deleteArtist(artistId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
            ResponseDTO.<Void>builder().build()
        );
    }
}
