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
    @Column(name = "ticket_price_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketPriceId;

    @Column(name = "concert_id", nullable = false)
    private Long concertId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "ticket_rating", nullable = false)
    private TicketRating ticketRating;

    @Column(name = "price", nullable = false)
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

    public void update(final Long changePrice) {
        this.price = changePrice;
    }
}
