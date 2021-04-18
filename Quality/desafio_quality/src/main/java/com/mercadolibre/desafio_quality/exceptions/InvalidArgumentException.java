package com.mercadolibre.desafio_quality.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidArgumentException extends ApiException{
    private static final String INTERNAL_ERROR_CODE = "invalid_argument";

    public InvalidArgumentException(String message) {
        super(INTERNAL_ERROR_CODE, message, HttpStatus.BAD_REQUEST.value());
    }
}
