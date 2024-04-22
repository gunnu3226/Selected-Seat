package io.nbc.selectedseat.web.domain.concert.user;

import io.nbc.selectedseat.common.WebPage;
import io.nbc.selectedseat.domain.concert.dto.ConcertDateResponseDTO;
import io.nbc.selectedseat.domain.concert.dto.GetConcertRatingResponseDTO;
import io.nbc.selectedseat.domain.concert.dto.GetConcertResponseDTO;
import io.nbc.selectedseat.domain.concert.service.dto.ConcertDetailInfo;
import io.nbc.selectedseat.domain.concert.service.dto.ConcertSearchRequestDTO;
import io.nbc.selectedseat.domain.concert.service.dto.SearchSuggestionResponseDTO;
import io.nbc.selectedseat.domain.concert.service.query.ConcertReader;
import io.nbc.selectedseat.web.common.dto.ResponseDTO;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/concerts")
@RequiredArgsConstructor
public class ConcertController {

    private final ConcertReader concertReader;

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
        @RequestParam("category") Long categoryId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
            ResponseDTO.<List<GetConcertResponseDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("콘서트 전체 조회 성공")
                .data(concertReader.getConcertsByCategory(categoryId))
                .build()
        );
    }

    @GetMapping("/rating/{ratingId}")
    public ResponseEntity<ResponseDTO<GetConcertRatingResponseDTO>> getConcertRating(
        @PathVariable Long ratingId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
            ResponseDTO.<GetConcertRatingResponseDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("콘서트 등급 조회 성공")
                .data(concertReader.getConcertRating(ratingId))
                .build()
        );
    }

    @GetMapping("/{concertId}/dates")
    public ResponseEntity<ResponseDTO<List<ConcertDateResponseDTO>>> getConcertDates(
        @PathVariable Long concertId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
            ResponseDTO.<List<ConcertDateResponseDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("콘서트 일정 조회 성공")
                .data(concertReader.getConcertDates(concertId))
                .build()
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseDTO<WebPage<List<ConcertDetailInfo>>>> searchConcerts(
        @RequestParam("text") String text,
        @RequestParam(value = "regions", required = false) List<String> regions,
        @RequestParam(value = "categories", required = false) List<String> categories,
        @RequestParam(value = "states", required = false) List<String> states,
        @RequestParam(value = "concertRatings", required = false) List<String> concertRatings,
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "10") Integer size
    ) throws IOException {
        ConcertSearchRequestDTO requestDTO = new ConcertSearchRequestDTO(
            text,
            regions,
            categories,
            states,
            concertRatings
        );

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
        @RequestParam("text") String text
    ) throws IOException {
        List<SearchSuggestionResponseDTO> response = concertReader.searchSuggestions(
            text);
        return ResponseEntity.status(HttpStatus.OK).body(
            ResponseDTO.<List<SearchSuggestionResponseDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("자동완성 검색어 추천 성공")
                .data(response)
                .build()
        );
    }
}
