package io.nbc.selectedseat.db.core.domain.concert.entity;

import io.nbc.selectedseat.db.core.domain.common.BaseEntity;
import io.nbc.selectedseat.domain.concert.model.ConcertRating;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "concert_ratings")
public class ConcertRatingEntity extends BaseEntity {

    @Id
    @Column(name = "concert_rating_id")
    private Long concertRatingId;

    @Column(name = "name", nullable = false)
    private String name;

    public ConcertRating toModel(){
        return ConcertRating.builder()
            .ratingId(concertRatingId)
            .rating(name)
            .createdAt(getCreatedAt())
            .modifiedAt(getModifiedAt())
            .deletedAt(getDeletedAt())
            .build();
    }

}
