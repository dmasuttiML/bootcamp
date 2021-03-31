package com.example.starwars.services;

import com.example.starwars.dtos.PersonajeDTO;
import com.example.starwars.repositories.PersonajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonajeServiceImpl implements PersonajeService {
    @Autowired
    private PersonajeRepository personajeRepository;

    @Override
    public List<PersonajeDTO> obtenerPersonajes(String nombre){
        return personajeRepository.buscarPersonajesPorNombre(nombre);
    }
}
