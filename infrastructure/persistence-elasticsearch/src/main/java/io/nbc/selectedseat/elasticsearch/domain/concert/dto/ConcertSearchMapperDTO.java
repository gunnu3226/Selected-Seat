package io.nbc.selectedseat.elasticsearch.domain.concert.dto;

public record ConcertSearchMapperDTO(
    String text,
    String region,
    String category,
    String state,
    String concertRating
) {

}
