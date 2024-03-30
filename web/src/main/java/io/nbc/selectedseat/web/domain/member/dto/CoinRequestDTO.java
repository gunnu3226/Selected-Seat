package io.nbc.selectedseat.web.domain.member.dto;

import jakarta.validation.constraints.NotNull;

public record CoinRequestDTO(
    @NotNull Long amount
) {

}
