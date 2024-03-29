package io.nbc.selectedseat.web.domain.member;

import io.nbc.selectedseat.domain.member.dto.SignupResponseDTO;
import io.nbc.selectedseat.domain.member.service.MemberService;
import io.nbc.selectedseat.web.common.dto.ResponseDTO;
import io.nbc.selectedseat.web.domain.member.dto.SignupRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<ResponseDTO<SignupResponseDTO>> signUp(
        @RequestBody SignupRequestDTO requestDTO
    ) {
        SignupResponseDTO responseDTO = memberService.signup(
            requestDTO.email(),
            requestDTO.password(),
            requestDTO.profile(),
            requestDTO.birth()
        );
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ResponseDTO.<SignupResponseDTO>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("회원가입 성공")
                .data(responseDTO)
                .build());
    }
}
