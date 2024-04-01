package io.nbc.selectedseat.db.core.domain.ticket.repository.ticket;

import io.nbc.selectedseat.db.core.domain.ticket.entity.TicketEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketJpaRepository extends JpaRepository<TicketEntity, Long> {

    List<TicketEntity> findAllByConcertId(final Long concertId);

    void deleteAllByConcertId(final Long concertId);
}
