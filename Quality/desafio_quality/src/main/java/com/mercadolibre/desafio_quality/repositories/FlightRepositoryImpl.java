package com.mercadolibre.desafio_quality.repositories;

import com.mercadolibre.desafio_quality.dtos.FlightDTO;
import com.mercadolibre.desafio_quality.dtos.HotelDTO;
import com.mercadolibre.desafio_quality.dtos.ReservationDTO;
import com.mercadolibre.desafio_quality.exceptions.BookingNotFoundException;
import com.mercadolibre.desafio_quality.exceptions.ReservationNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class FlightRepositoryImpl extends CSVRepository<FlightDTO> implements FlightRepository {

    public FlightRepositoryImpl(@Value("${flight_file}") String fileName){
        super(fileName);
    }

    /*
     * Function that converts a String[] into a FlightDTO
     * */
    @Override
    protected FlightDTO parseLine(String[] line) {
        FlightDTO flightDTO = new FlightDTO();

        flightDTO.setFlightNumber(line[0]);
        flightDTO.setOrigin(line[1]);
        flightDTO.setDestination(line[2]);
        flightDTO.setSeatType(line[3]);
        flightDTO.setPrice(Double.parseDouble(line[4].replace("$","").replace(".","")));
        flightDTO.setDateFrom(LocalDate.parse(line[5], DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        flightDTO.setDateTo(LocalDate.parse(line[6], DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        return flightDTO;
    }

    /*
     * Function that converts a FlightDTO into a String[]
     * */
    @Override
    protected String[] makeLine(FlightDTO flightDTO) {
        String[] line = new String[7];

        line[0] = flightDTO.getFlightNumber();
        line[1] = flightDTO.getOrigin();
        line[2] = flightDTO.getDestination();
        line[3] = flightDTO.getSeatType();
        line[4] = String.format("$%.0f", flightDTO.getPrice());
        line[5] = flightDTO.getDateFrom().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        line[6] = flightDTO.getDateTo().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        return line;
    }

    /*
     * Returns all available flights.
     * */
    @Override
    public List<FlightDTO> getFlights() {
        return loadData();
    }

    /*
     * Returns available flights filtered by dateFrom, dateTo, origin and destination.
     * */
    @Override
    public List<FlightDTO> getFlights(LocalDate dateFrom, LocalDate dateTo, String origin, String destination) {
        return loadData().stream()
                         .filter(f -> f.getOrigin().equals(origin) && f.getDestination().equals(destination))
                         .filter(f -> dateFrom.isBefore(f.getDateFrom()) || dateFrom.isEqual(f.getDateFrom()))
                         .filter(f -> dateTo.isAfter(f.getDateTo()) || dateTo.isEqual(f.getDateTo()))
                         .collect(Collectors.toList());
    }

    /*
     * Returns all available origins.
     * */
    @Override
    public List<String> getOrigins() {
        return loadData().stream()
                         .map(FlightDTO::getOrigin)
                         .distinct()
                         .collect(Collectors.toList());
    }

    /*
     * Returns all available destinations.
     * */
    @Override
    public List<String> getDestinations() {
        return loadData().stream()
                         .map(FlightDTO::getDestination)
                         .distinct()
                         .collect(Collectors.toList());
    }

    /*
     * Generates a flight reservation and returns the reserved flight.
     * In case of not being available the hotel returns null.
     * */
    @Override
    public FlightDTO generateReservation(ReservationDTO reservationDTO) {
        List<FlightDTO> flights = loadData();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateFrom = LocalDate.parse(reservationDTO.getDateFrom(), formatter);
        LocalDate dateTo = LocalDate.parse(reservationDTO.getDateTo(), formatter);

        FlightDTO flight = flights.stream()
                                  .filter(f -> f.getFlightNumber().equals(reservationDTO.getFlightNumber()) &&
                                               f.getSeatType().equalsIgnoreCase(reservationDTO.getSeatType()))
                                  .filter(h -> dateFrom.isAfter(h.getDateFrom()) || dateFrom.isEqual(h.getDateFrom()))
                                  .filter(h -> dateTo.isBefore(h.getDateTo()) || dateTo.isEqual(h.getDateTo()))
                                  .findFirst().orElse(null);

        if(flight != null) {
            if(!flight.getOrigin().equals(reservationDTO.getOrigin()))
                throw new ReservationNotFoundException("The origin does not match the flight origin");
            if(!flight.getDestination().equals(reservationDTO.getDestination()))
                throw new ReservationNotFoundException("The destination does not match the flight destination");
            saveData(flights);
        }

        return flight;
    }
}
