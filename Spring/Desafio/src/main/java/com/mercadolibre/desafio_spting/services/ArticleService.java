package com.mercadolibre.desafio_spting.services;

import com.mercadolibre.desafio_spting.dtos.ArticleDTO;
import com.mercadolibre.desafio_spting.dtos.PurchaseDTO;
import com.mercadolibre.desafio_spting.dtos.PurchaseResponseDTO;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    List<ArticleDTO> getArticles(Map<String, String> params);
    PurchaseResponseDTO generatePurchase(PurchaseDTO purchaseDTO);
}
