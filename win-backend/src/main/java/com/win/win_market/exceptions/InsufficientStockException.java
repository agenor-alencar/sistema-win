package com.win.win_market.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Exceção para quando há estoque insuficiente
 */
public class InsufficientStockException extends BusinessException {

    public InsufficientStockException(String productName, int requested, int available) {
        super(String.format("Estoque insuficiente para o produto '%s'. Solicitado: %d, Disponível: %d",
              productName, requested, available), "INSUFFICIENT_STOCK");
    }
}
