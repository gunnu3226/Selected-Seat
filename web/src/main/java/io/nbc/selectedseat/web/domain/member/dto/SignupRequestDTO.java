package io.nbc.selectedseat.web.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

public record SignupRequestDTO(

    @Email(message = "이메일 형식만 가입이 가능합니다")
    @NotBlank(message = "이메일은 필수 입력 값입니다")
    String email,

    @NotBlank(message = "비밀번호를 입력하세요")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,15}$", message = "비밀번호는 8글자~15자, 대문자 1개, 소문자 1개, 숫자 1개 이상 포함하세요")
    String password,
    String profile,
    LocalDate birth

) {

}
