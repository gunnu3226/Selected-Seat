package io.nbc.selectedseat.domain.member.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Follow {

    private Long followId;

    private Long artistId;

    private Long memberId;
}
