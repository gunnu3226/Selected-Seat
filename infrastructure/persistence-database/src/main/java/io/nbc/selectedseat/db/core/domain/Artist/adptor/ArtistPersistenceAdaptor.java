package io.nbc.selectedseat.db.core.domain.Artist.adptor;

import io.nbc.selectedseat.db.core.domain.Artist.entity.ArtistEntity;
import io.nbc.selectedseat.db.core.domain.Artist.repository.ArtistJpaRepository;
import io.nbc.selectedseat.domain.artist.model.Artist;
import io.nbc.selectedseat.domain.artist.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ArtistPersistenceAdaptor implements ArtistRepository {

    private final ArtistJpaRepository artistJpaRepository;

    @Override
    public Artist save(final Artist artist) {
        return artistJpaRepository.save(new ArtistEntity(artist)).toModel();
    }
}
