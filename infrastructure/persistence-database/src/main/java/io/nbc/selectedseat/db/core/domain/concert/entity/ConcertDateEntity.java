package io.nbc.selectedseat.db.core.domain.concert.entity;

import io.nbc.selectedseat.db.core.domain.common.BaseEntity;
import io.nbc.selectedseat.domain.concert.model.ConcertDate;
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
@Table(name = "concert_dates")
@SQLRestriction(value = "deleted_at is NULL")
@SQLDelete(sql = "update concert_dates set deleted_at = now() where concert_date_id = ?")
public class ConcertDateEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "concert_date_id")
    private Long concertDateId;
    @Column(name = "concert_id", nullable = false)
    private Long concertId;
    @Column(name = "concert_date", nullable = false)
    private LocalDateTime concertDate;

    public static ConcertDateEntity from(final ConcertDate concertDate){
        return ConcertDateEntity.builder()
            .concertId(concertDate.getConcertId())
            .concertDate(concertDate.getConcertDate())
            .build();
    }

    public ConcertDate toModel(){
        return ConcertDate.builder()
            .concertDateId(concertDateId)
            .concertId(concertId)
            .concertDate(concertDate)
            .build();
    }
}
