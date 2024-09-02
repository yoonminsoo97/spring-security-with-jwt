package com.example.springsecurityjwt.domain.member.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;

@Getter
public class MemberSignupRequest {

    @NotBlank(message = "닉네임을 입력해 주세요.")
    private String nickname;

    @NotBlank(message = "아이디를 입력해 주세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    private String password;

}
