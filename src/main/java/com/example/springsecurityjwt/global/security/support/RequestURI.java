package com.example.springsecurityjwt.global.security.support;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpMethod;

import java.util.Arrays;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

public enum RequestURI {

    MEMBER_NICKNAME_EXISTS(HttpMethod.GET, "/api/members/nickname/*", true),
    MEMBER_USERNAME_EXISTS(HttpMethod.GET, "/api/members/username/*", true),
    MEMBER_SIGNUP(HttpMethod.POST, "/api/members/signup", true),
    MEMBER_LOGIN(HttpMethod.POST, "/api/auth/login", true),
    MEMBER_LOGOUT(HttpMethod.POST, "/api/auth/logout", false),
    MEMBER_PROFILE(HttpMethod.GET, "/api/members/me", false);

    private final HttpMethod httpMethod;
    private final String pattern;
    private final boolean permitAll;

    RequestURI(HttpMethod httpMethod, String pattern, boolean permitAll) {
        this.httpMethod = httpMethod;
        this.pattern = pattern;
        this.permitAll = permitAll;
    }

    public String pattern() {
        return pattern;
    }

    public static boolean shouldNotFilter(HttpServletRequest request) {
        return Arrays.stream(RequestURI.values())
                .anyMatch(requestURI -> requestURI.permitAll && antMatcher(requestURI.httpMethod, requestURI.pattern()).matches(request));
    }

}
