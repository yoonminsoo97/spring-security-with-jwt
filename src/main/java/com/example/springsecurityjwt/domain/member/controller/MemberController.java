package com.example.springsecurityjwt.domain.member.controller;

import com.example.springsecurityjwt.domain.member.dto.MemberProfileResponse;
import com.example.springsecurityjwt.domain.member.dto.MemberSignupRequest;
import com.example.springsecurityjwt.domain.member.service.MemberService;
import com.example.springsecurityjwt.global.common.dto.ApiResponse;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/nickname/{nickname}")
    public ResponseEntity<ApiResponse<Void>> memberNicknameExists(@PathVariable("nickname") String nickname) {
        memberService.memberNicknameExists(nickname);
        return ResponseEntity.ok().body(ApiResponse.success());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<ApiResponse<Void>> memberUsernameExists(@PathVariable("username") String username) {
        memberService.memberUsernameExists(username);
        return ResponseEntity.ok().body(ApiResponse.success());
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> memberSignup(@Valid @RequestBody MemberSignupRequest memberSignupRequest) {
        memberService.memberSignup(memberSignupRequest);
        return ResponseEntity.ok().body(ApiResponse.success());
    }

    @Secured("ROLE_MEMBER")
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<MemberProfileResponse>> memberProfile(@AuthenticationPrincipal Long memberId) {
        MemberProfileResponse memberProfileResponse = memberService.memberProfile(memberId);
        return ResponseEntity.ok().body(ApiResponse.success(memberProfileResponse));
    }

}
