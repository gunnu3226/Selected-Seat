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
    private LocalDateTime startedAt;

    public ReservationDocument() {
    }

    public ReservationDocument(final Long reservationId,
        final String memberEmail,
        final String concertName, final String hall, final String ticketNumber,
        final LocalDateTime startedAt) {
        this.reservationId = reservationId;
        this.memberEmail = memberEmail;
        this.concertName = concertName;
        this.hall = hall;
        this.ticketNumber = ticketNumber;
        this.startedAt = startedAt;
    }
}
