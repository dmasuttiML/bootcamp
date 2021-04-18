package com.mercadolibre.desafio_quality.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.desafio_quality.dtos.HotelDTO;
import com.mercadolibre.desafio_quality.exceptions.InternalServerErrorException;
import com.mercadolibre.desafio_quality.exceptions.InvalidArgumentException;
import com.mercadolibre.desafio_quality.repositories.HotelRepository;
import com.mercadolibre.desafio_quality.repositories.HotelRepositoryImpl;
import com.mercadolibre.desafio_quality.utils.FileMockPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@DisplayName("Test of HotelServiceImpl")
class HotelServiceImplTest {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static List<HotelDTO> mockAllHotels;
    private static List<HotelDTO> mockHotelsWithFilters;

    @InjectMocks
    HotelServiceImpl hotelService;

    @Mock
    HotelRepositoryImpl hotelRepository;

    @BeforeEach
    void setUp() throws IOException {
        openMocks(this);

        TypeReference<List<HotelDTO>> tr = new TypeReference<>() {};
        mockAllHotels = objectMapper.readValue(new File(FileMockPath.FILE_GET_ALL_RESULT), tr);
        mockHotelsWithFilters = objectMapper.readValue(new File(FileMockPath.FILE_GET_WHIT_FILTERS_RESULT), tr);

        List<String> mockDestinations = mockAllHotels.stream()
                                                     .map(HotelDTO::getPlace)
                                                     .distinct()
                                                     .collect(Collectors.toList());

        when(hotelRepository.getHotels()).thenReturn(mockAllHotels);
        when(hotelRepository.getHotels(any(), any(), anyString())).thenReturn(mockHotelsWithFilters);
        when(hotelRepository.getDestinations()).thenReturn(mockDestinations);
    }

    @Test
    @DisplayName("Get hotels")
    void getHotels() {
        Assertions.assertIterableEquals(mockAllHotels, hotelService.getHotels(new HashMap<>()));
    }

    @Test
    @DisplayName("Get hotels to filters")
    void getHotelsWithFilters() {
        Map<String, String> params = new HashMap<>();
        params.put("dateFrom","01/03/2021");
        params.put("dateTo","15/03/2021");
        params.put("destination","Medellín");

        Assertions.assertIterableEquals(mockHotelsWithFilters, hotelService.getHotels(params));
    }

    @Test
    @DisplayName("Get hotels with wrong number of parameters")
    void wrongNumberOfParameters() {
        Map<String, String> params = new HashMap<>();
        params.put("dateFrom","01/03/2021");
        params.put("dateTo","15/03/2021");

        InvalidArgumentException e = Assertions.assertThrows(InvalidArgumentException.class,
                                                                 () -> hotelService.getHotels(params));
        Assertions.assertEquals("Wrong number of parameters", e.getDescription());
    }

    @Test
    @DisplayName("Get hotels with unknown parameter")
    void unknownParameter() {
        Map<String, String> params = new HashMap<>();
        params.put("dateFrom","01/03/2021");
        params.put("dateTo","15/03/2021");
        params.put("dest","Medellín");

        InvalidArgumentException e = Assertions.assertThrows(InvalidArgumentException.class,
                                                             () -> hotelService.getHotels(params));
        Assertions.assertEquals("Unknown parameter: dest", e.getDescription());
    }

    @Test
    @DisplayName("Get hotels with invalid date format")
    void invalidDateFormat() {
        Map<String, String> params = new HashMap<>();
        params.put("dateFrom","01-03-2021");
        params.put("dateTo","15/03/2021");
        params.put("destination","Medellín");

        InvalidArgumentException e = Assertions.assertThrows(InvalidArgumentException.class,
                                                             () -> hotelService.getHotels(params));
        Assertions.assertEquals("Date format must be dd/mm/yyyy: 01-03-2021", e.getDescription());
    }

    @Test
    @DisplayName("Get hotels with invalid date range")
    void withInvalidDateRange() {
        Map<String, String> params = new HashMap<>();
        params.put("dateFrom","15/03/2021");
        params.put("dateTo","01/03/2021");
        params.put("destination","Medellín");

        InvalidArgumentException e = Assertions.assertThrows(InvalidArgumentException.class,
                                                             () -> hotelService.getHotels(params));
        Assertions.assertEquals("The check-in date must be less than the check-out date", e.getDescription());
    }

    @Test
    @DisplayName("Get hotels with unknown destination")
    void unknownDestination() {
        Map<String, String> params = new HashMap<>();
        params.put("dateFrom","01/03/2021");
        params.put("dateTo","15/03/2021");
        params.put("destination","Paraná");

        InvalidArgumentException e = Assertions.assertThrows(InvalidArgumentException.class,
                                                             () -> hotelService.getHotels(params));
        Assertions.assertEquals("The chosen destination does not exist", e.getDescription());
    }
}