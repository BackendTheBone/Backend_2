package com.nps.coco.domain.member.exception;

public class ValidateMember extends RuntimeException {

    public ValidateMember() {
        super("로그인 상태가 아닙니다.");
    }
}