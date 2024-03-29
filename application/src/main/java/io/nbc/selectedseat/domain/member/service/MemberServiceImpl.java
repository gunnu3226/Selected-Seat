package io.nbc.selectedseat.domain.member.service;

import static io.nbc.selectedseat.redis.config.CacheConfig.CACHE_300;

import io.nbc.selectedseat.domain.member.dto.SignupDTO;
import io.nbc.selectedseat.domain.member.model.Member;
import io.nbc.selectedseat.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService  {
    // TODO: sample service
    private final MemberRepository memberRepository;


}
