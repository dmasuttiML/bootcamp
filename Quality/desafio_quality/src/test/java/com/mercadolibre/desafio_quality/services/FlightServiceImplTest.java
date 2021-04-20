package com.mercadolibre.desafio_quality.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.desafio_quality.dtos.*;
import com.mercadolibre.desafio_quality.exceptions.InvalidArgumentException;
import com.mercadolibre.desafio_quality.exceptions.ReservationNotFoundException;
import com.mercadolibre.desafio_quality.repositories.FlightRepositoryImpl;
import com.mercadolibre.desafio_quality.utils.FileMockPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@DisplayName("Tests of FlightServiceImplTest")
class FlightServiceImplTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static List<FlightDTO> mockFlightsGetAll;
    private static List<FlightDTO> mockFlightsWithFilters;
    private static ReservationResponseDTO mockReservationResponse;
    private static FlightDTO mockFlightReservation;


    @InjectMocks
    FlightServiceImpl flightService;

    @Mock
    FlightRepositoryImpl flightRepository;

    @BeforeEach
    void setUp() throws IOException {
        openMocks(this);

        TypeReference<List<FlightDTO>> tr = new TypeReference<>() {};
        mockFlightsGetAll = objectMapper.readValue(new File(FileMockPath.FILE_FLIGHTS_GET_ALL), tr);
        mockFlightsWithFilters = objectMapper.readValue(new File(FileMockPath.FILE_FLIGHTS_GET_WHIT_FILTERS), tr);
        mockReservationResponse = objectMapper.readValue(new File(FileMockPath.FILE_RESERVATION_RESPONSE),
                                                         new TypeReference<>() {});
        mockFlightReservation = objectMapper.readValue(new File(FileMockPath.FILE_FLIGHTS_RESERVATION),
                                                       new TypeReference<>() {});

        List<String> mockOrigins = objectMapper.readValue(new File(FileMockPath.FILE_FLIGHTS_ORIGINS),
                                                          new TypeReference<>() {});
        List<String> mockDestinations = objectMapper.readValue(new File(FileMockPath.FILE_FLIGHTS_DESTINATIONS),
                                                               new TypeReference<>() {});

        when(flightRepository.getFlights()).thenReturn(mockFlightsGetAll);
        when(flightRepository.getFlights(any(), any(), anyString(), anyString())).thenReturn(mockFlightsWithFilters);
        when(flightRepository.getOrigins()).thenReturn(mockOrigins);
        when(flightRepository.getDestinations()).thenReturn(mockDestinations);
    }

    @Test
    @DisplayName("Get flights")
    void getFlights() {
        Assertions.assertIterableEquals(mockFlightsGetAll, flightService.getFlights(new HashMap<>()));
    }

    @Test
    @DisplayName("Get flights to filters")
    void getFlightsWithFilters() {
        Map<String, String> params = new HashMap<>();
        params.put("dateFrom", "10/02/2020");
        params.put("dateTo", "15/03/2022");
        params.put("origin", "Puerto Iguazú");
        params.put("destination", "Bogotá");

        Assertions.assertIterableEquals(mockFlightsWithFilters, flightService.getFlights(params));
    }

    @Test
    @DisplayName("Get flights with wrong number of parameters")
    void gfWrongNumberOfParameters() {
        Map<String, String> params = new HashMap<>();
        params.put("dateFrom", "10/02/2020");
        params.put("dateTo", "15/03/2022");
        params.put("origin", "Puerto Iguazú");

        InvalidArgumentException e = Assertions.assertThrows(InvalidArgumentException.class,
                                     () -> flightService.getFlights(params));
        Assertions.assertEquals("Wrong number of parameters", e.getDescription());
    }

    @Test
    @DisplayName("Get flights with unknown parameter")
    void ghUnknownParameter() {
        Map<String, String> params = new HashMap<>();
        params.put("dateFrom", "01/03/2021");
        params.put("dateTo", "15/03/2021");
        params.put("origin", "Puerto Iguazú");
        params.put("dest", "Medellín");

        InvalidArgumentException e = Assertions.assertThrows(InvalidArgumentException.class,
                                     () -> flightService.getFlights(params));
        Assertions.assertEquals("Unknown parameter: dest", e.getDescription());
    }

    @Test
    @DisplayName("Get flights with unknown origin")
    void gfUnknownOrigin() {
        Map<String, String> params = new HashMap<>();
        params.put("dateFrom", "01/03/2021");
        params.put("dateTo", "15/03/2021");
        params.put("origin", "Paraná");
        params.put("destination", "Medellín");

        InvalidArgumentException e = Assertions.assertThrows(InvalidArgumentException.class,
                                     () -> flightService.getFlights(params));
        Assertions.assertEquals("The chosen origin does not exist", e.getDescription());
    }

    @Test
    @DisplayName("Get flights with unknown destination")
    void gfUnknownDestination() {
        Map<String, String> params = new HashMap<>();
        params.put("dateFrom", "01/03/2021");
        params.put("dateTo", "15/03/2021");
        params.put("origin", "Puerto Iguazú");
        params.put("destination", "Paraná");

        InvalidArgumentException e = Assertions.assertThrows(InvalidArgumentException.class,
                                     () -> flightService.getFlights(params));
        Assertions.assertEquals("The chosen destination does not exist", e.getDescription());
    }

    @Test
    @DisplayName("Generate reservation")
    void generateBooking() throws IOException {
        ReservationRequestDTO mockReservationRequest = objectMapper
                                                       .readValue(new File(FileMockPath.FILE_RESERVATION_REQUEST),
                                                                  new TypeReference<>() {});
        when(flightRepository.generateReservation(any())).thenReturn(mockFlightReservation);
        ReservationResponseDTO reservationResponseDTO = flightService.generateReservation(mockReservationRequest);
        Assertions.assertEquals(mockReservationResponse, reservationResponseDTO);
    }

    @Test
    @DisplayName("Flight not available")
    void grHotelNotAvailable() throws IOException {
        ReservationRequestDTO mockReservationRequest = objectMapper
                                                       .readValue(new File(FileMockPath.FILE_RESERVATION_REQUEST),
                                                                  new TypeReference<>() {});
        when(flightRepository.generateReservation(any())).thenReturn(null);
        ReservationNotFoundException e = Assertions.assertThrows(ReservationNotFoundException.class,
                                         () -> flightService.generateReservation(mockReservationRequest));
        Assertions.assertEquals("Flight not available", e.getDescription());
    }

    @Test
    @DisplayName("People amount not match with people array")
    void grPeopleAmount() throws IOException {
        ReservationRequestDTO mockReservationRequest = objectMapper
                                                      .readValue(new File(FileMockPath.FILE_RESERVATION_REQUEST),
                                                                 new TypeReference<>() {});
        mockReservationRequest.getFlightReservation().setSeats(1);
        when(flightRepository.generateReservation(any())).thenReturn(mockFlightReservation);

        InvalidArgumentException e = Assertions.assertThrows(InvalidArgumentException.class,
                () -> flightService.generateReservation(mockReservationRequest));
        Assertions.assertEquals("The sets does not match the size of the people array",
                e.getDescription());
    }
}