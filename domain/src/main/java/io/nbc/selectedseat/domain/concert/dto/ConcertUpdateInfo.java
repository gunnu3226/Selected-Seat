package io.nbc.selectedseat.domain.concert.dto;

import java.time.LocalDateTime;

public record ConcertUpdateInfo(

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
