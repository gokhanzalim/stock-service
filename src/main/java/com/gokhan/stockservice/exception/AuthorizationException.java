package com.gokhan.stockservice.exception;

import com.gokhan.stockservice.model.enums.ExceptionType;

import java.util.List;

public class AuthorizationException extends GeneralException {
    public AuthorizationException(String message, String code, List<String> details) {
        super(ExceptionType.AUTHORIZATION, message, code, details);
    }

    public AuthorizationException(String message, String code){
        super(ExceptionType.AUTHORIZATION, message, code);
    }
    public AuthorizationException(String code){
        super(ExceptionType.AUTHORIZATION, code);
    }
}
