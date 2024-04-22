package io.nbc.selectedseat.domain.concert.dto;

import io.nbc.selectedseat.domain.concert.model.Concert;
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

    //todo : Temporary settings for performance measurement
    public static ConcertInfo from(Concert concert) {
        return new ConcertInfo(
            concert.getRatingId(),
            concert.getStateId(),
            concert.getRegionId(),
            concert.getCategoryId(),
            concert.getName(),
            concert.getStartedAt(),
            concert.getEndedAt(),
            concert.getThumbnail(),
            concert.getHall(),
            concert.getTicketAmount()
        );
    }
}
