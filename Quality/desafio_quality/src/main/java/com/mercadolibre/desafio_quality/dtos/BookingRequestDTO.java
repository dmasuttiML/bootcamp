package com.mercadolibre.desafio_quality.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookingRequestDTO {
    private String userName;
    private BookingDTO booking;
}
