package com.mercadolibre.desafio_spting.exceptions;

import lombok.Data;

@Data
public class ApiException extends RuntimeException {
    private final String description;
    private final Integer statusCode;

    public ApiException(String description, Integer statusCode) {
        super(description);
        this.description = description;
        this.statusCode = statusCode;
    }
}