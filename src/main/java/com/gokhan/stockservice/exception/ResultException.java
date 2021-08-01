package com.gokhan.stockservice.exception;

import com.gokhan.stockservice.model.enums.ExceptionType;
import lombok.Data;

import java.util.List;

@Data
public class ResultException {
    private ExceptionType type;
    private String message;
    private String code;
    private List<String> details;

    public ResultException(ExceptionType type, String message, String code, List<String> details) {
        this.type = type;
        this.message = message;
        this.code = code;
        this.details = details;
    }
}
