package io.nbc.selectedseat.db.core.domain.ticket.entity;

import io.nbc.selectedseat.domain.ticket.model.TicketPrice;
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
@Table(name = "ticket_prices")
public class TicketPriceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketPriceId;

    @Column(name = "concert_id", nullable = false)
    private Long concertId;

    @Column(name = "ticket_rating", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private TicketRating ticketRating;

    @Column(nullable = false)
    private Long price;

    public static TicketPriceEntity from(final TicketPrice ticketPrice) {
        return TicketPriceEntity.builder()
            .concertId(ticketPrice.getConcertId())
            .ticketRating(ticketPrice.getTicketRating())
            .price(ticketPrice.getPrice())
            .build();
    }

    public TicketPrice toModel() {
        return TicketPrice.builder()
            .ticketPriceId(ticketPriceId)
            .concertId(concertId)
            .ticketRating(ticketRating)
            .price(price)
            .build();
    }
}
