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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime startedAt,
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
