package io.nbc.selectedseat.domain.concert.service.dto;

public record ConcertSearchRequestDTO(
    String text,
    String region,
    String category,
    String state,
    String concertRating
) {

}
