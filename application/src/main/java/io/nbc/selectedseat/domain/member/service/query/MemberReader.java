package io.nbc.selectedseat.domain.member.service.query;

import io.nbc.selectedseat.domain.member.dto.MemberInfo;
import io.nbc.selectedseat.domain.member.exception.NoSuchMemberException;
import io.nbc.selectedseat.domain.member.model.Member;
import io.nbc.selectedseat.domain.member.repository.MemberRepository;
import io.nbc.selectedseat.security.config.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberReader {

    private final MemberRepository memberRepository;
    private final PasswordUtil passwordUtil;

    public MemberInfo getMemberById(final Long memberId) {
        return MemberInfo.from(findMemberById(memberId));
    }

    public Member findMemberById(final Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
            () -> new NoSuchMemberException("존재하지 않는 회원입니다")
        );
    }
}
