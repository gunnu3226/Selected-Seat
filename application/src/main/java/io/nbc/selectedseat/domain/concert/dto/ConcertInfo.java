package io.nbc.selectedseat.domain.concert.dto;

import java.time.LocalDateTime;

public record ConcertInfo(

    Long concertId,
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

}
