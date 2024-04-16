package io.nbc.selectedseat.web.domain.member.user;

import io.nbc.selectedseat.domain.member.dto.CoinInfo;
import io.nbc.selectedseat.domain.member.dto.MemberInfo;
import io.nbc.selectedseat.domain.member.facade.MemberFacade;
import io.nbc.selectedseat.domain.member.facade.dto.FollowArtistsInfo;
import io.nbc.selectedseat.domain.member.facade.dto.ReservationDetailInfo;
import io.nbc.selectedseat.domain.member.facade.dto.TicketDetailInfo;
import io.nbc.selectedseat.domain.member.service.command.MemberWriter;
import io.nbc.selectedseat.domain.member.service.query.MemberReader;
import io.nbc.selectedseat.security.userdetail.UserDetailsImpl;
import io.nbc.selectedseat.web.common.dto.ResponseDTO;
import io.nbc.selectedseat.web.domain.dto.CoinResponseDTO;
import io.nbc.selectedseat.web.domain.dto.MemberIdResponseDTO;
import io.nbc.selectedseat.web.domain.member.dto.CoinRequestDTO;
import io.nbc.selectedseat.web.domain.member.dto.DeleteMemberRequestDTO;
import io.nbc.selectedseat.web.domain.member.dto.SignupRequestDTO;
import io.nbc.selectedseat.web.domain.member.dto.UpdateRequestDTO;
import io.nbc.selectedseat.web.excpetion.ImageNotFoundException;
import io.nbc.selectedseat.web.image.UploadService;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberWriter memberWriter;
    private final MemberReader memberReader;
    private final MemberFacade memberFacade;
    private final UploadService uploadService;

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseDTO<MemberIdResponseDTO>> signUp(
        @Valid @RequestBody SignupRequestDTO requestDTO
    ) {
        MemberIdResponseDTO responseDTO = new MemberIdResponseDTO(
            memberWriter.signup(
            requestDTO.email(),
            requestDTO.password(),
                    requestDTO.birth()
            ).id());

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ResponseDTO.<MemberIdResponseDTO>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("회원가입 성공")
                .data(responseDTO)
                .build());
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PutMapping("/profiles")
    public ResponseEntity<ResponseDTO<MemberIdResponseDTO>> updateMemberProfile(
        @RequestPart(required = false) MultipartFile profile,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws IOException {

        if (profile == null) {
            throw new ImageNotFoundException("이미지를 넣어주세요");
        }

        String profileLink = uploadService.upload(profile);

        MemberIdResponseDTO responseDTO = new MemberIdResponseDTO(
            memberWriter.updateMember(
                userDetails.getMemberId(),
                profileLink
            )
        );

        return ResponseEntity.status(HttpStatus.OK).body(
            ResponseDTO.<MemberIdResponseDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("프로필 이미지 수정 성공")
                .data(responseDTO)
                .build());
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PutMapping
    public ResponseEntity<ResponseDTO<MemberIdResponseDTO>> updateMember(
        @Valid @RequestBody UpdateRequestDTO requestDTO,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        MemberIdResponseDTO responseDTO = new MemberIdResponseDTO(
            memberWriter.updatePassword(
                userDetails.getMemberId(),
                requestDTO.password(),
                requestDTO.changePassword(),
                requestDTO.confirmPassword()
            ).id()
        );

        return ResponseEntity.status(HttpStatus.OK).body(
            ResponseDTO.<MemberIdResponseDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("비밀번호 수정 성공")
                .data(responseDTO)
                .build());
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @DeleteMapping
    public ResponseEntity<Void> deleteMember(
        @Valid @RequestBody DeleteMemberRequestDTO requestDTO,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        memberWriter.deleteMember(userDetails.getMemberId(), requestDTO.password());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PutMapping("/coins/charges")
    public ResponseEntity<ResponseDTO<CoinResponseDTO>> chargeCoin(
        @Valid @RequestBody CoinRequestDTO requestDTO,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        CoinResponseDTO responseDTO = new CoinResponseDTO(
            memberWriter.chargeCoin(userDetails.getMemberId(), requestDTO.amount())
                .coinAmount()
        );

        return ResponseEntity.status(HttpStatus.OK).body(
            ResponseDTO.<CoinResponseDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("코인 충전 성공")
                .data(responseDTO)
                .build());
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PutMapping("/coins/deductions")
    public ResponseEntity<ResponseDTO<CoinInfo>> deductionCoin(
        @Valid @RequestBody CoinRequestDTO requestDTO,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        CoinInfo responseDTO = memberWriter.deductionCoin(userDetails.getMemberId(),
            requestDTO.amount());

        return ResponseEntity.status(HttpStatus.OK).body(
            ResponseDTO.<CoinInfo>builder()
                .statusCode(HttpStatus.OK.value())
                .message("코인 차감 성공")
                .data(responseDTO)
                .build());
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping
    public ResponseEntity<ResponseDTO<MemberInfo>> getMemberByID(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        MemberInfo responseDTO = memberReader.getMemberById(userDetails.getMemberId());

        return ResponseEntity.status(HttpStatus.OK).body(
            ResponseDTO.<MemberInfo>builder()
                .statusCode(HttpStatus.OK.value())
                .message("회원 조회 성공")
                .data(responseDTO)
                .build()
        );
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/follows")
    public ResponseEntity<ResponseDTO<List<FollowArtistsInfo>>> getFollowArtists(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        List<FollowArtistsInfo> responseDTO = memberFacade.getFollowArtists(
            userDetails.getMemberId());

        return ResponseEntity.status(HttpStatus.OK).body(
            ResponseDTO.<List<FollowArtistsInfo>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("팔로우 아티스트 조회 성공")
                .data(responseDTO)
                .build());
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/tickets")
    public ResponseEntity<ResponseDTO<List<TicketDetailInfo>>> getTicketsByMemberId(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        List<TicketDetailInfo> responseDTO = memberFacade.getTicketsByMemberId(
            userDetails.getMemberId());

        return ResponseEntity.status(HttpStatus.OK).body(
            ResponseDTO.<List<TicketDetailInfo>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("보유 티켓 조회 성공")
                .data(responseDTO)
                .build());
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/reservations")
    public ResponseEntity<ResponseDTO<List<ReservationDetailInfo>>> getReservationsByMemberId(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        List<ReservationDetailInfo> responseDTO = memberFacade.getReservationsByMemberId(
            userDetails.getMemberId());

        return ResponseEntity.status(HttpStatus.OK).body(
            ResponseDTO.<List<ReservationDetailInfo>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("예매 내역 조회 성공")
                .data(responseDTO)
                .build());
    }
}
