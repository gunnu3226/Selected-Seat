package io.nbc.selectedseat.web.domain.ticket.dto.response;

public record NumberOfTicketResponseDTO(
    Long numOfTickets
) {

    public static NumberOfTicketResponseDTO from(final Long numOfTickets) {
        return new NumberOfTicketResponseDTO(numOfTickets);
    }
}
