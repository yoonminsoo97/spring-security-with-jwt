package com.example.springsecurityjwt.domain.member.service;

import com.example.springsecurityjwt.domain.member.dto.MemberSignupRequest;
import com.example.springsecurityjwt.domain.member.entity.Member;
import com.example.springsecurityjwt.domain.member.exception.DuplicateNicknameException;
import com.example.springsecurityjwt.domain.member.exception.DuplicateUsernameException;
import com.example.springsecurityjwt.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public void memberNicknameExists(String nickname) {
        if (memberRepository.existsByNickname(nickname)) {
            throw new DuplicateNicknameException();
        }
    }

    @Transactional(readOnly = true)
    public void memberUsernameExists(String username) {
        if (memberRepository.existsByUsername(username)) {
            throw new DuplicateUsernameException();
        }
    }

    @Transactional
    public void memberSignup(MemberSignupRequest memberSignupRequest) {
        memberNicknameExists(memberSignupRequest.getNickname());
        memberUsernameExists(memberSignupRequest.getUsername());
        String encoded = passwordEncoder.encode(memberSignupRequest.getPassword());
        Member member = Member.builder()
                .nickname(memberSignupRequest.getNickname())
                .username(memberSignupRequest.getUsername())
                .password(encoded)
                .build();
        memberRepository.save(member);
    }

}
