package io.nbc.selectedseat.domain.member.service.command;

import io.nbc.selectedseat.domain.member.dto.CoinInfo;
import io.nbc.selectedseat.domain.member.dto.MemberInfo;
import io.nbc.selectedseat.domain.member.exception.EmailExistException;
import io.nbc.selectedseat.domain.member.exception.MisMatchPasswordException;
import io.nbc.selectedseat.domain.member.exception.NotEnoughCoinException;
import io.nbc.selectedseat.domain.member.exception.SamePasswordException;
import io.nbc.selectedseat.domain.member.model.Member;
import io.nbc.selectedseat.domain.member.model.MemberRole;
import io.nbc.selectedseat.domain.member.repository.MemberRepository;
import io.nbc.selectedseat.domain.member.service.query.MemberReader;
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
public class MemberWriter {

    private final Long COIN_ZERO = 0L;

    private final MemberReader memberReader;
    private final MemberRepository memberRepository;
    private final PasswordUtil passwordUtil;

    public MemberInfo signup(
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
            .coin(0L)
            .build());
        return MemberInfo.from(savedMember);
    }

    public void deleteMember(final Long memberId, final String password) {
        Member member = memberReader.findMemberById(memberId);
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
        Member member = memberReader.findMemberById(userId);
        verifyPassword(password, member.getPassword());
        String encodedPassword = passwordUtil.encode(changePassword);
        memberRepository.updatePassword(userId, encodedPassword);

        return MemberInfo.from(member);
    }

    public CoinInfo chargeCoin(
        final Long memberId,
        final Long chargeAmount
    ) {
        memberReader.findMemberById(memberId);
        Long coin = memberRepository.chargeCoin(memberId, chargeAmount);
        return new CoinInfo(coin);
    }

    public CoinInfo deductionCoin(
        final Long memberId,
        final Long deductionAmount
    ) {
        Member member = memberReader.findMemberById(memberId);
        checkMemberCoin(deductionAmount, member);
        Long coin = memberRepository.deductionCoin(memberId, deductionAmount);
        return new CoinInfo(coin);
    }

    private void checkMemberCoin(Long deductionAmount, Member member) {
        if (member.getCoin() - deductionAmount >= COIN_ZERO) {
            return;
        }
        throw new NotEnoughCoinException("코인이 부족합니다");
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
