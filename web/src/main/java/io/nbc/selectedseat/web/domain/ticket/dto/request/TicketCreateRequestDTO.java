package io.nbc.selectedseat.web.domain.ticket.dto.request;

import jakarta.validation.constraints.NotNull;

public record TicketCreateRequestDTO(
    @NotNull(message = "필수 항목입니다") Long concertId
) {

}
