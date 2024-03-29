package io.nbc.selectedseat.domain.member.service;

import io.nbc.selectedseat.domain.member.dto.SignupResponseDTO;
import io.nbc.selectedseat.domain.member.exception.EmailExistException;
import io.nbc.selectedseat.domain.member.model.Member;
import io.nbc.selectedseat.domain.member.model.MemberRole;
import io.nbc.selectedseat.domain.member.repository.MemberRepository;
import io.nbc.selectedseat.security.config.PasswordUtil;
import java.time.LocalDate;
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
        if (memberRepository.findByEmail(email) != null) {
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
}
