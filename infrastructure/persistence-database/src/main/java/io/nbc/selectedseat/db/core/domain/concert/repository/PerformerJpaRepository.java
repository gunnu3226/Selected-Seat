package io.nbc.selectedseat.db.core.domain.concert.repository;

import io.nbc.selectedseat.db.core.domain.concert.entity.PerformerEntity;
import io.nbc.selectedseat.domain.concert.model.Performer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformerJpaRepository extends JpaRepository<PerformerEntity, Long> {

    List<PerformerEntity> findByConcertId(Long concertId);
}
