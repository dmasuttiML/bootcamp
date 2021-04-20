package com.mercadolibre.desafio_quality.exceptions;

import org.springframework.http.HttpStatus;

public class BookingNotFoundException extends ApiException {
    private static final String INTERNAL_ERROR_CODE = "booking_not_found";

    public BookingNotFoundException(String message) {
        super(INTERNAL_ERROR_CODE, message, HttpStatus.NOT_FOUND.value());
    }
}
