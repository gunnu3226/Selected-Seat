package io.nbc.selectedseat.web.domain.concert;

import io.nbc.selectedseat.common.WebPage;
import io.nbc.selectedseat.domain.concert.dto.GetConcertResponseDTO;
import io.nbc.selectedseat.domain.concert.service.command.ConcertWriter;
import io.nbc.selectedseat.domain.concert.service.dto.ConcertDetailInfo;
import io.nbc.selectedseat.domain.concert.service.dto.ConcertSearchRequestDTO;
import io.nbc.selectedseat.domain.concert.service.dto.SearchSuggestionResponseDTO;
import io.nbc.selectedseat.domain.concert.service.query.ConcertReader;
import io.nbc.selectedseat.web.common.dto.ResponseDTO;
import io.nbc.selectedseat.web.domain.concert.dto.request.CreateConcertRequestDTO;
import io.nbc.selectedseat.web.domain.concert.dto.request.SearchSuggestionRequestDto;
import io.nbc.selectedseat.web.domain.concert.dto.request.UpdateConcertRequestDTO;
import io.nbc.selectedseat.web.domain.concert.dto.response.ConcertResponseDTO;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/concerts")
@RequiredArgsConstructor
public class ConcertController {

    private final ConcertWriter concertWriter;
    private final ConcertReader concertReader;

    @PostMapping
    public ResponseEntity<ResponseDTO<ConcertResponseDTO>> createCategory(
        //TODO : @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody @Valid CreateConcertRequestDTO requestDTO
    ) {
        //TODO : userDetails.getUser(); admin check

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

    @PutMapping("/{concertId}")
    public ResponseEntity<ResponseDTO<ConcertResponseDTO>> updateConcert(
        //TODO : @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long concertId,
        @RequestBody @Valid UpdateConcertRequestDTO requestDTO
    ) {
        //TODO : userDetails.getUser(); admin check

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

    @DeleteMapping("/{concertId}")
    public ResponseEntity<ResponseDTO<Void>> deleteConcert(
        //TODO : @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long concertId
    ) {
        //TODO : userDetails.getUser(); admin check

        concertWriter.deleteConcert(concertId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{concertId}")
    public ResponseEntity<ResponseDTO<GetConcertResponseDTO>> getConcert(
        @PathVariable Long concertId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
            ResponseDTO.<GetConcertResponseDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("콘서트 조회 성공")
                .data(concertReader.getConcert(concertId))
                .build()
        );
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<GetConcertResponseDTO>>> getConcerts(
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
            ResponseDTO.<List<GetConcertResponseDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("콘서트 전체 조회 성공")
                .data(concertReader.getConcerts())
                .build()
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseDTO<WebPage<List<ConcertDetailInfo>>>> searchConcerts(
        @RequestBody ConcertSearchRequestDTO requestDTO,
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "10") Integer size
    ) throws IOException {
        WebPage<List<ConcertDetailInfo>> searchResponse = concertReader.searchConcertByTextAndFilter(
            requestDTO, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(
            ResponseDTO.<WebPage<List<ConcertDetailInfo>>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("콘서트 검색 성공")
                .data(searchResponse)
                .build()
        );
    }

    @GetMapping("/search/suggestions")
    public ResponseEntity<ResponseDTO<List<SearchSuggestionResponseDTO>>> searchSuggestions(
        @RequestBody SearchSuggestionRequestDto requestDTO
    ) throws IOException {
        List<SearchSuggestionResponseDTO> response = concertReader.searchSuggestions(
            requestDTO.keyword());
        return ResponseEntity.status(HttpStatus.OK).body(
            ResponseDTO.<List<SearchSuggestionResponseDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("자동완성 검색어 추천 성공")
                .data(response)
                .build()
        );
    }
}
