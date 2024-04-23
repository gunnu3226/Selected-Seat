package io.nbc.selectedseat.db.core.domain.concert.adaptor;

import static io.nbc.selectedseat.db.core.domain.concert.entity.QConcertEntity.concertEntity;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.nbc.selectedseat.db.core.domain.concert.entity.ConcertDateEntity;
import io.nbc.selectedseat.db.core.domain.concert.entity.ConcertEntity;
import io.nbc.selectedseat.db.core.domain.concert.entity.ConcertRatingEntity;
import io.nbc.selectedseat.db.core.domain.concert.repository.ConcertDateJpaRepository;
import io.nbc.selectedseat.db.core.domain.concert.repository.ConcertJpaRepository;
import io.nbc.selectedseat.db.core.domain.concert.repository.ConcertRatingJpaRepository;
import io.nbc.selectedseat.db.core.domain.concert.repository.temp.RegionJpaRepository;
import io.nbc.selectedseat.db.core.domain.concert.repository.temp.StateJpaRepository;
import io.nbc.selectedseat.domain.category.repository.CategoryRepository;
import io.nbc.selectedseat.domain.concert.dto.ConcertSearchMapper;
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

    //todo : Temporary settings for performance measurement
    private final JPAQueryFactory jpaQueryFactory;
    private final RegionJpaRepository regionJpaRepository;
    private final CategoryRepository categoryRepository;
    private final StateJpaRepository stateJpaRepository;

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

    //todo : Temporary settings for performance measurement
    @Override
    public List<Concert> searchConcertByTextAndFilter(ConcertSearchMapper concertSearchMapper,
        int page,
        int size) {

        List<ConcertEntity> concertEntities = jpaQueryFactory
            .select(concertEntity)
            .from(concertEntity)
            .where(concertEntity.name.contains(concertSearchMapper.getText()))
            .where(concertRegionEq(concertSearchMapper.getRegion()))
            .where(concertCategoryEq(concertSearchMapper.getCategory()))
            .where(concertStateEq(concertSearchMapper.getState()))
            .where(concertRatingEq(concertSearchMapper.getConcertRating()))
            .offset((long) page * size)
            .limit(size)
            .fetch();

        return concertEntities.stream()
            .map(ConcertEntity::toModel)
            .toList();
    }

    private BooleanExpression concertRegionEq(String region) {
        if (region == null) {
            return null;
        }
        Long regionId = regionJpaRepository.findByName(region).getRegionId();
        return concertEntity.regionId.eq(regionId);
    }

    private BooleanExpression concertCategoryEq(String category) {
        if (category == null) {
            return null;
        }
        Long id = categoryRepository.findByName(category).get().getCategoryId();
        return concertEntity.categoryId.eq(id);
    }

    private BooleanExpression concertStateEq(String state) {

        if (state == null) {
            return null;
        }
        Long id = stateJpaRepository.findByName(state).getStateId();
        return concertEntity.stateId.eq(id);
    }

    private BooleanExpression concertRatingEq(String rating) {
        if (rating == null) {
            return null;
        }
        Long id = concertRatingJpaRepository.findByName(rating).getConcertRatingId();
        return concertEntity.ratingId.eq(id);
    }


}
