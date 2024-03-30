package io.nbc.selectedseat.web.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateRequestDTO(
    @NotBlank String password,
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,15}$", message = "비밀번호는 8글자~15자, 대문자 1개, 소문자 1개, 숫자 1개 이상 포함하세요")
    String changePassword,

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,15}$", message = "비밀번호는 8글자~15자, 대문자 1개, 소문자 1개, 숫자 1개 이상 포함하세요")
    String confirmPassword
) {

}
