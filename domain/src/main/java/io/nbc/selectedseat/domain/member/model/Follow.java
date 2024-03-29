package io.nbc.selectedseat.domain.member.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Follow {

    private Long followId;

    private Long artistId;

    private Long memberId;
}
