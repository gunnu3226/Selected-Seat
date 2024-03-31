package io.nbc.selectedseat.db.core.domain.ticket.repository;

import io.nbc.selectedseat.db.core.domain.ticket.entity.TicketPriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketPriceJpaRepository extends JpaRepository<TicketPriceEntity, Long> {

}
