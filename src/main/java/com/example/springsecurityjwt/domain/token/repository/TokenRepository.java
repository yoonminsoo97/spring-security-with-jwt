package com.example.springsecurityjwt.domain.token.repository;

import com.example.springsecurityjwt.domain.token.entity.Token;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByMemberId(Long memberId);

}
