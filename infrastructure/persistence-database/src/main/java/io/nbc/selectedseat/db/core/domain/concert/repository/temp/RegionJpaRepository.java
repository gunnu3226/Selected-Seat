package io.nbc.selectedseat.db.core.domain.concert.repository.temp;

import io.nbc.selectedseat.db.core.domain.concert.entity.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionJpaRepository extends JpaRepository<RegionEntity, Long> {

    RegionEntity findByName(final String name);
}
