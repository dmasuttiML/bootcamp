package com.mercadolibre.desafio_quality.repositories;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class FlightRepositoryImpl implements FlightRepository {
    private String path;

    public FlightRepositoryImpl(@Value("${flight_file}") String path){
        this.path = path;
    }

}
