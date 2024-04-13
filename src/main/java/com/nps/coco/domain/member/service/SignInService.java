package com.nps.coco.domain.member.service;

import com.nps.coco.domain.member.entity.Member;
import com.nps.coco.domain.member.exception.MemberNotFoundException;
import com.nps.coco.domain.member.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import com.nps.coco.global.config.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignInService {

    private final MemberRepository memberRepository;
    private final HttpSession session;
    private final PasswordEncoder passwordEncoder;

    private SignInService(MemberRepository memberRepository, HttpSession session) {
        this.memberRepository = memberRepository;
        this.session = session;
        this.passwordEncoder = new PasswordEncoder();
    }

    public void signIn(String password, String email) {
        Member member = validateNotFoundEmail(email);
        validatePassword(password, member.getPassword());

        session.setAttribute("member", member);
    }

    private Member validateNotFoundEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() ->  new MemberNotFoundException("아이디"));
    }

    private void validatePassword(String inputPass, String storedPass) {
        if (!passwordEncoder.matchPassword(inputPass, storedPass)) {
            throw new MemberNotFoundException("비밀번호");
        }
    }

    public void logout() {
        session.removeAttribute("member");
    }
}
