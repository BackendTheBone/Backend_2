package com.nps.coco.domain.member.service;

import com.nps.coco.domain.member.entity.Member;
import com.nps.coco.domain.member.exception.ValidateMember;
import com.nps.coco.domain.member.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class DeleteService {

    private final MemberRepository memberRepository;

    private final HttpSession session;

    private DeleteService(MemberRepository memberRepository, HttpSession session) {
        this.memberRepository = memberRepository;
        this.session = session;
    }

    public void deleteMember() {
        Member member = (Member) session.getAttribute("member");
        validateLogin(member);
        memberRepository.deleteById(member.getUserId());
    }

    private void validateLogin(Member member) {
        if (member == null) {
            throw new ValidateMember();
        }
    }
}
