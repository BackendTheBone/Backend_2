package com.nps.coco.domain.member.service;


import com.nps.coco.domain.member.dto.SignInDto;
import com.nps.coco.domain.member.dto.SignUpDto;
import com.nps.coco.domain.member.entity.Member;
import com.nps.coco.domain.member.exception.DuplicateEmailException;
import com.nps.coco.domain.member.exception.MemberNotFoundException;
import com.nps.coco.domain.member.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    private final HttpSession session;

    public MemberService(MemberRepository memberRepository, HttpSession session) {
        this.memberRepository = memberRepository;
        this.session = session;
    }

    public void signUp(SignUpDto signUpDto) {
        validateDuplicateEmail(signUpDto.getEmail());
        Member member = new Member(signUpDto);
        memberRepository.save(member);
    }

    public void validateDuplicateEmail(String email) {
        memberRepository.findByEmail(email)
                .ifPresent(user -> {
                    throw new DuplicateEmailException();
                });
    }

    public void signIn(SignInDto signInDto) {
        Member member = validateNotFoundEmail(signInDto.getEmail());
        validateNotFoundPass(signInDto.getPassword(), member.getPassword());

        session.setAttribute("member", member);
    }

    public Member validateNotFoundEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() ->  new MemberNotFoundException("아이디"));
    }

    public void validateNotFoundPass(String inputPass, String realPass) {
        if (!inputPass.equals(realPass)) {
            throw new MemberNotFoundException("비밀번호");
        }
    }

    public void logout() {
        session.removeAttribute("member");
    }
}