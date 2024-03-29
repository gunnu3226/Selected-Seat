package io.nbc.selectedseat.db.core.domain.reservation.repository;

import io.nbc.selectedseat.db.core.domain.reservation.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationJpaRepository extends JpaRepository<ReservationEntity, Long> {

}
