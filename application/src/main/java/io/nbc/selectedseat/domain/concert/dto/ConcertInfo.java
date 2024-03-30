package io.nbc.selectedseat.domain.concert.dto;

import java.time.LocalDateTime;

public record ConcertInfo(

    Long ratingId,
    Long stateId,
    Long regionId,
    Long categoryId,
    String name,
    LocalDateTime startedAt,
    LocalDateTime endedAt,
    String thumbnail,
    String hall,
    Long ticketAmount
) {

    public ConcertUpdateInfo toConcertUpdateInfo() {
        return new ConcertUpdateInfo(
            ratingId(),
            stateId(),
            regionId(),
            categoryId(),
            name(),
            startedAt(),
            endedAt(),
            thumbnail(),
            hall(),
            ticketAmount()
        );
    }
}
