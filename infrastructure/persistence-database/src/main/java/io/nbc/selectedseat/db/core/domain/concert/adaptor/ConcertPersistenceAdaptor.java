package io.nbc.selectedseat.db.core.domain.concert.adaptor;

import io.nbc.selectedseat.db.core.domain.concert.entity.ConcertEntity;
import io.nbc.selectedseat.db.core.domain.concert.repository.ConcertJpaRepository;
import io.nbc.selectedseat.domain.concert.model.Concert;
import io.nbc.selectedseat.domain.concert.repository.ConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ConcertPersistenceAdaptor implements ConcertRepository {

    private final ConcertJpaRepository concertJpaRepository;

    @Override
    public Long save(final Concert concert) {
        return concertJpaRepository.save(ConcertEntity.from(concert)).getConcertId();
    }
}
