package io.nbc.selectedseat.db.core.domain.concert.repository;

import io.nbc.selectedseat.db.core.domain.concert.entity.ConcertRatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertRatingJpaRepository extends
    JpaRepository<ConcertRatingEntity, Long> {

}
