package io.nbc.selectedseat.domain.member.service;

import io.nbc.selectedseat.domain.member.dto.CoinInfo;
import io.nbc.selectedseat.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CoinService {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    public CoinInfo chargeCoin(
        final Long userId,
        final Long chargeAmount
    ) {
        memberService.findMemberById(userId);
        Long coin = memberRepository.chargeCoin(userId, chargeAmount);
        return new CoinInfo(coin);
    }
}
