package io.nbc.selectedseat.domain.member.service;

import static io.nbc.selectedseat.redis.config.CacheConfig.CACHE_300;

import io.nbc.selectedseat.domain.member.dto.SignupDTO;
import io.nbc.selectedseat.domain.member.model.Member;
import io.nbc.selectedseat.domain.member.repository.MemberRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    // TODO: sample service
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member signup(SignupDTO dto) {
        return memberRepository.save(new Member(
            dto.name(),
            dto.email(),
            dto.tel(),
            dto.password())
        );
    }

    @Cacheable(cacheNames = CACHE_300, key = "'user:' + #p0")
    public Member findById(Long id) {
        return memberRepository.findById(id);
    }
}
