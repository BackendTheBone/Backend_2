package com.nps.coco.domain.user.controller;

import com.nps.coco.domain.user.dto.SignUpDto;
import com.nps.coco.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signUp")
    public ResponseEntity<SignUpDto> signup(@RequestBody SignUpDto signUpDto) {
        SignUpDto signResponse = userService.signUp(signUpDto);

        return new ResponseEntity<>(signResponse, HttpStatus.OK);
    }
}