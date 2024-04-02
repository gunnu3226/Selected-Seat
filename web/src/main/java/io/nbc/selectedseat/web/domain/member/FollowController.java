package io.nbc.selectedseat.web.domain.member;

import io.nbc.selectedseat.domain.member.dto.FollowInfo;
import io.nbc.selectedseat.domain.member.service.command.FollowWriter;
import io.nbc.selectedseat.web.common.dto.ResponseDTO;
import io.nbc.selectedseat.web.domain.member.dto.FollowRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members/follows")
@RequiredArgsConstructor
public class FollowController {

    private final FollowWriter followWriter;

    @PostMapping
    public ResponseEntity<ResponseDTO<FollowInfo>> followArtist(
        @Valid @RequestBody FollowRequestDTO requestDTO
        // Todo : 멤버 받는 부분 구현 필요(현재 @AuthenticationPrincipal 인식안됨)
    ) {
        FollowInfo responseDTO = followWriter.followArtist(
            1L, requestDTO.artistId());

        return ResponseEntity.status(HttpStatus.CREATED).body(
            ResponseDTO.<FollowInfo>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("팔로우 성공")
                .data(responseDTO)
                .build());
    }

    @DeleteMapping
    public ResponseEntity<Void> unFollowArtist(
        @Valid @RequestBody FollowRequestDTO requestDTO
        // Todo : 멤버 받는 부분 구현 필요(현재 @AuthenticationPrincipal 인식안됨)
    ) {
        followWriter.unFollowArtist(
            1L, requestDTO.artistId());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
