package io.nbc.selectedseat.web.domain.member;

import io.nbc.selectedseat.domain.member.dto.MemberInfo;
import io.nbc.selectedseat.domain.member.dto.SignupResponseDTO;
import io.nbc.selectedseat.domain.member.facade.MemberFacade;
import io.nbc.selectedseat.domain.member.facade.dto.FollowArtistsInfo;
import io.nbc.selectedseat.domain.member.service.MemberService;
import io.nbc.selectedseat.web.common.dto.ResponseDTO;
import io.nbc.selectedseat.web.domain.member.dto.DeleteMemberRequestDTO;
import io.nbc.selectedseat.web.domain.member.dto.SignupRequestDTO;
import io.nbc.selectedseat.web.domain.member.dto.UpdateRequestDTO;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberFacade memberFacade;

    @PostMapping
    public ResponseEntity<ResponseDTO<SignupResponseDTO>> signUp(
        @Valid @RequestBody SignupRequestDTO requestDTO
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

    @PutMapping
    public ResponseEntity<ResponseDTO<MemberInfo>> updateMember(
        @Valid @RequestBody UpdateRequestDTO requestDTO
        // Todo: user
    ) {
        MemberInfo responseDTO = memberService.updatePassword(
            1L, // Todo: user
            requestDTO.password(),
            requestDTO.changePassword(),
            requestDTO.confirmPassword()
        );
        return ResponseEntity.status(HttpStatus.OK).body(
            ResponseDTO.<MemberInfo>builder()
                .statusCode(HttpStatus.OK.value())
                .message("비밀번호 수정 성공")
                .data(responseDTO)
                .build());
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMember(
        @Valid @RequestBody DeleteMemberRequestDTO requestDTO
        // Todo: user logic
    ) {
        memberService.deleteMember(1L, requestDTO.password());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<FollowArtistsInfo>>> getFollowArtists(
        //Todo: user logic
    ) {
        List<FollowArtistsInfo> responseDTO = memberFacade.getFollowArtists(1L);
        return ResponseEntity.status(HttpStatus.OK).body(
            ResponseDTO.<List<FollowArtistsInfo>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("팔로우 아티스트 조회 성공")
                .data(responseDTO)
                .build()
        );
    }
}
