package io.nbc.selectedseat.domain.member.model;


import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    // TODO: sample class
    private Long member_id;
    private String email;
    private String password;
    private String nickname;
    private String profile;
    private LocalDate birth;
    private MemberRole member_role;
    // TODO: add

    // TODO: update
}
