package com.example.springsecurityjwt.global.security.service;

import com.example.springsecurityjwt.domain.member.entity.Member;
import com.example.springsecurityjwt.domain.member.repository.MemberRepository;
import com.example.springsecurityjwt.global.security.dto.AuthPrincipal;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberLoginService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("회원을 찾을 수 없습니다."));
        return new AuthPrincipal(member);
    }

}
