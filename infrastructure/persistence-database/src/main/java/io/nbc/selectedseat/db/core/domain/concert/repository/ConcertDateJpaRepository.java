package io.nbc.selectedseat.db.core.domain.concert.repository;

import io.nbc.selectedseat.db.core.domain.concert.entity.ConcertDateEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertDateJpaRepository extends
    JpaRepository<ConcertDateEntity, String> {

    List<ConcertDateEntity> findAllByConcertId(Long concertId);
}
