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
@SQLDelete(sql = "UPDATE members SET deleted_at = NOW() WHERE member_id = ?")
public class MemberEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "profile")
    private String profile;

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @Column(name = "member_role")
    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    @Column(name = "coin")
    private Long coin;

    public MemberEntity(
        String email,
        String password,
        String nickname,
        String profile,
        LocalDate birth,
        MemberRole memberRole,
        Long coin
    ) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profile = profile;
        this.birth = birth;
        this.memberRole = memberRole;
        this.coin = coin;
    }


    public static MemberEntity from(Member member) {
        return new MemberEntity(
            member.getEmail(),
            member.getPassword(),
            member.getNickname(),
            member.getProfile(),
            member.getBirth(),
            member.getMemberRole(),
            member.getCoin()
        );
    }

    public Member toModel() {
        return new Member(
            memberId,
            email,
            password,
            nickname,
            profile,
            birth,
            memberRole,
            coin
        );
    }

    public void updateMember(final String changePassword) {
        this.password = changePassword;
    }

    public void chargeCoin(final Long chargeAmount) {
        this.coin += chargeAmount;
    }

    public void deductionCoin(final Long deductionAmount) {
        this.coin -= deductionAmount;
    }
}
