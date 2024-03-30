package io.nbc.selectedseat.db.core.domain.member.adaptor;

import io.nbc.selectedseat.db.core.domain.member.entity.MemberEntity;
import io.nbc.selectedseat.db.core.domain.member.repository.MemberJpaRepository;
import io.nbc.selectedseat.domain.member.model.Member;
import io.nbc.selectedseat.domain.member.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberPersistenceAdaptor implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Member save(final Member member) {
        return memberJpaRepository.save(MemberEntity.from(member)).toModel();
    }

    @Override
    public Optional<Member> findById(final Long id) {
        return memberJpaRepository.findById(id).map(MemberEntity::toModel);
    }

    @Override
    public Optional<Member> findByEmail(final String email) {
        return memberJpaRepository.findByEmail(email).map(MemberEntity::toModel);
    }

    @Override
    public void updatePassword(
        final Long memberId,
        final String changePassword
    ) {
        memberJpaRepository.findById(memberId)
            .get().updateMember(changePassword);
    }

    @Override
    public void deleteMember(final Long memberId) {
        memberJpaRepository.deleteById(memberId);
    }

    @Override
    public Long chargeCoin(final Long memberId, final Long chargeAmount) {
        MemberEntity memberEntity = memberJpaRepository.findById(memberId).get();
        memberEntity.chargeCoin(chargeAmount);
        return memberEntity.getCoin();
    }

    @Override
    public Long deductionCoin(final Long memberId, final Long deductionAmount) {
        MemberEntity memberEntity = memberJpaRepository.findById(memberId).get();
        memberEntity.deductionCoin(deductionAmount);
        return memberEntity.getCoin();
    }
}
