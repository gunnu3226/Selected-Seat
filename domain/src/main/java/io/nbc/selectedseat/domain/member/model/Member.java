package io.nbc.selectedseat.domain.member.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Member {

    private Long memberId;

    private String email;

    private String password;

    private String nickname;

    private String profile;

    private LocalDate birth;

    private MemberRole memberRole;

    private Long coin;
}
