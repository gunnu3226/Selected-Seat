package io.nbc.selectedseat.domain.member.facade.dto;

import io.nbc.selectedseat.domain.concert.model.Concert;
import java.time.LocalDateTime;

public record ConcertInfo(

    Long id,
    String name,
    String hall,
    String thumbnail,
    LocalDateTime startedAt,
    LocalDateTime endedAt
) {

    public static ConcertInfo from(final Concert concert) {
        return new ConcertInfo(
            concert.getConcertId(),
            concert.getName(),
            concert.getHall(),
            concert.getThumbnail(),
            concert.getStartedAt(),
            concert.getEndedAt()
        );
    }
}
