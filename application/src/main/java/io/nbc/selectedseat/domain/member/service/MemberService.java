package io.nbc.selectedseat.domain.member.service;

import io.nbc.selectedseat.domain.member.dto.MemberInfo;
import io.nbc.selectedseat.domain.member.dto.SignupResponseDTO;
import io.nbc.selectedseat.domain.member.exception.EmailExistException;
import io.nbc.selectedseat.domain.member.exception.MisMatchPasswordException;
import io.nbc.selectedseat.domain.member.exception.NoSuchMemberException;
import io.nbc.selectedseat.domain.member.exception.SamePasswordException;
import io.nbc.selectedseat.domain.member.model.Member;
import io.nbc.selectedseat.domain.member.model.MemberRole;
import io.nbc.selectedseat.domain.member.repository.MemberRepository;
import io.nbc.selectedseat.security.config.PasswordUtil;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordUtil passwordUtil;

    public SignupResponseDTO signup(
        final String email,
        final String password,
        final String profile,
        final LocalDate birth
    ) {
        if (memberRepository.findByEmail(email).isPresent()) {
            throw new EmailExistException("해당 email은 이미 존재합니다.");
        }
        String encodedPassword = passwordUtil.encode(password);
        Member savedMember = memberRepository.save(Member.builder()
            .email(email)
            .password(encodedPassword)
            .profile(profile)
            .birth(birth)
            .nickname(UUID.randomUUID().toString())
            .memberRole(MemberRole.USER)
            .build());
        return new SignupResponseDTO(savedMember.getMemberId());
    }

    public void deleteMember(final Long memberId, final String password) {
        Member member = findMemberById(memberId);
        verifyPassword(password, member.getPassword());
        memberRepository.deleteMember(memberId);
    }

    public MemberInfo updatePassword(
        final Long userId,
        final String password,
        final String changePassword,
        final String confirmPassword
    ) {
        verifyDifferencePassword(password, changePassword);
        checkPasswordMatch(changePassword, confirmPassword);
        Member member = findMemberById(userId);
        verifyPassword(password, member.getPassword());
        String encodedPassword = passwordUtil.encode(changePassword);
        memberRepository.updatePassword(userId, encodedPassword);

        return new MemberInfo(userId);
    }

    private Member findMemberById(final Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
            () -> new NoSuchMemberException("존재하지 않는 회원입니다")
        );
        return member;
    }

    private void verifyPassword(
        final String changePassword,
        final String memberPassword
    ) {
        if (!passwordUtil.matchPassword(changePassword, memberPassword)) {
            throw new MisMatchPasswordException("비밀번호가 일치하지 않습니다");
        }
    }

    private void checkPasswordMatch(
        final String password,
        final String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new InputMismatchException("입력값이 불일치 합니다.");
        }
    }

    private void verifyDifferencePassword(
        final String password,
        final String changePassword
    ) {
        if (password.equals(changePassword)) {
            throw new SamePasswordException("변경할 비밀번호는 이전과 달라야 합니다.");
        }
    }
}
