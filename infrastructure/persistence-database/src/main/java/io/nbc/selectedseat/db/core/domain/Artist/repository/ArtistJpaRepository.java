package io.nbc.selectedseat.db.core.domain.artist.repository;

import io.nbc.selectedseat.db.core.domain.Artist.entity.ArtistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistJpaRepository extends JpaRepository<ArtistEntity, Long> {
}
