package io.nbc.selectedseat.domain.member.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Member {
    // TODO: sample class
    private Long id;
    private String name;
    private String email;
    private String tel;
    private String password;

    public Member(
        final Long id,
        final String name,
        final String email,
        final String tel,
        final String password
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.tel = tel;
        this.password = password;
    }

    public Member(
        final String name,
        final String email,
        final String tel,
        final String password
    ) {
        this.name = name;
        this.email = email;
        this.tel = tel;
        this.password = password;
    }

    // TODO: add

    // TODO: update
}
