package com.mercadolibre.desafio_quality.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.desafio_quality.dtos.FlightDTO;
import com.mercadolibre.desafio_quality.dtos.ReservationDTO;
import com.mercadolibre.desafio_quality.exceptions.InternalServerErrorException;
import com.mercadolibre.desafio_quality.exceptions.ReservationNotFoundException;
import com.mercadolibre.desafio_quality.utils.FileMockPath;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@DisplayName("Tests of HotelRepositoryImpl")
class FlightRepositoryImplTest {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static List<FlightDTO> mockFlightsGetAll;
    private static List<FlightDTO> mockFlightsWithFilters;
    private static List<String> mockFlightsOrigins;
    private static List<String> mockFlightsDestinations;
    private static FlightDTO mockFlightReservation;

    private FlightRepository flightRepository;
    private FlightRepository flightRepositoryBad;

    @BeforeEach
    void setUp() throws IOException {
        flightRepository = new FlightRepositoryImpl(FileMockPath.FILE_FLIGHTS);
        flightRepositoryBad = new FlightRepositoryImpl(FileMockPath.FILE_FLIGHTS_BAD);

        mockFlightsGetAll = objectMapper.readValue(new File(FileMockPath.FILE_FLIGHTS_GET_ALL),
                                                   new TypeReference<>() {});
        mockFlightsWithFilters = objectMapper.readValue(new File(FileMockPath.FILE_FLIGHTS_GET_WHIT_FILTERS),
                                                        new TypeReference<>() {});
        mockFlightsOrigins = objectMapper.readValue(new File(FileMockPath.FILE_FLIGHTS_ORIGINS),
                                                    new TypeReference<>() {});
        mockFlightsDestinations = objectMapper.readValue(new File(FileMockPath.FILE_FLIGHTS_DESTINATIONS),
                                                         new TypeReference<>() {});
        mockFlightReservation = objectMapper.readValue(new File(FileMockPath.FILE_FLIGHTS_RESERVATION),
                                                       new TypeReference<>() {});
    }

    @Test
    @DisplayName("Get all flights")
    void getFlights() {
        Assertions.assertIterableEquals(mockFlightsGetAll, flightRepository.getFlights());
    }

    @Test
    @DisplayName("Get flights by date, origin and destination")
    void getHotelsByDateAndDestination() {
        String origin = "Puerto Iguazú";
        String destination = "Bogotá";
        LocalDate dateFrom = LocalDate.of(2020,2,10);
        LocalDate dateTo = LocalDate.of(2022,3,15);

        Assertions.assertIterableEquals(mockFlightsWithFilters, flightRepository.getFlights(dateFrom,
                                                                                             dateTo,
                                                                                             origin,
                                                                                             destination));
    }

    @Test
    @DisplayName("Database inconsistency")
    void getFlightsDatabaseInconsistency() {
        InternalServerErrorException e = Assertions.assertThrows(InternalServerErrorException.class,
                                                                 () -> flightRepositoryBad.getFlights());
        Assertions.assertEquals("Database inconsistency", e.getDescription());
    }

    @Test
    @DisplayName("Get origins flights")
    void getOrigins() {
        Assertions.assertIterableEquals(mockFlightsOrigins, flightRepository.getOrigins());
    }

    @Test
    @DisplayName("Get destinations flights")
    void getDestinations() {
        Assertions.assertIterableEquals(mockFlightsDestinations, flightRepository.getDestinations());
    }

    @Test
    @DisplayName("Generate reservation")
    void generateBooking() {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setFlightNumber("BAPI-1235");
        reservationDTO.setOrigin("Buenos Aires");
        reservationDTO.setDestination("Puerto Iguazú");
        reservationDTO.setSeatType("Economy");
        reservationDTO.setDateFrom("10/02/2021");
        reservationDTO.setDateTo("15/02/2021");

        FlightDTO flightDTO = flightRepository.generateReservation(reservationDTO);

        Assertions.assertEquals(mockFlightReservation, flightDTO);
    }

    @Test
    @DisplayName("Origin not match")
    void generateBookingOriginNotMatch () {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setFlightNumber("BAPI-1235");
        reservationDTO.setOrigin("Bogota");
        reservationDTO.setDestination("Puerto Iguazú");
        reservationDTO.setSeatType("Economy");
        reservationDTO.setDateFrom("10/02/2021");
        reservationDTO.setDateTo("15/02/2021");

        ReservationNotFoundException e = Assertions.assertThrows(ReservationNotFoundException.class,
                                         () -> flightRepository.generateReservation(reservationDTO));
        Assertions.assertEquals("The origin does not match the flight origin", e.getDescription());
    }

    @Test
    @DisplayName("Destination not match")
    void generateBookingDestinationNotMatch () {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setFlightNumber("BAPI-1235");
        reservationDTO.setOrigin("Buenos Aires");
        reservationDTO.setDestination("Bogota");
        reservationDTO.setSeatType("Economy");
        reservationDTO.setDateFrom("10/02/2021");
        reservationDTO.setDateTo("15/02/2021");

        ReservationNotFoundException e = Assertions.assertThrows(ReservationNotFoundException.class,
                                         () -> flightRepository.generateReservation(reservationDTO));
        Assertions.assertEquals("The destination does not match the flight destination", e.getDescription());
    }
}