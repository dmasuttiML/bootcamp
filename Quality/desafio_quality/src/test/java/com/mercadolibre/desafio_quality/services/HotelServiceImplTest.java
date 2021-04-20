package com.mercadolibre.desafio_quality.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.desafio_quality.dtos.BookingRequestDTO;
import com.mercadolibre.desafio_quality.dtos.BookingResponseDTO;
import com.mercadolibre.desafio_quality.dtos.HotelDTO;
import com.mercadolibre.desafio_quality.exceptions.BookingNotFoundException;
import com.mercadolibre.desafio_quality.exceptions.InvalidArgumentException;
import com.mercadolibre.desafio_quality.repositories.HotelRepositoryImpl;
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

@DisplayName("Tests of HotelServiceImpl")
class HotelServiceImplTest {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static List<HotelDTO> mockHotelsGetAll;
    private static List<HotelDTO> mockHotelsWithFilters;
    private static BookingResponseDTO mockBookingResponse;
    private static HotelDTO mockHotelBooking;


    @InjectMocks
    HotelServiceImpl hotelService;

    @Mock
    HotelRepositoryImpl hotelRepository;

    @BeforeEach
    void setUp() throws IOException {
        openMocks(this);

        TypeReference<List<HotelDTO>> tr = new TypeReference<>() {};
        mockHotelsGetAll = objectMapper.readValue(new File(FileMockPath.FILE_HOTELS_GET_ALL), tr);
        mockHotelsWithFilters = objectMapper.readValue(new File(FileMockPath.FILE_HOTELS_GET_WHIT_FILTERS), tr);
        mockBookingResponse = objectMapper.readValue(new File(FileMockPath.FILE_BOOKING_RESPONSE),
                                                     new TypeReference<>() {});
        mockHotelBooking = objectMapper.readValue(new File(FileMockPath.FILE_HOTEL_BOOKING),
                                                  new TypeReference<>() {});

        List<String> mockDestinations = objectMapper.readValue(new File(FileMockPath.FILE_HOTELS_DESTINATIONS),
                                                               new TypeReference<>() {});

        when(hotelRepository.getHotels()).thenReturn(mockHotelsGetAll);
        when(hotelRepository.getHotels(any(), any(), anyString())).thenReturn(mockHotelsWithFilters);
        when(hotelRepository.getDestinations()).thenReturn(mockDestinations);
    }

    @Test
    @DisplayName("Get hotels")
    void getHotels() {
        Assertions.assertIterableEquals(mockHotelsGetAll, hotelService.getHotels(new HashMap<>()));
    }

    @Test
    @DisplayName("Get hotels to filters")
    void getHotelsWithFilters() {
        Map<String, String> params = new HashMap<>();
        params.put("dateFrom","01/03/2020");
        params.put("dateTo","15/03/2022");
        params.put("destination","Medellín");

        Assertions.assertIterableEquals(mockHotelsWithFilters, hotelService.getHotels(params));
    }

    @Test
    @DisplayName("Get hotels with wrong number of parameters")
    void ghWrongNumberOfParameters() {
        Map<String, String> params = new HashMap<>();
        params.put("dateFrom","01/03/2021");
        params.put("dateTo","15/03/2021");

        InvalidArgumentException e = Assertions.assertThrows(InvalidArgumentException.class,
                                                                 () -> hotelService.getHotels(params));
        Assertions.assertEquals("Wrong number of parameters", e.getDescription());
    }

