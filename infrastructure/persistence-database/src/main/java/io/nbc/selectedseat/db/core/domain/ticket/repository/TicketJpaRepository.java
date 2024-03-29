package io.nbc.selectedseat.db.core.domain.ticket.repository;

import io.nbc.selectedseat.db.core.domain.ticket.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketJpaRepository extends JpaRepository<TicketEntity, Long> {

}
