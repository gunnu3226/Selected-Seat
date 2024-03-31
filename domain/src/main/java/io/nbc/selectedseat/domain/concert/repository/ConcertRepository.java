package io.nbc.selectedseat.domain.concert.repository;

import io.nbc.selectedseat.domain.concert.model.Concert;

public interface ConcertRepository {

    Long save(Concert concert);

}
