package io.nbc.selectedseat.domain.member.dto;

public record SignupDTO(
        String name,
        String email,
        String password,
        String tel
) {
}
