package io.nbc.selectedseat.db.core.domain.reservation.repository;

import io.nbc.selectedseat.db.core.domain.reservation.entity.ReservationEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationJpaRepository extends JpaRepository<ReservationEntity, Long> {

    List<ReservationEntity> findByMemberId(final Long memberId);
}
