package io.nbc.selectedseat.db.core.domain.concert.repository;

import io.nbc.selectedseat.db.core.domain.concert.entity.PerformerEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformerJpaRepository extends JpaRepository<PerformerEntity, Long> {

    List<PerformerEntity> findByConcertId(Long concertId);

    List<PerformerEntity> findByArtistId(Long artistId);
}
