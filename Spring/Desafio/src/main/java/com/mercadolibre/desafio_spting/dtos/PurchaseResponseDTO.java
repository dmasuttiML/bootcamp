package com.mercadolibre.desafio_spting.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PurchaseResponseDTO {
    private TicketDTO ticket;
    private StatusCodeDTO statusCode;
}
