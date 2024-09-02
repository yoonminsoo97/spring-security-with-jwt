package com.example.springsecurityjwt.domain.token.exception;

import com.example.springsecurityjwt.global.error.exception.ErrorType;

import org.springframework.security.core.AuthenticationException;

public class ExpiredTokenException extends AuthenticationException {

    public ExpiredTokenException() {
        super(ErrorType.EXPIRED_TOKEN.getErrorCode());
    }

}
