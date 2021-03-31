package com.example.calorias.dtos;

import lombok.Data;
import java.util.List;

@Data
public class PlatoDTO {
    private String nombre;
    private List<IngredienteDTO> ingredientes;
    private float caloriasTotales;
    private IngredienteDTO masCalorico;
}
