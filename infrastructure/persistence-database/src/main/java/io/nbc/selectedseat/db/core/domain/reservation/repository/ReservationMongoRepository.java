package io.nbc.selectedseat.db.core.domain.reservation.repository;

import io.nbc.selectedseat.db.core.domain.reservation.entity.ReservationDocument;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationMongoRepository extends
    MongoRepository<ReservationDocument, String> {

    Optional<ReservationDocument> findByReservationId(Long concertId);
}
