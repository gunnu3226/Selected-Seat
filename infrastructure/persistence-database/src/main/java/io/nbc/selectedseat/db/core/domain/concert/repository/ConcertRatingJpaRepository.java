package io.nbc.selectedseat.db.core.domain.concert.repository;

import io.nbc.selectedseat.db.core.domain.concert.entity.ConcertRatingEntity;
import io.nbc.selectedseat.domain.concert.repository.ConcertRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertRatingJpaRepository extends
    JpaRepository<ConcertRatingEntity, Long> {

    ConcertRatingEntity findByName(String rating);
}
