package io.nbc.selectedseat.db.core.domain.concert.repository;

import io.nbc.selectedseat.db.core.domain.concert.entity.ConcertEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertJpaRepository extends JpaRepository<ConcertEntity, Long> {
    List<ConcertEntity> findAllByCategoryId(final Long categoryId);

}
