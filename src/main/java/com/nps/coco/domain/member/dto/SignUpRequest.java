package com.nps.coco.domain.member.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class SignUpRequest {

    private String email;

    private String password;

    private String name;
}