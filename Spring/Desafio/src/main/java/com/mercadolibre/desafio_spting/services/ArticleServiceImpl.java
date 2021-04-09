package com.mercadolibre.desafio_spting.services;

import com.mercadolibre.desafio_spting.dtos.*;
import com.mercadolibre.desafio_spting.exceptions.ApiException;
import com.mercadolibre.desafio_spting.exceptions.InvalidArgumentException;
import com.mercadolibre.desafio_spting.repositories.ArticleRepository;
import com.mercadolibre.desafio_spting.enums.ArticleFilterTypes;
import com.mercadolibre.desafio_spting.enums.ArticleOrderTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    /* getArticles
        Metodo que a partir de un mapa de parametros, obtiene una lista de articulos.
        En la misma se validan los parametros recibidos y se los pasa a la función getArticles
        de ArticleRepository, para que le devuelva la lista de articulos filtrada y ordenada.
     */
    @Override
    public List<ArticleDTO> getArticles(Map<String, String> params) {
        // Validation of order parameter
        ArticleOrderTypes order = null;
        if(params.containsKey("order")){
            order = ArticleOrderTypes.valueOfId(params.get("order").trim());
            if(order == null)
                throw new InvalidArgumentException("Metodo de ordenamiento de artículos invalido: " + params.get("order"));
            params.remove("order");
        }

        // Valdation of filters parameters
        Map<ArticleFilterTypes, String> filters = new HashMap<>();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            ArticleFilterTypes filter = ArticleFilterTypes.valueOfKey(entry.getKey().trim());
            if(filter == null)
                throw new InvalidArgumentException("Filtro de artículos invalido: " + entry.getKey());
            filters.put(filter, entry.getValue());
        }

        if(filters.size() > 2)
            throw new InvalidArgumentException("Cantidad de filtros de artículos superada");

        return articleRepository.getArticles(filters, order);
    }

    /* generatePurchase
        Metodo que a partir de un objeto PurchaseDTO, genera la compra y retorna un objeto PurchaseResponseDTO.
        En la misma se validan que la petición contenga articulos y se los pasa a la función generatePurchase
        de ArticleRepository, para que le devuelva el TicketDTO de la compra.
     */
    @Override
    public PurchaseResponseDTO generatePurchase(PurchaseDTO purchaseDTO) {
        if(purchaseDTO.getArticles().size() == 0)
            throw new InvalidArgumentException("No hay artículos en la compra");

        TicketDTO ticketDTO = articleRepository.generatePurchase(purchaseDTO);
        StatusCodeDTO statusCodeDTO = new StatusCodeDTO(HttpStatus.OK.value(), "La solicitud de compra se completó con éxito");

        return new PurchaseResponseDTO(ticketDTO, statusCodeDTO);
    }
}
