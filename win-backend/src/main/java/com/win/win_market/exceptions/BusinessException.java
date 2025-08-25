package com.win.win_market.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Exceção para validações de negócio
 */
public class BusinessException extends BaseException {

    public BusinessException(String message) {
        super(message, "BUSINESS_RULE_VIOLATION", HttpStatus.BAD_REQUEST.value());
    }

    public BusinessException(String message, String errorCode) {
        super(message, errorCode, HttpStatus.BAD_REQUEST.value());
    }

    public BusinessException(String message, Throwable cause) {
        super(message, "BUSINESS_RULE_VIOLATION", HttpStatus.BAD_REQUEST.value(), cause);
    }
}
