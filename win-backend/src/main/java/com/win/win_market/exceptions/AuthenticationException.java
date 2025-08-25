package com.win.win_market.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Exceção para problemas de autenticação
 */
public class AuthenticationException extends BaseException {

    public AuthenticationException(String message) {
        super(message, "AUTHENTICATION_FAILED", HttpStatus.UNAUTHORIZED.value());
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, "AUTHENTICATION_FAILED", HttpStatus.UNAUTHORIZED.value(), cause);
    }
}
