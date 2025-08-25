package com.win.win_market.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Exceção para problemas relacionados a pagamentos
 */
public class PaymentException extends BusinessException {

    public PaymentException(String message) {
        super(message, "PAYMENT_ERROR");
    }

    public PaymentException(String message, Throwable cause) {
        super(message, cause);
    }
}
