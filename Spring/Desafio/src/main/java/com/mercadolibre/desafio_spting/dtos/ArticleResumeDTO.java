package com.mercadolibre.desafio_spting.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleResumeDTO {
    private Integer productId;
    private String name;
    private String brand;
    private Integer quantity;
}
