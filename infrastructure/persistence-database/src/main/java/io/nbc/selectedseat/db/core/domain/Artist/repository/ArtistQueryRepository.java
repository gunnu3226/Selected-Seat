package io.nbc.selectedseat.db.core.domain.Artist.repository;

import io.nbc.selectedseat.db.core.domain.Artist.entity.ArtistEntity;
import java.util.List;

public interface ArtistQueryRepository {

    List<ArtistEntity> findArtistsByIdList(final List<Long> artistIds);
}
