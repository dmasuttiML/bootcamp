package com.example.calorias.controllers;

import com.example.calorias.dtos.PlatoDTO;
import com.example.calorias.services.CaloriaService;
import com.example.calorias.services.CaloriaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calorias")
public class CaloriaController {

    @Autowired
    private CaloriaServiceImpl caloriaService;

    @PostMapping("/calculate")
    public PlatoDTO calculate(@RequestBody PlatoDTO plato){
        return caloriaService.calculate(plato);
    }

    @PostMapping("/calculateall")
    public PlatoDTO[] calculateAll(@RequestBody PlatoDTO[] platos){
        for (PlatoDTO plato: platos) {
            plato = caloriaService.calculate(plato);
        }
        return platos;
    }
}