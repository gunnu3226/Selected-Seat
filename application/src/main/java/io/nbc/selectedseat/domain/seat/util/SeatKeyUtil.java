package io.nbc.selectedseat.domain.seat.util;

public class SeatKeyUtil {
    public static String generateKey(
        final Long concertId,
        final Long concertDateId,
        final String ticketRating
    ) {
        return "seatKey:concertId:" + concertId
            + ":concertDate:" + concertDateId
            + ":ticketRating:" + ticketRating;
    }
}
