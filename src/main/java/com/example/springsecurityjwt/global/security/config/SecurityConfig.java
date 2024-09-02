package com.example.springsecurityjwt.global.security.config;

import com.example.springsecurityjwt.domain.token.service.TokenService;
import com.example.springsecurityjwt.global.security.handler.MemberLoginFailureHandler;
import com.example.springsecurityjwt.global.security.handler.MemberLoginSuccessHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableMethodSecurity(securedEnabled = true)
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenService tokenService;
    private final ObjectMapper objectMapper;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(form -> form
                        .loginProcessingUrl("/api/auth/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .successHandler(memberLoginSuccessHandler())
                        .failureHandler(memberLoginFailureHandler())
                        .permitAll()
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET,
                                "/api/members/nickname/*",
                                "/api/members/username/*").permitAll()
                        .requestMatchers(HttpMethod.POST,
                                "/api/members/signup").permitAll()
                        .anyRequest().denyAll()
                );
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationSuccessHandler memberLoginSuccessHandler() {
        return new MemberLoginSuccessHandler(tokenService, objectMapper);
    }

    @Bean
    public AuthenticationFailureHandler memberLoginFailureHandler() {
        return new MemberLoginFailureHandler(objectMapper);
    }

}
