package io.nbc.selectedseat.web.domain.member.dto;

public record FollowRequestDTO(
    Long memberId,
    Long artistId
) {

}
