package com.win.win_market.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Exceção para problemas de entrega
 */
public class DeliveryException extends BusinessException {

    public DeliveryException(String message) {
        super(message, "DELIVERY_ERROR");
    }

    public DeliveryException(String message, Throwable cause) {
        super(message, cause);
    }
}
