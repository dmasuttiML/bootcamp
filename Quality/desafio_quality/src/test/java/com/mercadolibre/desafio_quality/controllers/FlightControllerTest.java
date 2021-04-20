package com.mercadolibre.desafio_quality.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.desafio_quality.dtos.FlightDTO;
import com.mercadolibre.desafio_quality.dtos.ReservationResponseDTO;
import com.mercadolibre.desafio_quality.exceptions.ApiError;
import com.mercadolibre.desafio_quality.repositories.FlightRepository;
import com.mercadolibre.desafio_quality.utils.FileMockPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Integration tests of FlightController")
class FlightControllerTest {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private FlightRepository flightRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Get flights")
    public void getFlights() throws Exception {
        List<FlightDTO> mock = objectMapper.readValue(new File(FileMockPath.FILE_FLIGHTS_GET_ALL),
                                                      new TypeReference<>() {});

        when(flightRepository.getFlights()).thenReturn(mock);

        MvcResult mvcResult = mockMvc.perform(get("/flights"))
                                     .andExpect(status().isOk())
                                     .andReturn();

        List<FlightDTO> response = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(),
                                                          new TypeReference<>() {});

        Assertions.assertEquals(mock, response);
    }

    @Test
    @DisplayName("Generate reservation")
    public void generateReservation() throws Exception {
        FlightDTO mockFlight = objectMapper.readValue(new File(FileMockPath.FILE_FLIGHTS_RESERVATION),
                                                     new TypeReference<>() {});
        List<String> mockOrigins = objectMapper.readValue(new File(FileMockPath.FILE_FLIGHTS_ORIGINS),
                                                          new TypeReference<>() {});
        List<String> mockDestinations = objectMapper.readValue(new File(FileMockPath.FILE_FLIGHTS_DESTINATIONS),
                                                               new TypeReference<>() {});
        ReservationResponseDTO expected = objectMapper.readValue(new File(FileMockPath.FILE_RESERVATION_RESPONSE),
                                                                 new TypeReference<>() {});

        when(flightRepository.getOrigins()).thenReturn(mockOrigins);
        when(flightRepository.getDestinations()).thenReturn(mockDestinations);
        when(flightRepository.generateReservation(any())).thenReturn(mockFlight);

        String body = Files.readString(Path.of(FileMockPath.FILE_RESERVATION_REQUEST));

        MvcResult mvcResult = mockMvc.perform(post("/flight-reservation")
                                     .content(body)
                                     .contentType(MediaType.APPLICATION_JSON_VALUE))
                                     .andExpect(status().isOk())
                                     .andReturn();

        ReservationResponseDTO response = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(),
                                                                 new TypeReference<>() {});

        Assertions.assertEquals(expected, response);
    }

    @Test
    @DisplayName("Invalid seats exception")
    public void invalidPeopleAmountException() throws Exception {
        String body = Files.readString(Path.of(FileMockPath.FILE_RESERVATION_REQUEST));

        body = body.replace("\"seats\": 2", "\"seats\" : \"DOS\"");
        MvcResult mvcResult = mockMvc.perform(post("/flight-reservation")
                                     .content(body)
                                     .contentType(MediaType.APPLICATION_JSON_VALUE))
                                     .andExpect(status().isBadRequest())
                                     .andReturn();

        ApiError response = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(),
                                                   new TypeReference<>() {});

        Assertions.assertEquals("invalid_argument", response.getError());
        Assertions.assertEquals("The number of people must be a numerical value", response.getMessage());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @DisplayName("Invalid parse integer exception")
    public void invalidParseIntegerException() throws Exception {
        String body = Files.readString(Path.of(FileMockPath.FILE_RESERVATION_REQUEST));

        body = body.replace("\"dues\": 6", "\"dues\" : \"DOS\"");
        MvcResult mvcResult = mockMvc.perform(post("/flight-reservation")
                                     .content(body)
                                     .contentType(MediaType.APPLICATION_JSON_VALUE))
                                     .andExpect(status().is5xxServerError())
                                     .andReturn();

        ApiError response = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(),
                new TypeReference<>() {});

        Assertions.assertEquals("internal_error", response.getError());
        Assertions.assertEquals("Internal server error", response.getMessage());
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
    }
}