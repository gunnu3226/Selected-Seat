package io.nbc.selectedseat.web.domain.concert;

import io.nbc.selectedseat.domain.concert.service.ConcertService;
import io.nbc.selectedseat.web.common.dto.ResponseDTO;
import io.nbc.selectedseat.web.domain.concert.dto.request.CreateConcertRequestDTO;
import io.nbc.selectedseat.web.domain.concert.dto.request.UpdateConcertRequestDTO;
import io.nbc.selectedseat.web.domain.concert.dto.response.ConcertResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/concerts")
@RequiredArgsConstructor
public class ConcertController {

    private final ConcertService concertService;

    @PostMapping
    public ResponseEntity<ResponseDTO<ConcertResponseDTO>> createCategory(
        //TODO : @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody @Valid CreateConcertRequestDTO requestDTO
    ) {
        //TODO : userDetails.getUser(); admin check

        ConcertResponseDTO responseDTO = ConcertResponseDTO.from(
            concertService.createConcert(requestDTO.toConcertInfo())
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(
            ResponseDTO.<ConcertResponseDTO>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("콘서트가 생성되었습니다")
                .data(responseDTO)
                .build()
        );
    }

    @PutMapping("/{concertId}")
    public ResponseEntity<ResponseDTO<ConcertResponseDTO>> updateConcert(
        //TODO : @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long concertId,
        @RequestBody @Valid UpdateConcertRequestDTO requestDTO
    ) {
        //TODO : userDetails.getUser(); admin check

        ConcertResponseDTO responseDTO = ConcertResponseDTO.from(
            concertService.updateConcert(concertId, requestDTO.toConcertInfo())
        );

        return ResponseEntity.status(HttpStatus.OK).body(
            ResponseDTO.<ConcertResponseDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("콘서트가 수정되었습니다")
                .data(responseDTO)
                .build()
        );
    }

}
