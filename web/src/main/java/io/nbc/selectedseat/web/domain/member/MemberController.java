package io.nbc.selectedseat.web.domain.member;

import io.nbc.selectedseat.domain.member.dto.CoinInfo;
import io.nbc.selectedseat.domain.member.dto.MemberInfo;
import io.nbc.selectedseat.domain.member.facade.MemberFacade;
import io.nbc.selectedseat.domain.member.facade.dto.FollowArtistsInfo;
import io.nbc.selectedseat.domain.member.facade.dto.ReservationDetailInfo;
import io.nbc.selectedseat.domain.member.facade.dto.TicketDetailInfo;
import io.nbc.selectedseat.domain.member.service.command.MemberWriter;
import io.nbc.selectedseat.domain.member.service.query.MemberReader;
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

    @PostMapping
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

    @PutMapping("/profile")
    public ResponseEntity<ResponseDTO<MemberIdResponseDTO>> updateMemberProfile(
        @RequestPart(required = false) MultipartFile profile
    ) throws IOException {

        if (profile == null) {
            throw new ImageNotFoundException("이미지를 넣어주세요");
        }

        String profileLink = uploadService.upload(profile);

        MemberIdResponseDTO responseDTO = new MemberIdResponseDTO(
            memberWriter.updateMember(
                1L, // TODO : member id
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


    @PutMapping
    public ResponseEntity<ResponseDTO<MemberIdResponseDTO>> updateMember(
        @Valid @RequestBody UpdateRequestDTO requestDTO
        // Todo: user
    ) {
        MemberIdResponseDTO responseDTO = new MemberIdResponseDTO(
            memberWriter.updatePassword(
                1L, // Todo: user
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

    @DeleteMapping
    public ResponseEntity<Void> deleteMember(
        @Valid @RequestBody DeleteMemberRequestDTO requestDTO
        // Todo: user logic
    ) {
        memberWriter.deleteMember(1L, requestDTO.password());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/coins/charges")
    public ResponseEntity<ResponseDTO<CoinResponseDTO>> chargeCoin(
        @Valid @RequestBody CoinRequestDTO requestDTO
        // Todo: user
    ) {
        CoinResponseDTO responseDTO = new CoinResponseDTO(
            memberWriter.chargeCoin(1L, requestDTO.amount())
                .coinAmount()
        );
        return ResponseEntity.status(HttpStatus.OK).body(
            ResponseDTO.<CoinResponseDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("코인 충전 성공")
                .data(responseDTO)
                .build());
    }

    @PutMapping("/coins/deductions")
    public ResponseEntity<ResponseDTO<CoinInfo>> deductionCoin(
        @Valid @RequestBody CoinRequestDTO requestDTO
        // Todo: user
    ) {
        CoinInfo responseDTO = memberWriter.deductionCoin(1L, requestDTO.amount());
        return ResponseEntity.status(HttpStatus.OK).body(
            ResponseDTO.<CoinInfo>builder()
                .statusCode(HttpStatus.OK.value())
                .message("코인 차감 성공")
                .data(responseDTO)
                .build());
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<MemberInfo>> getMemberByID(
        //Todo: user
    ) {
        MemberInfo responseDTO = memberReader.getMemberById(1L);
        return ResponseEntity.status(HttpStatus.OK).body(
            ResponseDTO.<MemberInfo>builder()
                .statusCode(HttpStatus.OK.value())
                .message("회원 조회 성공")
                .data(responseDTO)
                .build()
        );
    }

    @GetMapping("/follows")
    public ResponseEntity<ResponseDTO<List<FollowArtistsInfo>>> getFollowArtists(
        //Todo: user logic
    ) {
        List<FollowArtistsInfo> responseDTO = memberFacade.getFollowArtists(1L);
        return ResponseEntity.status(HttpStatus.OK).body(
            ResponseDTO.<List<FollowArtistsInfo>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("팔로우 아티스트 조회 성공")
                .data(responseDTO)
                .build());
    }

    @GetMapping("/tickets")
    public ResponseEntity<ResponseDTO<List<TicketDetailInfo>>> getTicketsByMemberId(
        //Todo:유저
    ) {
        List<TicketDetailInfo> responseDTO = memberFacade.getTicketsByMemberId(1L);
        return ResponseEntity.status(HttpStatus.OK).body(
            ResponseDTO.<List<TicketDetailInfo>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("보유 티켓 조회 성공")
                .data(responseDTO)
                .build());
    }

    @GetMapping("/reservations")
    public ResponseEntity<ResponseDTO<List<ReservationDetailInfo>>> getReservationsByMemberId(
        //Todo:유저
    ) {
        List<ReservationDetailInfo> responseDTO = memberFacade.getReservationsByMemberId(1L);
        return ResponseEntity.status(HttpStatus.OK).body(
            ResponseDTO.<List<ReservationDetailInfo>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("예매 내역 조회 성공")
                .data(responseDTO)
                .build());
    }
}
