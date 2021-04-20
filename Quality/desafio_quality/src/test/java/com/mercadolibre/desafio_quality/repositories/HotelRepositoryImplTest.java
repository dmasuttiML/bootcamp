package com.mercadolibre.desafio_quality.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.desafio_quality.dtos.BookingDTO;
import com.mercadolibre.desafio_quality.dtos.HotelDTO;
import com.mercadolibre.desafio_quality.exceptions.BookingNotFoundException;
import com.mercadolibre.desafio_quality.exceptions.InternalServerErrorException;
import com.mercadolibre.desafio_quality.utils.FileMockPath;
import com.mercadolibre.desafio_quality.utils.HelperCSV;
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
    private static List<HotelDTO> mockHotelsGetAll;
    private static List<HotelDTO> mockHotelsWithFilters;
    private static List<String> mockHotelsDestinations;
    private static HotelDTO mockHotelBooking;

    private HotelRepository hotelRepository;
    private HotelRepository hotelRepositoryBad;
    private HotelRepository hotelRepositoryToWrite;

    @BeforeEach
    void setUp() throws IOException {
        hotelRepository = new HotelRepositoryImpl(FileMockPath.FILE_HOTELS);
        hotelRepositoryBad = new HotelRepositoryImpl(FileMockPath.FILE_HOTELS_BAD);
        hotelRepositoryToWrite = new HotelRepositoryImpl(FileMockPath.FILE_MOCK_WRITE);

        mockHotelsGetAll = objectMapper.readValue(new File(FileMockPath.FILE_HOTELS_GET_ALL),
                                                  new TypeReference<>() {});
        mockHotelsWithFilters = objectMapper.readValue(new File(FileMockPath.FILE_HOTELS_GET_WHIT_FILTERS),
                                                       new TypeReference<>() {});
        mockHotelsDestinations = objectMapper.readValue(new File(FileMockPath.FILE_HOTELS_DESTINATIONS),
                                                        new TypeReference<>() {});
        mockHotelBooking = objectMapper.readValue(new File(FileMockPath.FILE_HOTEL_BOOKING),
                                                  new TypeReference<>() {});
    }

    @Test
    @DisplayName("Get all hotels")
    void getHotels() {
        Assertions.assertIterableEquals(mockHotelsGetAll, hotelRepository.getHotels());
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
        LocalDate dateFrom = LocalDate.of(2020,3,1);
        LocalDate dateTo = LocalDate.of(2022,3,15);

        Assertions.assertIterableEquals(mockHotelsWithFilters, hotelRepository.getHotels(dateFrom,
                                                                                         dateTo,
                                                                                         destination));
    }

    @Test
    @DisplayName("Get destinations hotels")
    void getDestinations() {
        Assertions.assertIterableEquals(mockHotelsDestinations, hotelRepository.getDestinations());
    }

    @Test
    @DisplayName("Generate booking")
    void generateBooking() {
        // Clean the file to write
        List<String[]> lines = HelperCSV.readCSV(FileMockPath.FILE_HOTELS);
        HelperCSV.writeCSV(lines, FileMockPath.FILE_MOCK_WRITE);

        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setHotelCode("BG-0004");
        bookingDTO.setDestination("Cartagena");
        bookingDTO.setRoomType("Múltiple");
        bookingDTO.setDateFrom("17/04/2021");
        bookingDTO.setDateTo("12/06/2021");

        HotelDTO hotelDTO = hotelRepositoryToWrite.generateBooking(bookingDTO);

        Assertions.assertEquals(mockHotelBooking, hotelDTO);

        hotelDTO = hotelRepositoryToWrite.generateBooking(bookingDTO);

        Assertions.assertNull(hotelDTO);
    }

    @Test
    @DisplayName("Destination not match")
    void generateBookingDestinationNotMatch () {
        // Clean the file to write
        List<String[]> lines = HelperCSV.readCSV(FileMockPath.FILE_HOTELS);
        HelperCSV.writeCSV(lines, FileMockPath.FILE_MOCK_WRITE);

        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setHotelCode("BG-0004");
        bookingDTO.setDestination("Buenos Aires");
        bookingDTO.setRoomType("Múltiple");
        bookingDTO.setDateFrom("17/04/2021");
        bookingDTO.setDateTo("12/06/2021");

        BookingNotFoundException e = Assertions.assertThrows(BookingNotFoundException.class,
                                                             () -> hotelRepositoryToWrite.generateBooking(bookingDTO));
        Assertions.assertEquals("The destination does not match the hotel location", e.getDescription());
    }
}