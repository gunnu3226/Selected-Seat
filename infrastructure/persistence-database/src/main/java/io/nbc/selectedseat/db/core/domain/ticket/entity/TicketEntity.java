package io.nbc.selectedseat.db.core.domain.ticket.entity;

import io.nbc.selectedseat.db.core.domain.common.BaseEntity;
import io.nbc.selectedseat.domain.ticket.model.Ticket;
import io.nbc.selectedseat.domain.ticket.model.TicketRating;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "tickets")
public class TicketEntity extends BaseEntity {

    @Id
    @Column(name = "ticket_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long ticketId;

    @Column(name = "concert_id", nullable = false)
    private Long concertId;

    @Enumerated(EnumType.STRING)
    @Column(name = "ticket_rating", nullable = false)
    private TicketRating ticketRating;

    @Column(name = "ticket_number", nullable = false)
    private String ticketNumber;

    public static TicketEntity from(final Ticket ticket) {
        return TicketEntity.builder()
            .concertId(ticket.getConcertId())
            .ticketRating(ticket.getTicketRating())
            .ticketNumber(ticket.getTicketNumber())
            .build();
    }

    public Ticket toModel() {
        return Ticket.builder()
            .concertId(concertId)
            .ticketRating(ticketRating)
            .ticketNumber(ticketNumber)
            .ticketId(ticketId)
            .createdAt(getCreatedAt())
            .modifiedAt(getModifiedAt())
            .build();
    }
}
