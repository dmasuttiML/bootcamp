package com.mercadolibre.desafio_quality.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatusCodeDTO {
    private Integer code;
    private String message;
}
