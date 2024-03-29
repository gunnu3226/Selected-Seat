package io.nbc.selectedseat.db.core.domain.Artist.repository;

import io.nbc.selectedseat.db.core.domain.Artist.entity.ArtistJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistJpaRepository extends JpaRepository<ArtistJpaEntity, Long> {

}
