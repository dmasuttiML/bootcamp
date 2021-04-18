package com.mercadolibre.desafio_quality.repositories;

import com.mercadolibre.desafio_quality.dtos.HotelDTO;
import com.mercadolibre.desafio_quality.exceptions.InternalServerErrorException;
import com.mercadolibre.desafio_quality.utils.HelperCSV;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class HotelRepositoryImpl implements HotelRepository {
    private String fileName;

    public HotelRepositoryImpl(@Value("${hotels_file}") String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<HotelDTO> getHotels() {
        return loadHotels();
    }

    @Override
    public List<HotelDTO> getHotels(LocalDate dateFrom, LocalDate dateTo, String destination) {
        return loadHotels().stream()
                           .filter(h -> dateFrom.isAfter(h.getDateFrom()) || dateFrom.isEqual(h.getDateFrom()))
                           .filter(h -> dateTo.isBefore(h.getDateTo()) || dateTo.isEqual(h.getDateTo()))
                           .filter(h -> h.getPlace().equals(destination))
                           .collect(Collectors.toList());
    }

    @Override
    public List<String> getDestinations() {
        return loadHotels().stream()
                           .map(h -> h.getPlace())
                           .distinct()
                           .collect(Collectors.toList());
    }

    private List<HotelDTO> loadHotels(){
        List<HotelDTO> hotels = new ArrayList<>();
        List<String[]> dataLines = HelperCSV.readCSV(fileName);

        try {
            // Elimino la cabecera.
            if(dataLines.size() > 0)
                dataLines.remove(0);

            for (String[] line: dataLines) {
                HotelDTO hotelDTO = new HotelDTO();

                hotelDTO.setHotelCode(line[0]);
                hotelDTO.setName(line[1]);
                hotelDTO.setPlace(line[2]);
                hotelDTO.setRoomType(line[3]);
                hotelDTO.setPrice(Double.parseDouble(line[4].replace("$","").replace(".","")));
                hotelDTO.setDateFrom(LocalDate.parse(line[5], DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                hotelDTO.setDateTo(LocalDate.parse(line[6], DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                hotelDTO.setReserved(line[7].equals("SI"));

                hotels.add(hotelDTO);
            }
        }
        catch (Exception e){
            throw new InternalServerErrorException("Database inconsistency", e);
        }

        return hotels;
    }
}
