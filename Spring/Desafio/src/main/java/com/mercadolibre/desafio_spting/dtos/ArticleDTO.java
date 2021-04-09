package com.mercadolibre.desafio_spting.dtos;

import jdk.jfr.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO {
    private Integer productId;
    private String name;
    private String category;
    private String brand;
    private Double price;
    private Integer quantity;
    private String freeShipping;
    private String prestige;
}
