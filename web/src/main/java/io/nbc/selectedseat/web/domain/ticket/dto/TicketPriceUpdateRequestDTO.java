package io.nbc.selectedseat.web.domain.ticket.dto;

import jakarta.validation.constraints.NotNull;

public record TicketPriceUpdateRequestDTO(

    @NotNull Long price
) {

}
