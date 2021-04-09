package com.mercadolibre.desafio_spting.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDTO {
    private StatusCodeDTO statusCode;
}
