package com.mercadolibre.desafio_spting.repositories;

import com.mercadolibre.desafio_spting.dtos.ArticleDTO;
import com.mercadolibre.desafio_spting.dtos.PurchaseDTO;
import com.mercadolibre.desafio_spting.dtos.TicketDTO;
import com.mercadolibre.desafio_spting.enums.ArticleFilterTypes;
import com.mercadolibre.desafio_spting.enums.ArticleOrderTypes;

import java.util.List;
import java.util.Map;

public interface ArticleRepository {
    List<ArticleDTO> getArticles(Map<ArticleFilterTypes, String> filters, ArticleOrderTypes order);
    TicketDTO generatePurchase(PurchaseDTO purchaseDTO);
}
