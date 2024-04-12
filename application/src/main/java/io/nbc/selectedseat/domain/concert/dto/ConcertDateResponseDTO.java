package io.nbc.selectedseat.domain.concert.dto;

import io.nbc.selectedseat.domain.concert.model.ConcertDate;
import java.time.LocalDateTime;

public record ConcertDateResponseDTO(
    Long concertDateId,
    LocalDateTime concertDate
) {
    public static ConcertDateResponseDTO from(final ConcertDate concertDate) {
        return new ConcertDateResponseDTO(
            concertDate.getConcertDateId(),
            concertDate.getConcertDate()
        );
    }
}
