package io.nbc.selectedseat.domain.member.service;

import io.nbc.selectedseat.domain.member.dto.CoinInfo;
import io.nbc.selectedseat.domain.member.exception.NotEnoughCoinException;
import io.nbc.selectedseat.domain.member.model.Member;
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
        final Long memberId,
        final Long chargeAmount
    ) {
        memberService.findMemberById(memberId);
        Long coin = memberRepository.chargeCoin(memberId, chargeAmount);
        return new CoinInfo(coin);
    }

    public CoinInfo deductionCoin(
        final Long memberId,
        final Long deductionAmount
    ) {
        Member member = memberService.findMemberById(memberId);
        checkMemberCoin(deductionAmount, member);
        Long coin = memberRepository.deductionCoin(memberId, deductionAmount);
        return new CoinInfo(coin);
    }

    private void checkMemberCoin(Long deductionAmount, Member member) {
        if (member.getCoin() - deductionAmount >= 0) {
            return;
        }
        throw new NotEnoughCoinException("코인이 부족합니다");
    }
}
