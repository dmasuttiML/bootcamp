package com.mercadolibre.desafio_quality.repositories;

import com.mercadolibre.desafio_quality.dtos.BookingDTO;
import com.mercadolibre.desafio_quality.dtos.FlightDTO;
import com.mercadolibre.desafio_quality.dtos.ReservationDTO;

import java.time.LocalDate;
import java.util.List;

public interface FlightRepository {
    List<FlightDTO> getFlights();
    List<FlightDTO> getFlights(LocalDate dateFrom, LocalDate dateTo, String origin, String destination);
    List<String> getOrigins();
    List<String> getDestinations();
    FlightDTO generateReservation(ReservationDTO reservationDTO);
}
