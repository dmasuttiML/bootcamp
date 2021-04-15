package com.mercadolibre.desafio_quality.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDTO {
    private String description;
    private Integer statusCode;
}
