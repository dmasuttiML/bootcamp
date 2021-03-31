package com.example.calorias.controllers;

import com.example.calorias.dtos.ErrorDTO;
import com.example.calorias.dtos.PlatoDTO;
import com.example.calorias.exceptionsHandler.IngredientNotFound;
import com.example.calorias.services.CaloriaService;
import com.example.calorias.services.CaloriaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calorias")
public class CaloriaController {

    @Autowired
    private CaloriaServiceImpl caloriaService;

    @PostMapping("/calculate")
    public ResponseEntity<PlatoDTO> calculate(@RequestBody PlatoDTO plato) throws IngredientNotFound {
        return ResponseEntity.ok(caloriaService.calculate(plato));
    }

    @PostMapping("/calculateall")
    public ResponseEntity<PlatoDTO[]> calculateAll(@RequestBody PlatoDTO[] platos) throws IngredientNotFound {
        for (PlatoDTO plato: platos) {
            plato = caloriaService.calculate(plato);
        }

        return ResponseEntity.ok(platos);
    }

    @ExceptionHandler(IngredientNotFound.class )
    public ResponseEntity<ErrorDTO> handleException(IngredientNotFound e) {
        ErrorDTO errorDTO = new ErrorDTO("Ingrediente invalido", e.getMessage());
        return ResponseEntity.badRequest().body(errorDTO);
    }
}