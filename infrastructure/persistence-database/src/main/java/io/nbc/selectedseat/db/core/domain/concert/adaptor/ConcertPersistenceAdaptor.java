package io.nbc.selectedseat.db.core.domain.concert.adaptor;

import io.nbc.selectedseat.db.core.domain.concert.entity.ConcertEntity;
import io.nbc.selectedseat.db.core.domain.concert.repository.ConcertJpaRepository;
import io.nbc.selectedseat.domain.concert.model.Concert;
import io.nbc.selectedseat.domain.concert.repository.ConcertRepository;
import java.util.List;
import java.util.Optional;
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

    @Override
    public Optional<Concert> findById(final Long concertId) {
        return concertJpaRepository.findById(concertId)
            .map(ConcertEntity::tomodel);
    }

    @Override
    public Concert update(final Concert concert) {
        return concertJpaRepository.save(ConcertEntity.from(concert)).tomodel();
    }

    @Override
    public void delete(final Long concertId) {
        concertJpaRepository.deleteById(concertId);
    }

    @Override
    public List<Concert> getConcerts() {
        return concertJpaRepository.findAll().stream()
            .map(ConcertEntity::tomodel)
            .toList();
    }

    @Override
    public List<Concert> getConcertsByConcertIds(final List<Long> concertIds) {
        return concertJpaRepository.findAllById(concertIds)
            .stream().map(ConcertEntity::tomodel)
            .toList();
    }
}
