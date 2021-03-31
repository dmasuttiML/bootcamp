package com.example.starwars.repositories;

import com.example.starwars.dtos.PersonajeDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PersonajeRepositoryImpl implements PersonajeRepository {
    @Override
    public List<PersonajeDTO> buscarPersonajesPorNombre(String nombre) {
        List<PersonajeDTO> dataBase = loadDataBase();
        return dataBase.stream()
                       .filter(p -> p.getName().indexOf(nombre) != -1)
                       .collect(Collectors.toList());
    }

    private List<PersonajeDTO> loadDataBase() {
        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:starwars.json");
        }
        catch (Exception e){
            e.printStackTrace();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<PersonajeDTO>> typeRef = new TypeReference<List<PersonajeDTO>>() {};
        List<PersonajeDTO> ingredienteDTOS = null;

        try {
            ingredienteDTOS = objectMapper.readValue(file, typeRef);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return ingredienteDTOS;

    }
}
