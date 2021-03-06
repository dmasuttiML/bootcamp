package com.mercadolibre.desafio_quality.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentMethodDTO {
    private String type;
    private String number;
    private Integer dues;
}
