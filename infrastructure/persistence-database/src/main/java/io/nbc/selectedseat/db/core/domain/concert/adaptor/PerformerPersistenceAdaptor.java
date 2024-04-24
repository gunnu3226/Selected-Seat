package io.nbc.selectedseat.db.core.domain.concert.adaptor;

import io.nbc.selectedseat.db.core.domain.concert.entity.PerformerEntity;
import io.nbc.selectedseat.db.core.domain.concert.repository.PerformerJpaRepository;
import io.nbc.selectedseat.domain.concert.model.Performer;
import io.nbc.selectedseat.domain.concert.repository.PerformerRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PerformerPersistenceAdaptor implements PerformerRepository {

    private final PerformerJpaRepository performerJpaRepository;

    @Override
    public List<Performer> getPerformerByConcertId(final Long concertId) {
        return performerJpaRepository.findByConcertId(concertId).stream()
            .map(PerformerEntity::toModel)
            .toList();
    }

    @Override
    public List<Performer> findAllByArtistId(Long artistId) {
        return performerJpaRepository.findByArtistId(artistId).stream()
            .map(PerformerEntity::toModel)
            .toList();
    }
}
