package io.nbc.selectedseat.db.core.domain.artist.repository;

import io.nbc.selectedseat.db.core.domain.artist.entity.ArtistEntity;
import java.util.List;

public interface ArtistQueryRepository {

    List<ArtistEntity> findArtistsByIdList(final List<Long> artistIds);
}
