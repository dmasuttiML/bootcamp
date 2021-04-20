package com.mercadolibre.desafio_quality.controllers;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.mercadolibre.desafio_quality.dtos.*;
import com.mercadolibre.desafio_quality.exceptions.ApiError;
import com.mercadolibre.desafio_quality.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/*
 * Controller for flights.
 * */
@RestController
public class FlightController {

    @Autowired
    FlightService flightService;

    /*
     * Endpoint that gets a list of flights
     * If params is empty, return a list of all available flights.
     * If params contains "dateFrom", "dateTo", "origin", and "destination",
     * it returns a list of flights filtered by them.
     * */
    @GetMapping("/flights")
    public ResponseEntity<List<FlightDTO>> getFlights(@RequestParam() Map<String, String> params){
        return ResponseEntity.ok(flightService.getFlights(params));
    }

    /*
     * Endpoint that generates a flight reservation.
     * Request example: src/test/resources/mockReservationRequest.json
     * Response example: src/test/resources/mockReservationResponse.json
     * */
    @PostMapping("/flight-reservation")
    public ResponseEntity generateReservation(@RequestBody ReservationRequestDTO reservationRequestDTO){
        return ResponseEntity.ok(flightService.generateReservation(reservationRequestDTO));
    }


    /*
     * Class for handling seats exception.
     * */
    @ExceptionHandler(value = { InvalidFormatException.class })
    protected ResponseEntity<ApiError> invalidFormatException(InvalidFormatException e) {
        ApiError apiError;

        if (e.getPathReference().contains("seats"))
            apiError = new ApiError("invalid_argument", "The number of people must be a numerical value",
                    HttpStatus.BAD_REQUEST.value());
        else
            apiError = new ApiError("internal_error", "Internal server error",
                                    HttpStatus.INTERNAL_SERVER_ERROR.value());

        return ResponseEntity.status(apiError.getStatus())
                .body(apiError);
    }
}
