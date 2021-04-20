package com.mercadolibre.desafio_quality.exceptions;

import org.springframework.http.HttpStatus;

public class ReservationNotFoundException extends ApiException {
    private static final String INTERNAL_ERROR_CODE = "reservation_not_found";

    public ReservationNotFoundException(String message) {
        super(INTERNAL_ERROR_CODE, message, HttpStatus.NOT_FOUND.value());
    }
}
