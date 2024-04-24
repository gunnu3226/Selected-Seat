package io.nbc.selectedseat.db.core.domain.concert.repository.temp;

import io.nbc.selectedseat.db.core.domain.concert.entity.StateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateJpaRepository extends JpaRepository<StateEntity, Long> {

    StateEntity findByName(String state);
}
