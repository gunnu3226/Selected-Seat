package io.nbc.selectedseat.db.core.domain.concert.entity;

import io.nbc.selectedseat.db.core.domain.common.BaseEntity;
import io.nbc.selectedseat.domain.concert.model.Concert;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "concerts")
@SQLRestriction(value = "deleted_at is NULL")
@SQLDelete(sql = "update concerts set deleted_at = NOW() where concert_id = ?")
public class ConcertEntity extends BaseEntity {

    @Id
    @Column(name = "concert_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long concertId;

    @Column(name = "rating_id", nullable = false)
    public Long ratingId;

    @Column(name = "stateId", nullable = false)
    public Long stateId;

    @Column(name = "region_id", nullable = false)
    public Long regionId;

    @Column(name = "category_id", nullable = false)
    public Long categoryId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "started_at", updatable = false)
    public LocalDateTime startedAt;

    @Column(name = "ended_at", updatable = false)
    public LocalDateTime endedAt;

    @Column(name = "thumbnail", nullable = false)
    private String thumbnail;

    @Column(name = "hall", nullable = false)
    private String hall;

    @Column(name = "ticket_amount", nullable = false)
    private Long ticketAmount;

    public static ConcertEntity from(final Concert concert) {
        return ConcertEntity.builder()
            .concertId(concert.getConcertId())
            .ratingId(concert.getRatingId())
            .stateId(concert.getStateId())
            .regionId(concert.getRegionId())
            .categoryId(concert.getCategoryId())
            .name(concert.getName())
            .startedAt(concert.getStartedAt())
            .endedAt(concert.getEndedAt())
            .thumbnail(concert.getThumbnail())
            .hall(concert.getHall())
            .ticketAmount(concert.getTicketAmount())
            .build();
    }

    public Concert toModel() {
        return new Concert(
            concertId,
            ratingId,
            stateId,
            regionId,
            categoryId,
            name,
            startedAt,
            endedAt,
            thumbnail,
            hall,
            ticketAmount
        );
    }

}
