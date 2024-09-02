package com.example.springsecurityjwt.domain.member.exception;

import com.example.springsecurityjwt.global.error.exception.BaseException;
import com.example.springsecurityjwt.global.error.exception.ErrorType;

public class NotFoundMemberException extends BaseException {

    public NotFoundMemberException() {
        super(ErrorType.NOT_FOUNT_MEMBER);
    }

}
