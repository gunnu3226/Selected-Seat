package io.nbc.selectedseat.domain.concert.service.dto;

import io.nbc.selectedseat.elasticsearch.domain.concert.document.ConcertDocument;
import java.time.LocalDateTime;
import java.util.List;

public record ConcertDetailInfo(
    Long id,
    String name,
    String thumbnail,
    List<String> performers,
    String hall,
    String region,
    Long ticketAmount,
    String category,
    String state,
    String concertRating,
    LocalDateTime startedAt,
    LocalDateTime endedAt

) {

    public static ConcertDetailInfo from(ConcertDocument concertDocument) {
        return new ConcertDetailInfo(
            concertDocument.getId(),
            concertDocument.getName(),
            concertDocument.getThumbnail(),
            concertDocument.getPerformers(),
            concertDocument.getHall(),
            concertDocument.getRegion(),
            concertDocument.getTicketAmount(),
            concertDocument.getCategory(),
            concertDocument.getState(),
            concertDocument.getConcertRating(),
            concertDocument.getStartedAt(),
            concertDocument.getEndedAt()
        );
    }
}
