package com.mercadolibre.desafio_spting.exceptions;

import org.springframework.http.HttpStatus;

public class ArticleNotFoundException extends ApiException {
    public ArticleNotFoundException(String description) {
        super(description, HttpStatus.UNPROCESSABLE_ENTITY.value());
    }
}
