package com.mercadolibre.desafio_spting.exceptions;

import org.springframework.http.HttpStatus;

public class ClientNotFoundException extends ApiException{
    public ClientNotFoundException(String description) {
        super(description, HttpStatus.UNPROCESSABLE_ENTITY.value());
    }
}
