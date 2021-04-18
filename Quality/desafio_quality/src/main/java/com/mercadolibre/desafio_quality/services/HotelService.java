package com.mercadolibre.desafio_quality.services;

import com.mercadolibre.desafio_quality.dtos.BookingRequestDTO;
import com.mercadolibre.desafio_quality.dtos.BookingResponseDTO;
import com.mercadolibre.desafio_quality.dtos.HotelDTO;

import java.util.List;
import java.util.Map;

public interface HotelService {
    List<HotelDTO> getHotels(Map<String, String> params);
    BookingResponseDTO generateBooking(BookingRequestDTO bookingRequestDTO);
}
