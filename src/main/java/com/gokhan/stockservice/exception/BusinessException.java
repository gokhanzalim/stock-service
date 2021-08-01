package com.gokhan.stockservice.exception;

import com.gokhan.stockservice.model.enums.ExceptionType;

import java.util.List;

public class BusinessException extends GeneralException {
    public BusinessException(String message, String code, List<String> details) {
        super(ExceptionType.BUSINESS, message, code, details);
    }

    public BusinessException(String message, String code){
        super(ExceptionType.BUSINESS, message, code);
    }
    public BusinessException(String code){
        super(ExceptionType.BUSINESS, code);
    }
}
