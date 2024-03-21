package com.nps.coco.domain.member.controller;

import com.nps.coco.domain.member.dto.SignUpDto;
import com.nps.coco.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signUp")
    public ResponseEntity<SignUpDto> signup(@RequestBody SignUpDto signUpDto) {
        SignUpDto signResponse = memberService.signUp(signUpDto);

        return new ResponseEntity<>(signResponse, HttpStatus.OK);
    }
}