package io.nbc.selectedseat.web.domain.concert.dto.request;

import io.nbc.selectedseat.domain.concert.dto.ConcertInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record UpdateConcertRequestDTO(
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

    public ConcertInfo toConcertInfo() {
        return new ConcertInfo(
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
