package io.nbc.selectedseat.db.core.domain.member.entity;


import io.nbc.selectedseat.db.core.domain.common.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;

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
}
