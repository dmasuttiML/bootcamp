package com.mercadolibre.desafio_quality.repositories;

import com.mercadolibre.desafio_quality.dtos.BookingDTO;
import com.mercadolibre.desafio_quality.dtos.HotelDTO;
import com.mercadolibre.desafio_quality.exceptions.BookingNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/*
* Repository for interaction with hotels.
* */
@Repository
public class HotelRepositoryImpl extends CSVRepository<HotelDTO> implements HotelRepository {

    public HotelRepositoryImpl(@Value("${hotels_file}") String fileName) {
        super(fileName);
    }

    /*
    * Function that converts a String[] into a HotelDTO
    * */
    @Override
    protected HotelDTO parseLine(String[] line) {
        HotelDTO hotelDTO = new HotelDTO();

        hotelDTO.setHotelCode(line[0]);
        hotelDTO.setName(line[1]);
        hotelDTO.setPlace(line[2]);
        hotelDTO.setRoomType(line[3]);
        hotelDTO.setPrice(Double.parseDouble(line[4].replace("$","").replace(".","")));
        hotelDTO.setDateFrom(LocalDate.parse(line[5], DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        hotelDTO.setDateTo(LocalDate.parse(line[6], DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        hotelDTO.setReserved(line[7].equals("SI"));

        return hotelDTO;
    }

    /*
     * Function that converts a HotelDTO into a String[]
     * */
    @Override
    protected String[] makeLine(HotelDTO hotelDTO) {
        String[] line = new String[8];

        line[0] = hotelDTO.getHotelCode();
        line[1] = hotelDTO.getName();
        line[2] = hotelDTO.getPlace();
        line[3] = hotelDTO.getRoomType();
        line[4] = String.format("$%.0f", hotelDTO.getPrice());
        line[5] = hotelDTO.getDateFrom().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        line[6] = hotelDTO.getDateTo().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        line[7] = hotelDTO.getReserved() ? "SI" : "NO";

        return line;
    }

    /*
    * Returns all available hotels.
    * */
    @Override
    public List<HotelDTO> getHotels() {
        return loadData().stream()
                         .filter(h -> !h.getReserved())
                         .collect(Collectors.toList());
    }

    /*
     * Returns available hotels filtered by dateFrom, dateTo and destination.
     * */
    @Override
    public List<HotelDTO> getHotels(LocalDate dateFrom, LocalDate dateTo, String destination) {
        return loadData().stream()
                         .filter(h -> h.getPlace().equals(destination) && !h.getReserved())
                         .filter(h -> dateFrom.isBefore(h.getDateFrom()) || dateFrom.isEqual(h.getDateFrom()))
                         .filter(h -> dateTo.isAfter(h.getDateTo()) || dateTo.isEqual(h.getDateTo()))
                         .collect(Collectors.toList());
    }

    /*
    * Returns all available destinations.
    * */
    @Override
    public List<String> getDestinations() {
        return loadData().stream()
                         .map(HotelDTO::getPlace)
                         .distinct()
                         .collect(Collectors.toList());
    }

    /*
     * Generates a hotel reservation and returns the reserved hotel.
     * In case of not being available the hotel returns null.
     * */
    @Override
    public HotelDTO generateBooking(BookingDTO bookingDTO) {
        List<HotelDTO> hotels = loadData();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateFrom = LocalDate.parse(bookingDTO.getDateFrom(), formatter);
        LocalDate dateTo = LocalDate.parse(bookingDTO.getDateTo(), formatter);

        HotelDTO hotel = hotels.stream()
                               .filter(h -> h.getHotelCode().equals(bookingDTO.getHotelCode()) &&
                                            h.getRoomType().equalsIgnoreCase(bookingDTO.getRoomType()) &&
                                            !h.getReserved())
                               .filter(h -> dateFrom.isAfter(h.getDateFrom()) || dateFrom.isEqual(h.getDateFrom()))
                               .filter(h -> dateTo.isBefore(h.getDateTo()) || dateTo.isEqual(h.getDateTo()))
                               .findFirst().orElse(null);

        if(hotel != null) {
            if(!hotel.getPlace().equals(bookingDTO.getDestination()))
                throw new BookingNotFoundException("The destination does not match the hotel location");
            hotel.setReserved(true);
            saveData(hotels);
        }

        return hotel;
    }
}
