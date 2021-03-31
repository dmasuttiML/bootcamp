package com.example.calorias.repositories;

import com.example.calorias.dtos.IngredienteDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.List;

@Repository
public class IngredienteRepositoryImpl implements IngredienteRepository {
    private List<IngredienteDTO> dataBase;

    public IngredienteRepositoryImpl() {
        dataBase = loadDataBase();
    }

    private List<IngredienteDTO> loadDataBase() {
        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:food.json");
        }
        catch (Exception e){
            e.printStackTrace();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<IngredienteDTO>> typeRef = new TypeReference<List<IngredienteDTO>>() {};
        List<IngredienteDTO> ingredienteDTOS = null;

        try {
            ingredienteDTOS = objectMapper.readValue(file, typeRef);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return ingredienteDTOS;

    }

    @Override
    public IngredienteDTO buscarIngredientePorNombre(String nombre) {
        return dataBase.stream()
                .filter(i -> i.getName().equals(nombre))
                .findFirst()
                .orElse(null);
    }
}
