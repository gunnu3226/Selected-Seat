package io.nbc.selectedseat.domain.concert.repository;

import io.nbc.selectedseat.domain.concert.model.Performer;
import java.util.List;

public interface PerformerRepository {

    List<Performer> getPerformerByConcertId(final Long concertId);

    List<Performer> findAllByArtistId(Long artistId);
}
