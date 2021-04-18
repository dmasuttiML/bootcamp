package com.mercadolibre.desafio_quality.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.desafio_quality.dtos.HotelDTO;
import com.mercadolibre.desafio_quality.exceptions.InternalServerErrorException;
import com.mercadolibre.desafio_quality.utils.FileMockPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@DisplayName("Tests of HotelRepositoryImpl")
class HotelRepositoryImplTest {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static List<HotelDTO> mockAllHotels;
    private static List<HotelDTO> mockHotelsWithFilters;

    private HotelRepository hotelRepository;
    private HotelRepository hotelRepositoryBad;

    @BeforeEach
    void setUp() throws IOException {
        hotelRepository = new HotelRepositoryImpl(FileMockPath.FILE_HOTELS);
        hotelRepositoryBad = new HotelRepositoryImpl(FileMockPath.FILE_HOTELS_BAD);

        mockAllHotels = objectMapper.readValue(new File(FileMockPath.FILE_GET_ALL_RESULT), new TypeReference<>() {});
        mockHotelsWithFilters = objectMapper.readValue(new File(FileMockPath.FILE_GET_WHIT_FILTERS_RESULT), new TypeReference<>() {});
    }


    @Test
    @DisplayName("Get all hotels")
    void getHotels() {
        Assertions.assertIterableEquals(mockAllHotels, hotelRepository.getHotels());
    }

    @Test
    @DisplayName("Database inconsistency")
    void getHotelsDatabaseInconsistency() {
        InternalServerErrorException e = Assertions.assertThrows(InternalServerErrorException.class,
                                                                 () -> hotelRepositoryBad.getHotels());
        Assertions.assertEquals("Database inconsistency", e.getDescription());
    }

    @Test
    @DisplayName("Get hotels by date and destination")
    void getHotelsByDateAndDestination() throws IOException {
        String destination = "Medellín";
        LocalDate dateFrom = LocalDate.of(2021,3,1);
        LocalDate dateTo = LocalDate.of(2021,3,15);

        Assertions.assertIterableEquals(mockHotelsWithFilters, hotelRepository.getHotels(dateFrom, dateTo, destination));
    }
}