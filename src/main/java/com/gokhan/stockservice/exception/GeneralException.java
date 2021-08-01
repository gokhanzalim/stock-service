package com.gokhan.stockservice.exception;

import com.gokhan.stockservice.model.enums.ExceptionType;
import lombok.Data;

import java.util.List;

@Data
public class GeneralException extends RuntimeException {
    private ExceptionType type;
    private String message;
    private String code;
    private List<String> details;

    public GeneralException(ExceptionType type, String message,String code, List<String> details) {
        this(type, message,code);
        this.details = details;
    }

    public GeneralException(ExceptionType type, String message, String code) {
        this(type,code);
        this.message = message;
    }

    public GeneralException(ExceptionType type, String code) {
        this.type = type;
        this.code = code;
    }
}
