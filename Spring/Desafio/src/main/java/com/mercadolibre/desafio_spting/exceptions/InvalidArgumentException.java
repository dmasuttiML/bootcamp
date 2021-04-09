package com.mercadolibre.desafio_spting.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidArgumentException extends ApiException{

    public InvalidArgumentException(String description) {
        super(description, HttpStatus.BAD_REQUEST.value());
    }
}
