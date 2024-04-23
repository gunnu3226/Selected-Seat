package io.nbc.selectedseat.domain.concert.service.dto;

import java.util.List;

public record ConcertSearchRequestDTO(
    String text,
    List<String> regions,
    List<String> categories,
    List<String> states,
    List<String> concertRatings
) {

}
