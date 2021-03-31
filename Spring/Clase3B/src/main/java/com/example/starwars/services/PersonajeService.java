package com.example.starwars.services;

import com.example.starwars.dtos.PersonajeDTO;

import java.util.List;

public interface PersonajeService {
    List<PersonajeDTO> obtenerPersonajes(String nombre);
}
