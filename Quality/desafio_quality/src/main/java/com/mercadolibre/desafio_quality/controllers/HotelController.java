package com.mercadolibre.desafio_quality.controllers;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.mercadolibre.desafio_quality.dtos.BookingRequestDTO;
import com.mercadolibre.desafio_quality.dtos.BookingResponseDTO;
import com.mercadolibre.desafio_quality.dtos.HotelDTO;
import com.mercadolibre.desafio_quality.exceptions.ApiError;
import com.mercadolibre.desafio_quality.exceptions.InternalServerErrorException;
import com.mercadolibre.desafio_quality.exceptions.InvalidArgumentException;
import com.mercadolibre.desafio_quality.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/*
* Controller for hotels.
* */
@RestController
public class HotelController {

    @Autowired
    HotelService hotelService;

    /*
    * Endpoint that gets a list of hotels
    * If params is empty, return a list of all available hotels.
    * if params contains "dateFrom", "dateTo", and "destination", it returns a list of hotels filtered by them.
    * */
    @GetMapping("/hotels")
    public ResponseEntity<List<HotelDTO>> getHotels(@RequestParam() Map<String, String> params){
        return ResponseEntity.ok(hotelService.getHotels(params));
    }

    /*
    * Endpoint that generates a hotel reservation.
    * Request example: src/test/resources/mockBookingRequest.json
    * Response example: src/test/resources/mockBookingResponse.json
    * */
    @PostMapping("/booking")
    public ResponseEntity<BookingResponseDTO> generateBooking(@RequestBody BookingRequestDTO bookingRequestDTO){
        return ResponseEntity.ok(hotelService.generateBooking(bookingRequestDTO));
    }

    /*
     * Class for handling peopleAmount exception.
     * */
    @ExceptionHandler(value = { InvalidFormatException.class })
    protected ResponseEntity<ApiError> invalidFormatException(InvalidFormatException e) {
        ApiError apiError;

        if (e.getPathReference().contains("peopleAmount"))
            apiError = new ApiError("invalid_argument", "The number of people must be a numerical value",
                                    HttpStatus.BAD_REQUEST.value());
        else
            apiError = new ApiError("internal_error", "Internal server error",
                                    HttpStatus.INTERNAL_SERVER_ERROR.value());

        return ResponseEntity.status(apiError.getStatus())
                .body(apiError);
    }
}
