package io.nbc.selectedseat.domain.concert.dto;

import io.nbc.selectedseat.domain.concert.model.ConcertRating;

public record GetConcertRatingResponseDTO(
    Long ratingId,
    String rating
) {

    public static GetConcertRatingResponseDTO from(final ConcertRating rating) {
        return new GetConcertRatingResponseDTO(
            rating.getRatingId(),
            rating.getRating()
        );
    }
}
