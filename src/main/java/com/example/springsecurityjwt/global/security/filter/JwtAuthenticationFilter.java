package com.example.springsecurityjwt.global.security.filter;

import com.example.springsecurityjwt.domain.token.service.TokenService;
import com.example.springsecurityjwt.global.security.support.RequestURI;

import io.jsonwebtoken.Claims;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = extractToken(request);
            Claims payload = tokenService.getPayload(token);
            Authentication authentication = createAuthentication(payload);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (AuthenticationException authException) {
            authenticationEntryPoint.commence(request, response, authException);
        }
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (StringUtils.hasText(header) && header.startsWith("Bearer")) {
            return header.substring("Bearer".length()).trim();
        }
        return null;
    }

    private Authentication createAuthentication(Claims payload) {
        Long memberId = Long.valueOf(payload.getSubject());
        String authority = payload.get("authority", String.class);
        return UsernamePasswordAuthenticationToken
                .authenticated(memberId, null, createAuthorityList(authority));
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return RequestURI.shouldNotFilter(request);
    }

}
