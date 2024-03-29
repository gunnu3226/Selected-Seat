package io.nbc.selectedseat.db.core.domain.member.adaptor;

import io.nbc.selectedseat.db.core.domain.member.entity.MemberJpaEntity;
import io.nbc.selectedseat.db.core.domain.member.repository.MemberJpaRepository;
import io.nbc.selectedseat.domain.member.model.Member;
import io.nbc.selectedseat.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Component;

@Component
public class MemberPersistenceAdaptor implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    public MemberPersistenceAdaptor(MemberJpaRepository memberJpaRepository) {
        this.memberJpaRepository = memberJpaRepository;
    }

    // TODO: sample member repository implementation

    @Override
    public Member save(Member member) {
        return memberJpaRepository.save(MemberJpaEntity.from(member)).toModel();
    }

    @Override
    public Member findById(Long id) {
        return memberJpaRepository.findById(id)
            .orElseThrow()
            .toModel();
    }

    @Override
    public Member findByEmail(String email) {
        return null;
    }
}
