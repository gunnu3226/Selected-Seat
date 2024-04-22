package io.nbc.selectedseat.db.core.domain.artist.adptor;


import io.nbc.selectedseat.db.core.domain.artist.entity.ArtistEntity;
import io.nbc.selectedseat.db.core.domain.artist.repository.ArtistJpaRepository;
import io.nbc.selectedseat.domain.artist.model.Artist;
import io.nbc.selectedseat.domain.artist.repository.ArtistRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ArtistPersistenceAdaptor implements ArtistRepository {

    private final ArtistJpaRepository artistJpaRepository;
    private final io.nbc.selectedseat.db.core.domain.artist.repository.ArtistQueryRepository artistQueryRepository;

    @Override
    public Long save(final Artist artist) {
        Artist savedArtist = artistJpaRepository.save(new ArtistEntity(artist)).toModel();
        return savedArtist.getArtistId();
    }

    @Override
    public Artist getArtist(final Long artistId) {
        return getArtistById(artistId).toModel();
    }

    @Override
    public Long update(
        final Long artistId,
        final String name,
        final String profile
    ) {
        ArtistEntity artistEntity = getArtistById(artistId);
        artistEntity.update(name, profile);
        return artistEntity.getArtistId();
    }

    @Override
    public void delete(final Long artistId) {
        ArtistEntity artistEntity = getArtistById(artistId);
        artistJpaRepository.delete(artistEntity);
    }

    @Override
    public List<Artist> findArtistsByIdList(final List<Long> artistIds) {
        return artistQueryRepository.findArtistsByIdList(artistIds).stream()
            .map(ArtistEntity::toModel).toList();
    }

    private ArtistEntity getArtistById(final Long artistId) {
        return artistJpaRepository.findById(artistId).orElseThrow(
            () -> new EntityNotFoundException("아티스트가 존재하지 않습니다")
        );
    }
}
