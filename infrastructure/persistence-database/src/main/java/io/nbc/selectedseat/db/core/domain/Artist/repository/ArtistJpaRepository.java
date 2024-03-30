package io.nbc.selectedseat.db.core.domain.Artist.repository;

import io.nbc.selectedseat.db.core.domain.Artist.entity.ArtistEntity;
import io.nbc.selectedseat.domain.artist.model.Artist;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArtistJpaRepository extends JpaRepository<ArtistEntity, Long> {

    @Query("SELECT a FROM ArtistEntity a WHERE a.artistId IN :artistIds")
    List<Artist> findArtistsByIdList(final List<Long> artistIds);
}
