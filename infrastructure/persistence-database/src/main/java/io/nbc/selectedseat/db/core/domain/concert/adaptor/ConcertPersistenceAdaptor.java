package io.nbc.selectedseat.db.core.domain.concert.adaptor;

import io.nbc.selectedseat.db.core.domain.concert.entity.ConcertDateEntity;
import io.nbc.selectedseat.db.core.domain.concert.entity.ConcertEntity;
import io.nbc.selectedseat.db.core.domain.concert.entity.ConcertRatingEntity;
import io.nbc.selectedseat.db.core.domain.concert.repository.ConcertDateJpaRepository;
import io.nbc.selectedseat.db.core.domain.concert.repository.ConcertJpaRepository;
import io.nbc.selectedseat.db.core.domain.concert.repository.ConcertRatingJpaRepository;
import io.nbc.selectedseat.domain.concert.model.Concert;
import io.nbc.selectedseat.domain.concert.model.ConcertDate;
import io.nbc.selectedseat.domain.concert.model.ConcertRating;
import io.nbc.selectedseat.domain.concert.repository.ConcertRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ConcertPersistenceAdaptor implements ConcertRepository {

    private final ConcertJpaRepository concertJpaRepository;
    private final ConcertRatingJpaRepository concertRatingJpaRepository;
    private final ConcertDateJpaRepository concertDateJpaRepository;
    @Override
    public Long save(final Concert concert) {
        return concertJpaRepository.save(ConcertEntity.from(concert)).getConcertId();
    }

    @Override
    public Optional<Concert> findById(final Long concertId) {
        return concertJpaRepository.findById(concertId)
            .map(ConcertEntity::toModel);
    }

    @Override
    public Concert update(final Concert concert) {
        return concertJpaRepository.save(ConcertEntity.from(concert)).toModel();
    }

    @Override
    public void delete(final Long concertId) {
        concertJpaRepository.deleteById(concertId);
    }

    @Override
    public List<Concert> getConcerts() {
        return concertJpaRepository.findAll().stream()
            .map(ConcertEntity::toModel)
            .toList();
    }

    @Override
    public List<Concert> getConcertsByConcertIds(final List<Long> concertIds) {
        return concertJpaRepository.findAllById(concertIds)
            .stream().map(ConcertEntity::toModel)
            .toList();
    }

    @Override
    public Optional<ConcertRating> getConcertRating(final Long ratingId) {
        return concertRatingJpaRepository.findById(ratingId)
            .map(ConcertRatingEntity::toModel);
    }

    @Override
    public List<ConcertDate> getConcertDates(final Long concertId) {
        return concertDateJpaRepository.findAllByConcertId(concertId).stream()
            .map(ConcertDateEntity::toModel)
            .toList();
    }

    @Override
    public List<Concert> getConcertsByCategory(final Long categoryId) {
        return concertJpaRepository.findAllByCategoryId(categoryId).stream()
            .map(ConcertEntity::toModel)
            .toList();
    }

    @Override
    public Optional<ConcertDate> getConcertDate(final Long concertId) {
        return concertDateJpaRepository.findById(concertId)
            .map(ConcertDateEntity::toModel);
    }
}
