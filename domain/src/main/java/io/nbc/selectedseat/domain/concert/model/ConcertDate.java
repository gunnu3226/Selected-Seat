package io.nbc.selectedseat.domain.concert.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConcertDate {
    private Long concertDateId;
    private Long concertId;
    private LocalDateTime concertDate;
}
