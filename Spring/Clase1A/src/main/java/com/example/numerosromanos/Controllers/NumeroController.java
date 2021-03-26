package com.example.numerosromanos.Controllers;

import com.example.numerosromanos.Entities.Numero;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumeroController {

    @GetMapping("/convertiraromano/{numero}")
    public String convertirARomano(@PathVariable Integer numero){
        String resp = "";

        if(numero > 0 && numero < 4000) {
            Numero n = new Numero(numero);
            resp = n.convertirARomano();
        }
        else {
            resp = "Numero fuera de rango";
        }

        return resp;
    }
}
