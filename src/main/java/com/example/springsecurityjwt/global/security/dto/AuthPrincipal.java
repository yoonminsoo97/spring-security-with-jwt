package com.example.springsecurityjwt.global.security.dto;

import com.example.springsecurityjwt.domain.member.entity.Member;

import org.springframework.security.core.userdetails.User;

import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

public class AuthPrincipal extends User {

    private final Member member;

    public AuthPrincipal(Member member) {
        super(member.getUsername(), member.getPassword(), createAuthorityList(member.getRole().getAuthority()));
        this.member = member;
    }

    public Long getMemberId() {
        return member.getId();
    }

    public String getNickname() {
        return member.getNickname();
    }

    public String getAuthority() {
        return member.getRole().getAuthority();
    }

}
