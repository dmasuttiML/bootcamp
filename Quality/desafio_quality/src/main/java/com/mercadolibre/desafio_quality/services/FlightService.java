package com.mercadolibre.desafio_quality.services;

import com.mercadolibre.desafio_quality.dtos.FlightDTO;
import com.mercadolibre.desafio_quality.dtos.ReservationRequestDTO;
import com.mercadolibre.desafio_quality.dtos.ReservationResponseDTO;

import java.util.List;
import java.util.Map;

public interface FlightService {
    List<FlightDTO> getFlights(Map<String, String> params);
    ReservationResponseDTO generateReservation(ReservationRequestDTO reservationRequestDTO);
}
