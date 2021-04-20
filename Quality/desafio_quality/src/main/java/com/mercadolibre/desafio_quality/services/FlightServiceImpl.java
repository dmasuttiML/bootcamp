package com.mercadolibre.desafio_quality.services;

import com.mercadolibre.desafio_quality.dtos.*;
import com.mercadolibre.desafio_quality.exceptions.InvalidArgumentException;
import com.mercadolibre.desafio_quality.exceptions.ReservationNotFoundException;
import com.mercadolibre.desafio_quality.repositories.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class FlightServiceImpl extends BookableService implements FlightService {

    private static final String DATE_FROM_KEY = "dateFrom";
    private static final String DATE_TO_KEY = "dateTo";
    private static final String ORIGIN_KEY = "origin";
    private static final String DESTINATION_KEY = "destination";
    private static final List<String> keys = Arrays.asList(DATE_FROM_KEY, DATE_TO_KEY, ORIGIN_KEY, DESTINATION_KEY);

    @Autowired
    FlightRepository flightRepository;

    @Override
    public List<FlightDTO> getFlights(Map<String, String> params) {
        List<FlightDTO> flights = null;

        if (params.size() == 4) {
            for (String param : params.keySet()) {
                if(!keys.contains(param))
                    throw new InvalidArgumentException("Unknown parameter: " + param);
            }

            LocalDate dateFrom = getAndValidateDate(params.get(DATE_FROM_KEY));
            LocalDate dateTo = getAndValidateDate(params.get(DATE_TO_KEY));
            validateDateRange(dateFrom, dateTo);

            String origin = params.get(ORIGIN_KEY);
            validateOrigin(origin);

            String destination = params.get(DESTINATION_KEY);
            validateDestination(destination);

            flights = flightRepository.getFlights(dateFrom, dateTo, origin, destination);
        }
        else if(params.size() == 0)
            flights = flightRepository.getFlights();
        else throw new InvalidArgumentException("Wrong number of parameters");

        return flights;
    }

    @Override
    public ReservationResponseDTO generateReservation(ReservationRequestDTO reservationRequestDTO) {
        validateEmail(reservationRequestDTO.getUserName());
        validateReservation(reservationRequestDTO.getFlightReservation());

        FlightDTO flight = flightRepository.generateReservation(reservationRequestDTO.getFlightReservation());

        if(flight == null)
            throw new ReservationNotFoundException("Flight not available");

        Integer dues = reservationRequestDTO.getFlightReservation().getPaymentMethod().getDues();
        Double amount = flight.getPrice();
        Double interest = dues > 3 ? dues > 6 ? 15.0 : 10.0 : 5.0;
        Double total = amount + (amount * interest / 100);

        // Set to null so it doesn't appear in response
        reservationRequestDTO.getFlightReservation().setPaymentMethod(null);

        ReservationResponseDTO reservationResponseDTO = new ReservationResponseDTO();
        reservationResponseDTO.setUserName(reservationRequestDTO.getUserName());
        reservationResponseDTO.setAmount(amount);
        reservationResponseDTO.setInterest(interest);
        reservationResponseDTO.setTotal(total);
        reservationResponseDTO.setFlightReservation(reservationRequestDTO.getFlightReservation());
        reservationResponseDTO.setStatusCode(getStatusCode());

        return reservationResponseDTO;
    }

    private void validateReservation(ReservationDTO reservationDTO) {
        LocalDate dateFrom = getAndValidateDate(reservationDTO.getDateFrom());
        LocalDate dateTo = getAndValidateDate(reservationDTO.getDateTo());
        validateDateRange(dateFrom, dateTo);

        if(reservationDTO.getSeats() != reservationDTO.getPeople().size())
            throw new InvalidArgumentException("The sets does not match the size of the people array");
        reservationDTO.getPeople().forEach(this::validatePeople);

        validateOrigin(reservationDTO.getOrigin());
        validateDestination(reservationDTO.getDestination());
        validatePaymentMethod(reservationDTO.getPaymentMethod());
    }

    private void validateOrigin(String origin){
        if(!flightRepository.getOrigins().contains(origin))
            throw new InvalidArgumentException("The chosen origin does not exist");
    }

    private void validateDestination(String destination){
        if(!flightRepository.getDestinations().contains(destination))
            throw new InvalidArgumentException("The chosen destination does not exist");
    }
}
