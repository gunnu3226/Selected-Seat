package io.nbc.selectedseat.web.domain.ticket.dto.response;

public record TicketInfoResponseDTO(
    Long numOfTickets
) {

    public static TicketInfoResponseDTO from(Long numOfTickets) {
        return new TicketInfoResponseDTO(numOfTickets);
    }
}
