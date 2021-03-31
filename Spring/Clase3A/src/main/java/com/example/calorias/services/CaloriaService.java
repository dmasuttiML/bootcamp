package com.example.calorias.services;

import com.example.calorias.dtos.PlatoDTO;
import com.example.calorias.exceptionsHandler.IngredientNotFound;

public interface CaloriaService {
    PlatoDTO calculate(PlatoDTO platoDTO) throws IngredientNotFound;
}
