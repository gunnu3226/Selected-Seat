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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long ticketId;

    @Column(nullable = false)
    private Long concertId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TicketRating ticketRating;

    @Column(nullable = false)
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
