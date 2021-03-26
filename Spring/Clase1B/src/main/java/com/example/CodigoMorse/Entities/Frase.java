package com.example.CodigoMorse.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Frase {
    private String morse;
    private String ascii;
}
