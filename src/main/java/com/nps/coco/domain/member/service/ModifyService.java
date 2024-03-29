package com.nps.coco.domain.member.service;

import com.nps.coco.domain.member.entity.Member;
import com.nps.coco.domain.member.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ModifyService {

    private final MemberRepository memberRepository;

    private final HttpSession session;

    public ModifyService(MemberRepository memberRepository, HttpSession session) {
        this.memberRepository = memberRepository;
        this.session = session;
    }

    @Transactional
    public void modifyPassAndName(String password, String name) {
        Member member = (Member) session.getAttribute("member");
        validateLogin(member);
        member.update(password, name);
        memberRepository.save(member);
        session.setAttribute("member", member);
    }

    private void validateLogin(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("로그인 상태 오류");
        }
    }
}
