package com.win.win_market.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Classe para padronizar respostas de erro da API
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    OffsetDateTime timestamp,

    int status,

    String error,

    String message,

    String errorCode,

    String path,

    List<FieldError> fieldErrors
) {

    public static ErrorResponse of(int status, String error, String message, String errorCode, String path) {
        return new ErrorResponse(
            OffsetDateTime.now(),
            status,
            error,
            message,
            errorCode,
            path,
            null
        );
    }

    public static ErrorResponse of(int status, String error, String message, String errorCode, String path, List<FieldError> fieldErrors) {
        return new ErrorResponse(
            OffsetDateTime.now(),
            status,
            error,
            message,
            errorCode,
            path,
            fieldErrors
        );
    }

    public record FieldError(
        String field,
        Object rejectedValue,
        String message
    ) {}
}
