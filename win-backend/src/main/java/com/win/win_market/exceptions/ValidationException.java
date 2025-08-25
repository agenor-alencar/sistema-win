package com.win.win_market.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Exceção para validações de entrada inválidas
 */
public class ValidationException extends BaseException {

    public ValidationException(String message) {
        super(message, "VALIDATION_ERROR", HttpStatus.BAD_REQUEST.value());
    }

    public ValidationException(String message, String fieldName) {
        super(String.format("Erro de validação no campo '%s': %s", fieldName, message),
              "VALIDATION_ERROR", HttpStatus.BAD_REQUEST.value());
    }
}
