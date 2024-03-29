package com.nps.coco.domain.member.service;

import com.nps.coco.domain.member.entity.Member;
import com.nps.coco.domain.member.exception.ValidateMember;
import com.nps.coco.domain.member.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteService {

    private final MemberRepository memberRepository;

    private final HttpSession session;

    public DeleteService(MemberRepository memberRepository, HttpSession session) {
        this.memberRepository = memberRepository;
        this.session = session;
    }


    @Transactional
    public void deleteMember() {
        Member member = (Member) session.getAttribute("member");
        validateLogin(member);
        memberRepository.deleteById(member.getMemberId());
    }

    private void validateLogin(Member member) {
        if (member == null) {
            throw new ValidateMember();
        }
    }
}
