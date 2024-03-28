package io.nbc.selectedseat.db.core.domain.member.entity;

import io.nbc.selectedseat.db.core.domain.common.BaseEntity;
import io.nbc.selectedseat.domain.member.model.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class MemberJpaEntity extends BaseEntity {

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String tel;

    public MemberJpaEntity() {
    }

    public MemberJpaEntity(
        String name,
        String email,
        String password,
        String tel
    ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public static MemberJpaEntity from(Member member) {
        return new MemberJpaEntity(
            member.getName(),
            member.getEmail(),
            member.getPassword(),
            member.getTel()
        );
    }

    public Member toModel() {
        return new Member(
            getId(),
            name,
            email,
            tel,
            password
        );
    }
}
