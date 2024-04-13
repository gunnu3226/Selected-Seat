package io.nbc.selectedseat.domain.concert.dto;

import io.nbc.selectedseat.domain.concert.model.Concert;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

public record GetConcertResponseDTO(
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

    public static GetConcertResponseDTO from(Concert concert) {
        return new GetConcertResponseDTO(
            concert.getConcertId(),
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
