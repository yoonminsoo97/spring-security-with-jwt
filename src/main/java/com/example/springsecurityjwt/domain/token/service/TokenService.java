package com.example.springsecurityjwt.domain.token.service;

import com.example.springsecurityjwt.domain.token.dto.TokenResponse;
import com.example.springsecurityjwt.domain.token.entity.Token;
import com.example.springsecurityjwt.domain.token.jwt.JwtManager;
import com.example.springsecurityjwt.domain.token.repository.TokenRepository;
import com.example.springsecurityjwt.global.security.dto.AuthPrincipal;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;
    private final JwtManager jwtManager;

    @Transactional
    public TokenResponse saveRefreshToken(AuthPrincipal authPrincipal) {
        String accessToken = jwtManager.createAccessToken(authPrincipal);
        String refreshToken = jwtManager.createRefreshToken();
        tokenRepository.findByMemberId(authPrincipal.getMemberId())
                .ifPresentOrElse(
                        token -> token.update(refreshToken),
                        () -> tokenRepository.save(
                                Token.builder()
                                        .refreshToken(refreshToken)
                                        .memberId(authPrincipal.getMemberId())
                                        .build()
                        )
                );
        return new TokenResponse(accessToken, refreshToken);
    }

}
