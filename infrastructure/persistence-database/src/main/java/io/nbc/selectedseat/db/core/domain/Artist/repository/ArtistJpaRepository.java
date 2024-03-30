package io.nbc.selectedseat.db.core.domain.Artist.repository;

import io.nbc.selectedseat.db.core.domain.Artist.entity.ArtistEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArtistJpaRepository extends JpaRepository<ArtistEntity, Long> {

    @Query("SELECT a FROM ArtistEntity a WHERE a.artistId IN :artistIds")
    List<ArtistEntity> findArtistsByIdList(@Param("artistIds") final List<Long> artistIds);
}
