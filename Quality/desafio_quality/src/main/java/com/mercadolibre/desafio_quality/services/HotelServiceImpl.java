package com.mercadolibre.desafio_quality.services;

import com.mercadolibre.desafio_quality.dtos.*;
import com.mercadolibre.desafio_quality.exceptions.BookingNotFoundException;
import com.mercadolibre.desafio_quality.exceptions.InvalidArgumentException;
import com.mercadolibre.desafio_quality.repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class HotelServiceImpl extends BookableService implements HotelService {

    private static final String DATE_FROM_KEY = "dateFrom";
    private static final String DATE_TO_KEY = "dateTo";
    private static final String DESTINATION_KEY = "destination";
    private static final List<String> keys = Arrays.asList(DATE_FROM_KEY, DATE_TO_KEY, DESTINATION_KEY);

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public List<HotelDTO> getHotels(Map<String, String> params) {
        List<HotelDTO> hotels = null;

        if (params.size() == 3) {
            for (String param : params.keySet()) {
                if(!keys.contains(param))
                    throw new InvalidArgumentException("Unknown parameter: " + param);
            }

            LocalDate dateFrom = getAndValidateDate(params.get(DATE_FROM_KEY));
            LocalDate dateTo = getAndValidateDate(params.get(DATE_TO_KEY));
            validateDateRange(dateFrom, dateTo);

            String destination = params.get(DESTINATION_KEY);
            validateDestination(destination);

            hotels = hotelRepository.getHotels(dateFrom, dateTo, destination);
        }
        else if(params.size() == 0)
            hotels = hotelRepository.getHotels();
        else throw new InvalidArgumentException("Wrong number of parameters");

        return hotels;
    }

    @Override
    public BookingResponseDTO generateBooking(BookingRequestDTO bookingRequestDTO) {
        validateEmail(bookingRequestDTO.getUserName());
        validateBooking(bookingRequestDTO.getBooking());

        HotelDTO hotel = hotelRepository.generateBooking(bookingRequestDTO.getBooking());

        if(hotel == null)
            throw new BookingNotFoundException("Hotel not available");

        Integer dues = bookingRequestDTO.getBooking().getPaymentMethod().getDues();
        Double amount = hotel.getPrice();
        Double interest = dues > 3 ? dues > 6 ? 15.0 : 10.0 : 5.0;
        Double total = amount + (amount * interest / 100);

        // Set to null so it doesn't appear in response
        bookingRequestDTO.getBooking().setPaymentMethod(null);

        BookingResponseDTO bookingResponseDTO = new BookingResponseDTO();
        bookingResponseDTO.setUserName(bookingRequestDTO.getUserName());
        bookingResponseDTO.setAmount(amount);
        bookingResponseDTO.setInterest(interest);
        bookingResponseDTO.setTotal(total);
        bookingResponseDTO.setBooking(bookingRequestDTO.getBooking());
        bookingResponseDTO.setStatusCode(getStatusCode());

        return bookingResponseDTO;
    }

    private void validateBooking(BookingDTO booking){
        LocalDate dateFrom = getAndValidateDate(booking.getDateFrom());
        LocalDate dateTo = getAndValidateDate(booking.getDateTo());

        validateDateRange(dateFrom, dateTo);
        validateRoomType(booking.getRoomType(), booking.getPeopleAmount());
        validateDestination(booking.getDestination());
        validatePaymentMethod(booking.getPaymentMethod());

        if(booking.getPeopleAmount() != booking.getPeople().size())
            throw new InvalidArgumentException("The people amount does not match the size of the people array");
        booking.getPeople().forEach(this::validatePeople);
    }

    private void validateDestination(String destination){
        if(!hotelRepository.getDestinations().contains(destination))
            throw new InvalidArgumentException("The chosen destination does not exist");
    }

    private void validateRoomType(String roomType, Integer peopleAmount){
        boolean valid = true;
        switch(roomType.toUpperCase()){
            case "SINGLE":
                valid = peopleAmount == 1;
                break;
            case "DOBLE":
                valid = peopleAmount > 0 && peopleAmount <= 2;
                break;
            case "TRIPLE":
                valid = peopleAmount > 0 && peopleAmount <= 3;
                break;
            case "MÚLTIPLE":
                valid = peopleAmount <= 10;
                break;
            default:
                throw new InvalidArgumentException("Invalid room type");
        }

        if(!valid)
            throw new InvalidArgumentException("The selected room type does not matches " +
                                               "the number of people they will stay in it");
    }
}
