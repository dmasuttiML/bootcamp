package com.mercadolibre.desafio_spting.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterClientResponseDTO {
    private ClientDTO client;
    private StatusCodeDTO statusCode;
}
