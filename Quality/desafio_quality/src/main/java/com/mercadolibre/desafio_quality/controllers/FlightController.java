package com.mercadolibre.desafio_quality.controllers;

import com.mercadolibre.desafio_quality.dtos.*;
import com.mercadolibre.desafio_quality.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class FlightController {

    @Autowired
    FlightService flightService;

    @GetMapping("/flights")
    public ResponseEntity<List<FlightDTO>> getFlights(@RequestParam() Map<String, String> params){
        return ResponseEntity.ok(flightService.getFlights(params));
    }

    @PostMapping("/flight-reservation")
    public ResponseEntity generateReservation(@RequestBody ReservationRequestDTO reservationRequestDTO){
        return ResponseEntity.ok(flightService.generateReservation(reservationRequestDTO));
    }
}
