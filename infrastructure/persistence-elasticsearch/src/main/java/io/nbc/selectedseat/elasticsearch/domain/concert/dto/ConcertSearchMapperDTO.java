package io.nbc.selectedseat.elasticsearch.domain.concert.dto;

import java.util.List;

public record ConcertSearchMapperDTO(
    String text,
    List<String> regions,
    List<String> categories,
    List<String> states,
    List<String> concertRatings
) {

}
