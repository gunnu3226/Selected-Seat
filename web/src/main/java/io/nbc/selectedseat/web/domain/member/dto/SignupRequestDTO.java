package io.nbc.selectedseat.web.domain.member.dto;

import java.time.LocalDate;

public record SignupRequestDTO(

    String email,
    String password,
    String profile,
    LocalDate birth

) {

}
