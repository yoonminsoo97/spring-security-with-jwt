package com.example.springsecurityjwt.domain.token.exception;

import com.example.springsecurityjwt.global.error.exception.ErrorType;

import org.springframework.security.core.AuthenticationException;

public class InvalidTokenException extends AuthenticationException {

    public InvalidTokenException() {
        super(ErrorType.INVALID_TOKEN.getErrorCode());
    }

}
