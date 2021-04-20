package com.mercadolibre.desafio_quality.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReservationResponseDTO {
    private String userName;
    private Double amount;
    private Double interest;
    private Double total;
    private ReservationDTO flightReservation;
    private StatusCodeDTO statusCode;
}
