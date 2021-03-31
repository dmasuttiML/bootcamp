package com.example.starwars.repositories;

import com.example.starwars.dtos.PersonajeDTO;

import java.util.List;

public interface PersonajeRepository {
    List<PersonajeDTO> buscarPersonajesPorNombre(String nombre);
}
