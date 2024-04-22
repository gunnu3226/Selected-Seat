package io.nbc.selectedseat.db.core.domain.artist.repository;

import static io.nbc.selectedseat.db.core.domain.Artist.entity.QArtistEntity.artistEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.nbc.selectedseat.db.core.domain.Artist.entity.ArtistEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ArtistQueryRepositoryImpl implements
    io.nbc.selectedseat.db.core.domain.Artist.repository.ArtistQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ArtistEntity> findArtistsByIdList(List<Long> artistIds) {
        return queryFactory.selectFrom(artistEntity)
            .where(artistEntity.artistId.in(artistIds))
            .fetch();
    }
}
