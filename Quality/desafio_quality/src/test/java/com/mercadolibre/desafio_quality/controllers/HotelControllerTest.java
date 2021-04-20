package com.mercadolibre.desafio_quality.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.desafio_quality.dtos.HotelDTO;
import com.mercadolibre.desafio_quality.exceptions.ApiError;
import com.mercadolibre.desafio_quality.exceptions.InternalServerErrorException;
import com.mercadolibre.desafio_quality.exceptions.InvalidArgumentException;
import com.mercadolibre.desafio_quality.repositories.HotelRepository;
import com.mercadolibre.desafio_quality.utils.FileMockPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.File;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Integration tests of HotelController")
class HotelControllerTest {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private HotelRepository hotelRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    static void setUp(){
    }

    @Test
    @DisplayName("Get hotels")
    public void getHotels() throws Exception {
        List<HotelDTO> mock =  objectMapper.readValue(new File(FileMockPath.FILE_HOTELS_GET_ALL),
                                                      new TypeReference<>() {});

        when(hotelRepository.getHotels()).thenReturn(mock);

        MvcResult mvcResult = mockMvc.perform(get("/hotels"))
                                     .andDo(print())
                                     .andExpect(status().isOk())
                                     .andReturn();

        List<HotelDTO> response = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), new TypeReference<>() {});

        Assertions.assertEquals(mock, response);
    }

    @Test
    @DisplayName("Unknown exception")
    public void internalUnknownException() throws Exception {
        when(hotelRepository.getHotels()).thenThrow(new RuntimeException("Internal server error"));

        MvcResult mvcResult = mockMvc.perform(get("/hotels"))
                                     .andDo(print())
                                     .andExpect(status().is5xxServerError())
                                     .andReturn();

        ApiError response = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(),
                                                   new TypeReference<>() {});

        Assertions.assertEquals("internal_error", response.getError());
        Assertions.assertEquals("Internal server error", response.getMessage());
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
    }

    @Test
    @DisplayName("Invalid Argument Exception")
    public void invalidArgumentException() throws Exception {
        when(hotelRepository.getHotels()).thenThrow(new InvalidArgumentException("Invalid argument"));

        MvcResult mvcResult = mockMvc.perform(get("/hotels"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();

        ApiError response = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(),
                                                   new TypeReference<>() {});

        Assertions.assertEquals("invalid_argument", response.getError());
        Assertions.assertEquals("Invalid argument", response.getMessage());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }
}