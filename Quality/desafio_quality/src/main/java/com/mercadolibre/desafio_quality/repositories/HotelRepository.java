package com.mercadolibre.desafio_quality.repositories;

import com.mercadolibre.desafio_quality.dtos.BookingDTO;
import com.mercadolibre.desafio_quality.dtos.HotelDTO;

import java.time.LocalDate;
import java.util.List;

public interface HotelRepository {
    List<HotelDTO> getHotels();
    List<HotelDTO> getHotels(LocalDate dateFrom, LocalDate dateTo, String destination);
    List<String> getDestinations();
    HotelDTO generateBooking(BookingDTO bookingDTO);
}
