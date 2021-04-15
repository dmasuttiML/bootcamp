package com.mercadolibre.desafio_quality.exceptions;

import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends ApiException {
    public InternalServerErrorException(String description) {
        super(description, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
