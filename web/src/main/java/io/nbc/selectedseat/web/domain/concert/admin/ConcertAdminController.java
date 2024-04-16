package io.nbc.selectedseat.web.domain.concert.admin;

import io.nbc.selectedseat.domain.concert.service.command.ConcertWriter;
import io.nbc.selectedseat.web.common.dto.ResponseDTO;
import io.nbc.selectedseat.web.domain.concert.dto.request.CreateConcertRequestDTO;
import io.nbc.selectedseat.web.domain.concert.dto.request.UpdateConcertRequestDTO;
import io.nbc.selectedseat.web.domain.concert.dto.response.ConcertResponseDTO;
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
@RequestMapping("/api/v1/concerts")
@RequiredArgsConstructor
public class ConcertAdminController {

    private final ConcertWriter concertWriter;

    @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<ResponseDTO<ConcertResponseDTO>> createCategory(
        @RequestBody @Valid CreateConcertRequestDTO requestDTO
    ) {
        ConcertResponseDTO responseDTO = ConcertResponseDTO.from(
            concertWriter.createConcert(requestDTO.toConcertInfo())
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(
            ResponseDTO.<ConcertResponseDTO>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("콘서트가 생성되었습니다")
                .data(responseDTO)
                .build()
        );
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{concertId}")
    public ResponseEntity<ResponseDTO<ConcertResponseDTO>> updateConcert(
        @PathVariable Long concertId,
        @RequestBody @Valid UpdateConcertRequestDTO requestDTO
    ) {
        ConcertResponseDTO responseDTO = ConcertResponseDTO.from(
            concertWriter.updateConcert(concertId, requestDTO.toConcertInfo())
        );

        return ResponseEntity.status(HttpStatus.OK).body(
            ResponseDTO.<ConcertResponseDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("콘서트가 수정되었습니다")
                .data(responseDTO)
                .build()
        );
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{concertId}")
    public ResponseEntity<ResponseDTO<Void>> deleteConcert(
        @PathVariable Long concertId
    ) {
        concertWriter.deleteConcert(concertId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

