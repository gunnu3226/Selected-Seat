package io.nbc.selectedseat.web.domain.member.user;

import io.nbc.selectedseat.domain.member.dto.FollowInfo;
import io.nbc.selectedseat.domain.member.service.command.FollowWriter;
import io.nbc.selectedseat.security.userdetail.UserDetailsImpl;
import io.nbc.selectedseat.web.common.dto.ResponseDTO;
import io.nbc.selectedseat.web.domain.member.dto.FollowRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<ResponseDTO<FollowInfo>> followArtist(
        @Valid @RequestBody FollowRequestDTO requestDTO,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        FollowInfo responseDTO = followWriter.followArtist(
            userDetails.getMemberId(), requestDTO.artistId());

        return ResponseEntity.status(HttpStatus.CREATED).body(
            ResponseDTO.<FollowInfo>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("팔로우 성공")
                .data(responseDTO)
                .build());
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @DeleteMapping
    public ResponseEntity<Void> unFollowArtist(
        @Valid @RequestBody FollowRequestDTO requestDTO,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        followWriter.unFollowArtist(
            userDetails.getMemberId(), requestDTO.artistId());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
