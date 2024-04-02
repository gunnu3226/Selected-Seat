package io.nbc.selectedseat.web.domain.dto;

import jakarta.validation.constraints.NotNull;

public record TicketPriceGetRequestDTO(

    @NotNull Long concertId
) {

}
