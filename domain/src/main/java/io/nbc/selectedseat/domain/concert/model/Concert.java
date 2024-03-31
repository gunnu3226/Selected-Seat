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
public class Concert {

    private Long concertId;
    private Long ratingId;
    private Long stateId;
    private Long regionId;
    private Long categoryId;
    private String name;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private String thumbnail;
    private String hall;
    private Long ticketAmount;
}
