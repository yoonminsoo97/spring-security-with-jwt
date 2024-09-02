package com.example.springsecurityjwt.domain.member.exception;

import com.example.springsecurityjwt.global.error.exception.BaseException;
import com.example.springsecurityjwt.global.error.exception.ErrorType;

public class DuplicateUsernameException extends BaseException {

    public DuplicateUsernameException() {
        super(ErrorType.DUPLICATE_USERNAME);
    }

}
