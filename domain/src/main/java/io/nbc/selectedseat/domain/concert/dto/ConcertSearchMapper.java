package io.nbc.selectedseat.domain.concert.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConcertSearchMapper {

    private String text;
    private String region;
    private String category;
    private String state;
    private String concertRating;

    public ConcertSearchMapper(
        String text,
        String region,
        String category,
        String state,
        String concertRating
    ) {
        this.text = text;
        this.region = region;
        this.category = category;
        this.state = state;
        this.concertRating = concertRating;
    }

}
