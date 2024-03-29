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
    public Member save(Member member) {
        return memberJpaRepository.save(MemberEntity.from(member)).toModel();
    }

    @Override
    public Member findById(Long id) {
        return memberJpaRepository.findById(id)
            .orElseThrow()
            .toModel();
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        Optional<MemberEntity> memberJpaEntity = memberJpaRepository.findByEmail(email);
        if (memberJpaEntity.isPresent()) {
            return Optional.of(memberJpaEntity.get().toModel());
        }
        return null;
    }
}
