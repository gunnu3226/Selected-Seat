package io.nbc.selectedseat.batch.entity;

import io.nbc.selectedseat.db.core.domain.concert.entity.ConcertEntity;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConcertBatchEntity {

    private Long concertId;
    public Long ratingId;
    public Long stateId;
    public Long regionId;
    public Long categoryId;
    private String name;
    public LocalDateTime startedAt;
    public LocalDateTime endedAt;
    private String thumbnail;
    private String hall;
    private Long ticketAmount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime deletedAt;

    public static ConcertBatchEntity from(ConcertEntity concert) {
        return new ConcertBatchEntity(
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
            concert.getTicketAmount(),
            concert.getCreatedAt(),
            concert.getModifiedAt(),
            concert.getDeletedAt()
        );
    }
}
