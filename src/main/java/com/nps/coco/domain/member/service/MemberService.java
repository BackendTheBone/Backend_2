package com.nps.coco.domain.member.service;


import com.nps.coco.domain.member.dto.SignInDto;
import com.nps.coco.domain.member.dto.SignUpDto;
import com.nps.coco.domain.member.entity.Member;
import com.nps.coco.domain.member.exception.DuplicateEmailException;
import com.nps.coco.domain.member.exception.MemberNotFoundException;
import com.nps.coco.domain.member.exception.ValidateMember;
import com.nps.coco.domain.member.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    private final HttpSession session;

    private MemberService(MemberRepository memberRepository, HttpSession session) {
        this.memberRepository = memberRepository;
        this.session = session;
    }

    public void signUp(SignUpDto signUpDto) {
        validateDuplicateEmail(signUpDto.getEmail());
        Member member = new Member(signUpDto);
        memberRepository.save(member);
    }

    private void validateDuplicateEmail(String email) {
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
        session.invalidate();
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