package com.mercadolibre.desafio_quality.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationDTO {
    private String dateFrom;
    private String dateTo;
    private String origin;
    private String destination;
    private String flightNumber;
    private Integer seats;
    private String seatType;
    private List<PeopleDTO> people;
    private PaymentMethodDTO paymentMethod;
}
