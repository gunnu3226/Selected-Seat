package io.nbc.selectedseat.db.core.domain.concert.entity;

import io.nbc.selectedseat.db.core.domain.common.BaseEntity;
import io.nbc.selectedseat.domain.concert.model.Performer;
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
@Table(name = "performers")
public class PerformerEntity extends BaseEntity {

    @Id
    @Column(name = "performer_id")
    private Long concertRatingId;

    @Column(name = "artist_id", nullable = false)
    private Long artistId;

    @Column(name = "concert_id", nullable = false)
    private Long concertId;

    public Performer toModel() {
        return Performer.builder()
            .performerId(concertRatingId)
            .artistId(artistId)
            .concertId(concertId)
            .createdAt(getCreatedAt())
            .modifiedAt(getModifiedAt())
            .deletedAt(getDeletedAt())
            .build();
    }

}
