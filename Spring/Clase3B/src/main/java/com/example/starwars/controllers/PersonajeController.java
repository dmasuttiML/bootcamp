package com.example.starwars.controllers;

import com.example.starwars.dtos.PersonajeDTO;
import com.example.starwars.services.PersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/starwars")
public class PersonajeController {

    @Autowired
    private PersonajeService personajeService;

    @GetMapping("/obtenerpersonajes")
    public List<PersonajeDTO> obtenerPersonajes(@RequestParam(defaultValue = "") String nombre){
        return personajeService.obtenerPersonajes(nombre);
    }
}