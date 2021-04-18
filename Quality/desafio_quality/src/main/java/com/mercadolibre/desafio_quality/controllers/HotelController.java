package com.mercadolibre.desafio_quality.controllers;

import com.mercadolibre.desafio_quality.dtos.BookingRequestDTO;
import com.mercadolibre.desafio_quality.dtos.BookingResponseDTO;
import com.mercadolibre.desafio_quality.dtos.HotelDTO;
import com.mercadolibre.desafio_quality.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class HotelController {

    @Autowired
    HotelService hotelService;

    @GetMapping("/hotels")
    public ResponseEntity<List<HotelDTO>> getHotels(@RequestParam() Map<String, String> params){
        return ResponseEntity.ok(hotelService.getHotels(params));
    }

    @PostMapping("/booking")
    public ResponseEntity<BookingResponseDTO> generateBooking(@RequestBody BookingRequestDTO bookingRequestDTO){
        return ResponseEntity.ok(hotelService.generateBooking(bookingRequestDTO));
    }
}
