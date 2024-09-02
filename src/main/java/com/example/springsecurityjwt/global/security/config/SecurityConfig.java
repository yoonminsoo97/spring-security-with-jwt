package com.example.springsecurityjwt.global.security.config;

import com.example.springsecurityjwt.domain.token.service.TokenService;
import com.example.springsecurityjwt.global.security.filter.JwtAuthenticationFilter;
import com.example.springsecurityjwt.global.security.handler.JwtAuthenticationEntryPoint;
import com.example.springsecurityjwt.global.security.handler.MemberLoginFailureHandler;
import com.example.springsecurityjwt.global.security.handler.MemberLoginSuccessHandler;
import com.example.springsecurityjwt.global.security.handler.MemberLogoutSuccessHandler;
import com.example.springsecurityjwt.global.security.support.RequestURI;
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
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

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
                .addFilterBefore(jwtAuthenticationFilter(), LogoutFilter.class)
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint())
                )
                .formLogin(form -> form
                        .loginProcessingUrl(RequestURI.MEMBER_LOGIN.pattern())
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .successHandler(memberLoginSuccessHandler())
                        .failureHandler(memberLoginFailureHandler())
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl(RequestURI.MEMBER_LOGOUT.pattern())
                        .logoutSuccessHandler(memberLogoutSuccessHandler())
                        .permitAll(false)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET,
                                RequestURI.MEMBER_NICKNAME_EXISTS.pattern(),
                                RequestURI.MEMBER_USERNAME_EXISTS.pattern()).permitAll()
                        .requestMatchers(HttpMethod.POST,
                                RequestURI.MEMBER_SIGNUP.pattern()).permitAll()
                        .requestMatchers(HttpMethod.GET,
                                RequestURI.MEMBER_PROFILE.pattern()).hasRole("MEMBER")
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

    @Bean
    public LogoutSuccessHandler memberLogoutSuccessHandler() {
        return new MemberLogoutSuccessHandler(tokenService, objectMapper);
    }

    @Bean
    public AuthenticationEntryPoint jwtAuthenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint(objectMapper);
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(tokenService, jwtAuthenticationEntryPoint());
    }

}
