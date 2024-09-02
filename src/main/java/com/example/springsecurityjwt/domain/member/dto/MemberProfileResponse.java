package com.example.springsecurityjwt.domain.member.dto;

import com.example.springsecurityjwt.domain.member.entity.Member;

import lombok.Getter;

@Getter
public class MemberProfileResponse {

    private String nickname;
    private String username;

    public MemberProfileResponse(Member member) {
        this.nickname = member.getNickname();
        this.username = member.getUsername();
    }

}
