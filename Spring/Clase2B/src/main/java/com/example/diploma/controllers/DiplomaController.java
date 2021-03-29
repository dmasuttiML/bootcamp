package com.example.diploma.controllers;

import com.example.diploma.dtos.AlumnoDTO;
import com.example.diploma.services.DiplomaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiplomaController {

    @PostMapping("/obtenerDiploma")
    public ResponseEntity obtenerDiploma(@RequestBody AlumnoDTO alumno){
        return new ResponseEntity(DiplomaService.generarDiploma(alumno), HttpStatus.OK);
    }
}
