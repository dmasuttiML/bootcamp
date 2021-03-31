package com.example.calorias.services;

import com.example.calorias.dtos.IngredienteDTO;
import com.example.calorias.dtos.PlatoDTO;
import com.example.calorias.repositories.IngredienteRepository;
import org.springframework.stereotype.Service;

@Service
public class CaloriaServiceImpl implements CaloriaService {
    private IngredienteRepository ingredienteRepository;

    private CaloriaServiceImpl(IngredienteRepository ingredienteRepository){
        this.ingredienteRepository = ingredienteRepository;
    }

    @Override
    public PlatoDTO calculate(PlatoDTO platoDTO){
        float caloriasTotales = 0;

        for (IngredienteDTO i: platoDTO.getIngredientes()) {
            IngredienteDTO ingrediente = ingredienteRepository.buscarIngredientePorNombre(i.getName());
            if(ingrediente != null){
                // Calorias por ingrediente.
                i.setCalories(ingrediente.getCalories());
                // Calorias totales.
                caloriasTotales += i.getCalories() * i.getWeight();
                // Ingrediente mas calorico.
                if(platoDTO.getMasCalorico() == null || i.getCalories() > platoDTO.getMasCalorico().getCalories()){
                    platoDTO.setMasCalorico(i);
                }
            }
        }

        platoDTO.setCaloriasTotales(caloriasTotales);

        return platoDTO;
    }
}
