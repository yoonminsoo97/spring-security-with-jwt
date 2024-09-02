package com.example.springsecurityjwt.global.security.handler;

import com.example.springsecurityjwt.global.common.dto.ApiResponse;
import com.example.springsecurityjwt.global.error.dto.ErrorResponse;
import com.example.springsecurityjwt.global.error.exception.ErrorType;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

@RequiredArgsConstructor
public class MemberLoginFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        ErrorResponse errorResponse = ErrorResponse.of(ErrorType.BAD_CREDENTIALS);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        objectMapper.writeValue(response.getOutputStream(), ApiResponse.fail(errorResponse));
    }

}
