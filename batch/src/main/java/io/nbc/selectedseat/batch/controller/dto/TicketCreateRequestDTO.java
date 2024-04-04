package io.nbc.selectedseat.batch.controller.dto;

public record TicketCreateRequestDTO(
    Long concertId,
    Long numOfRow,
    Long numOfRRatingTicket,
    Long numOfSRatingTicket,
    Long numOfARatingTicket
) {

}
