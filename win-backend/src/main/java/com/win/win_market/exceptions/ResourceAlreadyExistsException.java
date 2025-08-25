package com.win.win_market.exceptions;

/**
 * Exceção para quando um recurso já existe
 */
public class ResourceAlreadyExistsException extends BusinessException {

    public ResourceAlreadyExistsException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s já existe com %s: %s", resourceName, fieldName, fieldValue),
              "RESOURCE_ALREADY_EXISTS");
    }

    public ResourceAlreadyExistsException(String message) {
        super(message, "RESOURCE_ALREADY_EXISTS");
    }
}
