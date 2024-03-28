package io.nbc.selectedseat.db.core.domain.member.adaptor;

import io.nbc.selectedseat.db.core.domain.member.repository.MemberJpaRepository;
import io.nbc.selectedseat.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Component;

@Component
public class MemberPersistenceAdaptor implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    public MemberPersistenceAdaptor(MemberJpaRepository memberJpaRepository) {
        this.memberJpaRepository = memberJpaRepository;
    }

    // TODO: sample member repository implementation
}
