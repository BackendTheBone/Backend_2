package com.nps.coco.domain.member.controller;

import com.nps.coco.domain.member.dto.*;
import com.nps.coco.domain.member.entity.Member;
import com.nps.coco.domain.member.service.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final SignUpService signUpService;
    private final SignInService signInService;
    private final DeleteService deleteService;
    private final ModifyService modifyService;
    private final EmailService emailService;
    private final HttpSession session;

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest request) {
        try {
            signUpService.signUp(request.getEmail(), request.getName(), request.getPassword());
            return ResponseEntity.accepted().body("ACCEPT");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD");
        }
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest request) {
        try {
            signInService.signIn(request.getPassword(), request.getEmail());
            Member member = (Member) session.getAttribute("member");

            if(member != null) {
                return ResponseEntity.accepted().body("ACCEPT");
            }
            else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
            }
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(){
        try {
            signInService.logout();
            return ResponseEntity.accepted().body("ACCEPT");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD");
        }
    }

    @PostMapping("/deleteMember")
    public ResponseEntity<?> deleteMember(){
        try{
            deleteService.deleteMember();
            session.removeAttribute("member");
            return ResponseEntity.accepted().body("ACCEPT");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD");
        }
    }

    @PostMapping("/passCheck")
    public ResponseEntity<?> modifyMember(@RequestBody PassCheckRequest request){
        Member member = (Member) session.getAttribute("member");
        if (member != null && member.getPassword().equals(request.getPassword())) {
            return ResponseEntity.accepted().body("ACCEPT");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("INVALID");
    }

    @PostMapping("/modifyMember")
    public ResponseEntity<?> modifyMember(@RequestBody ModifyRequest request){
        try{
            modifyService.modifyPassAndName(request.getPassword(), request.getName());
            return ResponseEntity.accepted().body("ACCEPT");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD");
        }
    }

    @PostMapping("/sendEmail")
    public void sendEmail(@RequestBody EmailRequest request) throws Exception {
        emailService.sendEmailMessage(request.getEmail());
    }
}