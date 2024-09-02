package com.example.springsecurityjwt.domain.member.exception;

import com.example.springsecurityjwt.global.error.exception.BaseException;
import com.example.springsecurityjwt.global.error.exception.ErrorType;

public class DuplicateNicknameException extends BaseException {

    public DuplicateNicknameException() {
        super(ErrorType.DUPLICATE_NICKNAME);
    }

}
