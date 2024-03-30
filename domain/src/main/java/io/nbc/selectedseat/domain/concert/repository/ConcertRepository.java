package io.nbc.selectedseat.domain.concert.repository;

import io.nbc.selectedseat.domain.concert.model.Concert;
import java.util.Optional;

public interface ConcertRepository {

    Long save(final Concert concert);

    Optional<Concert> findById(final Long concertId);

    Concert update(final Concert concert);

    void delete(final Long concertId);
}
