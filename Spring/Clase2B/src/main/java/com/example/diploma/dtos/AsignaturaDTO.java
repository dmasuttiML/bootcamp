package com.example.diploma.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AsignaturaDTO {
    private String nombre;
    private int nota;
}
