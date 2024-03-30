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
import org.springframework.data.annotation.CreatedDate;

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

    @Column(nullable = false)
    public Long ratingId;

    @Column(nullable = false)
    public Long stateId;

    @Column(nullable = false)
    public Long regionId;

    @Column(nullable = false)
    public Long categoryId;

    @Column(nullable = false)
    private String name;

    @CreatedDate
    @Column(updatable = false)
    public LocalDateTime startedAt;

    @CreatedDate
    @Column(updatable = false)
    public LocalDateTime endedAt;

    @Column(nullable = false)
    private String thumbnail;

    @Column(nullable = false)
    private String hall;

    @Column(nullable = false)
    private Long ticketAmount;

    public Concert tomodel() {
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
