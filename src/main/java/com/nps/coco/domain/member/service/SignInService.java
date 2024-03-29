package com.nps.coco.domain.member.service;

import com.nps.coco.domain.member.dto.SignInRequest;
import com.nps.coco.domain.member.entity.Member;
import com.nps.coco.domain.member.exception.MemberNotFoundException;
import com.nps.coco.domain.member.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class SignInService {

    private final MemberRepository memberRepository;

    private final HttpSession session;

    private SignInService(MemberRepository memberRepository, HttpSession session) {
        this.memberRepository = memberRepository;
        this.session = session;
    }

    public void signIn(String password, String email) {
        Member member = validateNotFoundEmail(email);
        validateNotFoundPass(password, member.getPassword());

        session.setAttribute("member", member);
    }

    private Member validateNotFoundEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() ->  new MemberNotFoundException("아이디"));
    }

    private void validateNotFoundPass(String inputPass, String realPass) {
        if (!inputPass.equals(realPass)) {
            throw new MemberNotFoundException("비밀번호");
        }
    }

    public void logout() {
        session.removeAttribute("member");
    }
}
