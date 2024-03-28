package io.nbc.selectedseat.web.domain.member;

import io.nbc.selectedseat.domain.member.dto.SignupDTO;
import io.nbc.selectedseat.domain.member.model.Member;
import io.nbc.selectedseat.domain.member.service.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    @PostMapping("/sign-up")
    public Member signUp(@RequestBody SignupDTO dto) {
        return memberService.signup(dto);
    }

    @GetMapping("/{memberId}")
    public Member get(@PathVariable("memberId") Long memberId) {
        return memberService.findById(memberId);
    }
}
