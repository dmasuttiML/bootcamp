package com.example.CodigoMorse.Controllers;

import com.example.CodigoMorse.Entities.Frase;
import com.example.CodigoMorse.Services.CodigoMorseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CodigoMorseController {
    @Autowired
    private CodigoMorseService codigoMorseService;

    public CodigoMorseController(CodigoMorseService codigoMorseService) {
        this.codigoMorseService = codigoMorseService;
    }

    @GetMapping("/decodificar")
    public Frase decodificar(@RequestParam(defaultValue = "") String codigo)
    {
        return new Frase(codigo, codigoMorseService.decodificar(codigo));
    }
}
