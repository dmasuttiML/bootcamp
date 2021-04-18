package com.mercadolibre.desafio_quality.controllers;

import com.mercadolibre.desafio_quality.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlightController {

    @Autowired
    FlightService flightService;

}
