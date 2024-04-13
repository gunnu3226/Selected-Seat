package io.nbc.selectedseat.db.core.domain.reservation.entity;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@ToString
@Document(collection = "reservations")
public class ReservationDocument {

    @Id
    public String id;
    private Long reservationId;
    private String memberEmail;
    private String concertName;
    private String hall;
    private String ticketNumber;
    private Long ticketPrice;
    private LocalDateTime concertDate;

    public ReservationDocument() {
    }

    public ReservationDocument(
        final Long reservationId,
        final String memberEmail,
        final String concertName,
        final String hall,
        final String ticketNumber,
        final Long ticketPrice,
        final LocalDateTime concertDate
    ) {
        this.reservationId = reservationId;
        this.memberEmail = memberEmail;
        this.concertName = concertName;
        this.hall = hall;
        this.ticketNumber = ticketNumber;
        this.ticketPrice = ticketPrice;
        this.concertDate = concertDate;
    }
}