    @Test
    @DisplayName("Get hotels with unknown parameter")
    void ghUnknownParameter() {
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
    void ghInvalidDateFormat() {
        Map<String, String> params = new HashMap<>();
        params.put("dateFrom","01-03-2021");
        params.put("dateTo","15/03/2021");
        params.put("destination","Medellín");

        InvalidArgumentException e = Assertions.assertThrows(InvalidArgumentException.class,
                                                             () -> hotelService.getHotels(params));
        Assertions.assertEquals("Date format must be dd/mm/yyyy", e.getDescription());
    }

    @Test
    @DisplayName("Get hotels with invalid date range")
    void ghWithInvalidDateRange() {
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
    void ghUnknownDestination() {
        Map<String, String> params = new HashMap<>();
        params.put("dateFrom","01/03/2021");
        params.put("dateTo","15/03/2021");
        params.put("destination","Paraná");

        InvalidArgumentException e = Assertions.assertThrows(InvalidArgumentException.class,
                                                             () -> hotelService.getHotels(params));
        Assertions.assertEquals("The chosen destination does not exist", e.getDescription());
    }

    @Test
    @DisplayName("Generate booking")
    void generateBooking() throws IOException {
        BookingRequestDTO mockBookingRequest = objectMapper.readValue(new File(FileMockPath.FILE_BOOKING_REQUEST),
                                               new TypeReference<>() {});
        when(hotelRepository.generateBooking(any())).thenReturn(mockHotelBooking);
        BookingResponseDTO bookingResponseDTO = hotelService.generateBooking(mockBookingRequest);
        Assertions.assertEquals(mockBookingResponse, bookingResponseDTO);
    }

    @Test
    @DisplayName("Hotel not available")
    void gbHotelNotAvailable() throws IOException {
        BookingRequestDTO mockBookingRequest = objectMapper.readValue(new File(FileMockPath.FILE_BOOKING_REQUEST),
                                                                      new TypeReference<>() {});
        when(hotelRepository.generateBooking(any())).thenReturn(null);
        BookingNotFoundException e = Assertions.assertThrows(BookingNotFoundException.class,
                                                             () -> hotelService.generateBooking(mockBookingRequest));
        Assertions.assertEquals("Hotel not available", e.getDescription());
    }

    @Test
    @DisplayName("People amount not match with people array")
    void gbPeopleAmount() throws IOException {
        BookingRequestDTO mockBookingRequest = objectMapper.readValue(new File(FileMockPath.FILE_BOOKING_REQUEST),
                                                                      new TypeReference<>() {});
        mockBookingRequest.getBooking().setPeopleAmount(1);
        when(hotelRepository.generateBooking(any())).thenReturn(mockHotelBooking);

        InvalidArgumentException e = Assertions.assertThrows(InvalidArgumentException.class,
                                                             () -> hotelService.generateBooking(mockBookingRequest));
        Assertions.assertEquals("The people amount does not match the size of the people array",
                                e.getDescription());
    }

    @Test
    @DisplayName("Invalid room type")
    void gbInvalidRoomType() throws IOException {
        BookingRequestDTO mockBookingRequest = objectMapper.readValue(new File(FileMockPath.FILE_BOOKING_REQUEST),
                                                                      new TypeReference<>() {});
        when(hotelRepository.generateBooking(any())).thenReturn(mockHotelBooking);

        Map<String, Integer> map = new HashMap<String, Integer>();

        map.put("Single", 2);
        map.put("Doble", 3);
        map.put("Triple", 4);
        map.put("Múltiple", 11);

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            mockBookingRequest.getBooking().setRoomType(entry.getKey());
            mockBookingRequest.getBooking().setPeopleAmount(entry.getValue());

            InvalidArgumentException e = Assertions.assertThrows(InvalidArgumentException.class,
                                         () -> hotelService.generateBooking(mockBookingRequest));
            Assertions.assertEquals("The selected room type does not matches " +
                                    "the number of people they will stay in it", e.getDescription());
        }

        mockBookingRequest.getBooking().setRoomType("Cuadruple");
        InvalidArgumentException e = Assertions.assertThrows(InvalidArgumentException.class,
                                     () -> hotelService.generateBooking(mockBookingRequest));
        Assertions.assertEquals("Invalid room type", e.getDescription());
    }

    @Test
    @DisplayName("Validate payment method")
    void gbValidatePaymentMethod() throws IOException {
        BookingRequestDTO mockBookingRequest = objectMapper.readValue(new File(FileMockPath.FILE_BOOKING_REQUEST),
                                                                      new TypeReference<>() {});
        when(hotelRepository.generateBooking(any())).thenReturn(mockHotelBooking);

        mockBookingRequest.getBooking().getPaymentMethod().setType("CREDIT");
        mockBookingRequest.getBooking().getPaymentMethod().setDues(18);

        InvalidArgumentException e = Assertions.assertThrows(InvalidArgumentException.class,
                                     () -> hotelService.generateBooking(mockBookingRequest));
        Assertions.assertEquals("Invalid amount of dues for credit", e.getDescription());

        mockBookingRequest.getBooking().getPaymentMethod().setType("DEBIT");
        mockBookingRequest.getBooking().getPaymentMethod().setDues(2);

        e = Assertions.assertThrows(InvalidArgumentException.class,
                                    () -> hotelService.generateBooking(mockBookingRequest));
        Assertions.assertEquals("Invalid amount of dues for debit", e.getDescription());
    }

    @Test
    @DisplayName("Validate people")
    void gbValidatePeople() throws IOException {
        BookingRequestDTO mockBookingRequest = objectMapper.readValue(new File(FileMockPath.FILE_BOOKING_REQUEST),
                                                                      new TypeReference<>() {});
        when(hotelRepository.generateBooking(any())).thenReturn(mockHotelBooking);

        mockBookingRequest.getBooking().getPeople().get(0).setDni("aaaaa");
        InvalidArgumentException e = Assertions.assertThrows(InvalidArgumentException.class,
                                     () -> hotelService.generateBooking(mockBookingRequest));
        Assertions.assertEquals("Invalid DNI", e.getDescription());

        mockBookingRequest.getBooking().getPeople().get(0).setDni("12345678");
        mockBookingRequest.getBooking().getPeople().get(0).setMail("aaaaa.com");
        e = Assertions.assertThrows(InvalidArgumentException.class,
                                    () -> hotelService.generateBooking(mockBookingRequest));
        Assertions.assertEquals("Please enter a valid email", e.getDescription());
    }

}