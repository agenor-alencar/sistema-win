package com.win.win_market.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Exceção para problemas de autorização
 */
public class AuthorizationException extends BaseException {

    public AuthorizationException(String message) {
        super(message, "ACCESS_DENIED", HttpStatus.FORBIDDEN.value());
    }

    public AuthorizationException(String message, Throwable cause) {
        super(message, "ACCESS_DENIED", HttpStatus.FORBIDDEN.value(), cause);
    }
}
