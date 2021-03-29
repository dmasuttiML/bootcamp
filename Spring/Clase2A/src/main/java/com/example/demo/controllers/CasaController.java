package com.example.demo.controllers;

import com.example.demo.services.CasaService;
import com.example.demo.dtos.CasaDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/casa")
public class CasaController {

    CasaService casaService = new CasaService();
    @PostMapping("/calcularMetroCuadrado")
    public ResponseEntity calcularMetroCuadrado(@RequestBody CasaDTO casa){
        casa.setMetrosCuadrados(casaService.retornarMetros(casa));
        return new ResponseEntity(casa, HttpStatus.OK);
    }

    @PostMapping("/getValor")
    public ResponseEntity getValor(@RequestBody CasaDTO casa){
        casa.setPrecio(casaService.getValorCasa(casa));
        return new ResponseEntity(casa, HttpStatus.OK);
    }

    @PostMapping("/habitacionMayor")
    public ResponseEntity habitacionMayor(@RequestBody CasaDTO casa){
        return new ResponseEntity(casaService.habitacionGrande(casa), HttpStatus.OK);
    }

    //@RequestBody
}
