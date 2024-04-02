package io.nbc.selectedseat.web.domain.ticket.dto.request;

import jakarta.validation.constraints.NotNull;

public record TicketPriceUpdateRequestDTO(

    @NotNull(message = "변강할 가격을 입력해주세요")
    Long price
) {

}
