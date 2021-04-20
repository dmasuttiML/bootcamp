package com.mercadolibre.desafio_quality.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReservationRequestDTO {
    private String userName;
    private ReservationDTO flightReservation;
}
