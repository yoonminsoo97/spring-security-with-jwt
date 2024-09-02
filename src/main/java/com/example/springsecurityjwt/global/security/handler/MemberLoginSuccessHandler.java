package com.example.springsecurityjwt.global.security.handler;

import com.example.springsecurityjwt.domain.token.dto.TokenResponse;
import com.example.springsecurityjwt.domain.token.service.TokenService;
import com.example.springsecurityjwt.global.common.dto.ApiResponse;
import com.example.springsecurityjwt.global.security.dto.AuthPrincipal;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@RequiredArgsConstructor
public class MemberLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenService tokenService;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        AuthPrincipal authPrincipal = (AuthPrincipal) authentication.getPrincipal();
        TokenResponse tokenResponse = tokenService.saveRefreshToken(authPrincipal);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());
        objectMapper.writeValue(response.getOutputStream(), ApiResponse.success(tokenResponse));
    }

}
