package io.nbc.selectedseat.web.domain.ticket.dto.response;

import io.nbc.selectedseat.domain.ticket.dto.TicketInfo;
import io.nbc.selectedseat.domain.ticket.model.TicketRating;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record TicketInfoResponseDTO(
    Long ticketId,
    Long concertId,
    TicketRating ticketRating,
    String ticketNumber,
    LocalDateTime createdAt
) {

    public static TicketInfoResponseDTO from(final TicketInfo ticketInfo) {
        return TicketInfoResponseDTO.builder()
            .ticketId(ticketInfo.ticketId())
            .concertId(ticketInfo.concertId())
            .ticketRating(ticketInfo.ticketRating())
            .ticketNumber(ticketInfo.ticketNumber())
            .createdAt(ticketInfo.createdAt())
            .build();
    }
}
