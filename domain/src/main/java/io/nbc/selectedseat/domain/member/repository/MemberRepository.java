package io.nbc.selectedseat.domain.member.repository;

import io.nbc.selectedseat.domain.member.model.Member;

public interface MemberRepository {
    // TODO: sample repository
    Member save(Member member);
    Member findById(Long id);
}
