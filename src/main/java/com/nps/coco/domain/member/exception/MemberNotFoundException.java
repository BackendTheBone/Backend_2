package com.nps.coco.domain.member.exception;

public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException(String str) {
        super(str + " 이(가) 오류입니다");
    }
}
