package io.nbc.selectedseat.web.domain.artist.admin;

import io.nbc.selectedseat.domain.artist.dto.CreateArtistRequestDTO;
import io.nbc.selectedseat.domain.artist.service.command.ArtistWriter;
import io.nbc.selectedseat.web.common.dto.ResponseDTO;
import io.nbc.selectedseat.web.domain.artist.dto.request.ArtistRequestDTO;
import io.nbc.selectedseat.web.domain.artist.dto.response.ArtistResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/artists")
@RequiredArgsConstructor
public class ArtistAdminController {

    private final ArtistWriter artistWriter;

    @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<ResponseDTO<ArtistResponseDTO>> createArtist(
        @RequestBody @Valid ArtistRequestDTO artistRequestDTO
    ) {
        CreateArtistRequestDTO requestDTO = new CreateArtistRequestDTO(artistRequestDTO.name(),
            artistRequestDTO.profile());

        ArtistResponseDTO responseDTO = new ArtistResponseDTO(
            artistWriter.createArtist(requestDTO));

        return ResponseEntity.status(HttpStatus.CREATED).body(
            ResponseDTO.<ArtistResponseDTO>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("아티스트 등록이 완료되었습니다")
                .data(responseDTO)
                .build()
        );
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{artistId}")
    public ResponseEntity<ResponseDTO<ArtistResponseDTO>> updateArtist(
        @PathVariable Long artistId,
        @RequestBody ArtistRequestDTO requestDTO
    ) {
        ArtistResponseDTO responseDTO = new ArtistResponseDTO(
            artistWriter.updateArtist(artistId, requestDTO.name(), requestDTO.profile()));

        return ResponseEntity.status(HttpStatus.OK).body(
            ResponseDTO.<ArtistResponseDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("아티스트가 수정되었습니다")
                .data(responseDTO)
                .build()
        );
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{artistId}")
    public ResponseEntity<ResponseDTO<Void>> deleteArtist(
        @PathVariable Long artistId
    ) {
        artistWriter.deleteArtist(artistId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
            ResponseDTO.<Void>builder().build()
        );
    }
}
