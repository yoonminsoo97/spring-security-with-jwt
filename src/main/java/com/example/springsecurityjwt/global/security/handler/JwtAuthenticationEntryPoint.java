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
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        ErrorType errorType = ErrorType.of(authException.getMessage());
        ErrorResponse errorResponse = ErrorResponse.of(errorType);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        objectMapper.writeValue(response.getOutputStream(), ApiResponse.fail(errorResponse));
    }

}
