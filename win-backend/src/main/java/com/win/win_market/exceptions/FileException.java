package com.win.win_market.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Exceção para problemas com arquivos e uploads
 */
public class FileException extends BaseException {

    public FileException(String message) {
        super(message, "FILE_ERROR", HttpStatus.BAD_REQUEST.value());
    }

    public FileException(String message, Throwable cause) {
        super(message, "FILE_ERROR", HttpStatus.BAD_REQUEST.value(), cause);
    }
}
