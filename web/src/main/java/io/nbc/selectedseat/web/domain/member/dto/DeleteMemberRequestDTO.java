package io.nbc.selectedseat.web.domain.member.dto;

import jakarta.validation.constraints.NotBlank;

public record DeleteMemberRequestDTO(

    @NotBlank String password
) {

}
