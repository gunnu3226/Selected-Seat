package io.nbc.selectedseat.domain.member.repository;

import io.nbc.selectedseat.domain.member.model.Member;
import java.util.Optional;

public interface MemberRepository {

    // TODO: sample repository
    Member save(final Member member);

    Optional<Member> findById(final Long id);

    Optional<Member> findByEmail(final String email);

    void delete(final Long memberId);
}
