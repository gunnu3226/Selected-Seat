package io.nbc.selectedseat.db.core.domain.member.entity;

import io.nbc.selectedseat.db.core.domain.common.BaseEntity;
import io.nbc.selectedseat.domain.member.model.Member;
import io.nbc.selectedseat.domain.member.model.MemberRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "members")
@SQLRestriction("deleted_at is NULL")
@SQLDelete(sql = "UPDATE artists SET deleted_at = NOW() WHERE artist_id = ?")
public class MemberEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long member_id;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String nickname;

    @Column
    private String profile;

    @Column
    private LocalDate birth;

    @Column
    @Enumerated(EnumType.STRING)
    private MemberRole member_role;

    public MemberEntity(
        String email,
        String password,
        String nickname,
        String profile,
        LocalDate birth,
        MemberRole memberRole
    ) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profile = profile;
        this.birth = birth;
        this.member_role = memberRole;
    }


    public static MemberEntity from(Member member) {
        return new MemberEntity(
            member.getEmail(),
            member.getPassword(),
            member.getNickname(),
            member.getProfile(),
            member.getBirth(),
            member.getMember_role()
        );
    }

    public Member toModel() {
        return new Member(
            member_id,
            email,
            password,
            nickname,
            profile,
            birth,
            member_role
        );
    }
}
