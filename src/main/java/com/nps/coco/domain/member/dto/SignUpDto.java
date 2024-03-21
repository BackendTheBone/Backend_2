package com.nps.coco.domain.member.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class SignUpDto {

    private String email;

    private String password;

    private String name;

    public SignUpDto(String email) {
        this.email = email;
    }
}