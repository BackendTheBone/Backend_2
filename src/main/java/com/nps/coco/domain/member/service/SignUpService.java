package com.nps.coco.domain.member.service;

import com.nps.coco.domain.member.dto.SignUpRequest;
import com.nps.coco.domain.member.entity.Member;
import com.nps.coco.domain.member.exception.DuplicateEmailException;
import com.nps.coco.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class SignUpService {

    private final MemberRepository memberRepository;

    private SignUpService (MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void signUp(String email, String name, String password) {
        validateDuplicateEmail(email);
        Member member = new Member(email, name, password);
        memberRepository.save(member);
    }

    private void validateDuplicateEmail(String email) {
        memberRepository.findByEmail(email)
                .ifPresent(user -> {
                    throw new DuplicateEmailException();
                });
    }
}
