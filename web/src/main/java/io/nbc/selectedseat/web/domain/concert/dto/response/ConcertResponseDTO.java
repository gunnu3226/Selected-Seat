package io.nbc.selectedseat.web.domain.concert.dto.response;

public record ConcertResponseDTO(
    Long concertId
) {

    public static ConcertResponseDTO from(final Long concertId) {
        return new ConcertResponseDTO(concertId);
    }
}
