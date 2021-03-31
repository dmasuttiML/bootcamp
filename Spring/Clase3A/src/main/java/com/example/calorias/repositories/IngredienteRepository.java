package com.example.calorias.repositories;

import com.example.calorias.dtos.IngredienteDTO;

import java.util.List;

public interface IngredienteRepository {
    IngredienteDTO buscarIngredientePorNombre(String nombre);
}
