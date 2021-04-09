package com.mercadolibre.desafio_spting.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TicketDTO {
    private Integer id;
    private List<ArticleResumeDTO> articles;
    private Double total;
}
