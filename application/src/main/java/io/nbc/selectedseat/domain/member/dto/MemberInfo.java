package io.nbc.selectedseat.domain.member.dto;

import io.nbc.selectedseat.domain.member.model.Member;
import java.time.LocalDate;

public record MemberInfo(
    Long id,
    String email,
    String nickName,
    String profile,
    LocalDate birth,
    Long coin
) {

    public static MemberInfo from(final Member member) {
        return new MemberInfo(
            member.getMemberId(),
            member.getEmail(),
            member.getNickname(),
            member.getProfile(),
            member.getBirth(),
            member.getCoin()
        );
    }
}
