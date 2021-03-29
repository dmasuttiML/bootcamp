package com.example.diploma.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DiplomaDTO {
    private String mensaje;
    private float promedio;
    private String alumno;
}
