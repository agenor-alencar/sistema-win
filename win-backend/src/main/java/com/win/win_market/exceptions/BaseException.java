package com.win.win_market.exceptions;

/**
 * Exceção base para todas as exceções customizadas da aplicação
 */
public abstract class BaseException extends RuntimeException {

    private final String errorCode;
    private final int httpStatus;

    public BaseException(String message, String errorCode, int httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    public BaseException(String message, String errorCode, int httpStatus, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public int getHttpStatus() {
        return httpStatus;
    }
}
