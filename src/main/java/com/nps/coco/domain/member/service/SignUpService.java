package com.nps.coco.domain.member.service;

import com.nps.coco.domain.member.entity.Member;
import com.nps.coco.domain.member.exception.DuplicateEmailException;
import com.nps.coco.domain.member.repository.MemberRepository;
import com.nps.coco.global.config.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignUpService {

    private final MemberRepository memberRepository;
    private PasswordEncoder passwordEncoder;

    private SignUpService (MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = new PasswordEncoder();
    }

    public void signUp(String email, String name, String password) {
        validateDuplicateEmail(email);
        String encodedPass = passwordEncoder.encodePassword(password);
        Member member = new Member(email, name, encodedPass);
        memberRepository.save(member);
    }

    private void validateDuplicateEmail(String email) {
        memberRepository.findByEmail(email)
                .ifPresent(user -> {
                    throw new DuplicateEmailException();
                });
    }
}
