package com.mercadolibre.desafio_quality.services;

import com.mercadolibre.desafio_quality.dtos.BookingDTO;
import com.mercadolibre.desafio_quality.dtos.BookingRequestDTO;
import com.mercadolibre.desafio_quality.dtos.BookingResponseDTO;
import com.mercadolibre.desafio_quality.dtos.HotelDTO;
import com.mercadolibre.desafio_quality.exceptions.InvalidArgumentException;
import com.mercadolibre.desafio_quality.repositories.HotelRepository;
import com.mercadolibre.desafio_quality.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

@Service
public class HotelServiceImpl implements HotelService {

    private static final String DATE_FROM_KEY = "dateFrom";
    private static final String DATE_TO_KEY = "dateTo";
    private static final String DESTINATION_KEY = "destination";

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public List<HotelDTO> getHotels(Map<String, String> params) {
        List<HotelDTO> hotels = null;

        if (params.size() == 3) {
            for (String param : params.keySet()) {
                if(!param.equals(DATE_FROM_KEY) && !param.equals(DATE_TO_KEY) && !param.equals(DESTINATION_KEY))
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
        BookingDTO bookingDTO = bookingRequestDTO.getBooking();
        validateDateRange(bookingDTO.getDateFrom(), bookingDTO.getDateTo());
        return null;
    }

    private LocalDate getAndValidateDate(String strDate) {
        try {
            return LocalDate.parse(strDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException e) {
            throw new InvalidArgumentException("Date format must be dd/mm/yyyy: " + strDate);
        }
    }

    private void validateDestination(String destination){
        if(!hotelRepository.getDestinations().contains(destination))
            throw new InvalidArgumentException("The chosen destination does not exist");
    }

    private void validateDateRange(LocalDate dateFrom, LocalDate dateTo){
        if(dateFrom.isAfter(dateTo))
            throw new InvalidArgumentException("The check-in date must be less than the check-out date");
    }
}
