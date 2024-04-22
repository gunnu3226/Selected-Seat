package io.nbc.selectedseat.web.domain.member.dto;

public record EmailAuthCodeRequestDTO(
    String email,
    String authCode
) {

}
