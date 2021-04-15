package com.mercadolibre.desafio_spting.repositories;

import com.mercadolibre.desafio_spting.dtos.*;
import com.mercadolibre.desafio_spting.exceptions.ApiException;
import com.mercadolibre.desafio_spting.enums.ArticleFilterTypes;
import com.mercadolibre.desafio_spting.exceptions.ArticleNotFoundException;
import com.mercadolibre.desafio_spting.exceptions.InternalServerErrorException;
import com.mercadolibre.desafio_spting.exceptions.InvalidArgumentException;
import com.mercadolibre.desafio_spting.utils.HelperCSV;
import com.mercadolibre.desafio_spting.enums.ArticleOrderTypes;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository {
    private final AtomicInteger indexTicket = new AtomicInteger(1);
    private final String[] headArticleFile = new String[] {"productId", "name", "category", "brand", "price", "quantity", "freeShipping", "prestige"};
    private static final String nameArticleFile = "dbProductos.csv";

    /* getArticles
        Obtiene, filtra y ordena los articulos mediante un mapa de filtros y un metodo de ordenamiento.
        Si el parametro order es null, no los ordena.
    */
    @Override
    public List<ArticleDTO> getArticles(Map<ArticleFilterTypes,String> filters, ArticleOrderTypes order) {
        List<ArticleDTO> articles = loadArticles();

        articles = filterArticles(articles, filters);

        if(order != null)
            articles = orderArticles(articles, order);

        return articles;
    }

    /* generatePurchase
        Genera y persiste una compra mediante un PurchaseDTO.
        Retorna un TicketDTO con los detalles de la compra.
    */
    @Override
    public TicketDTO generatePurchase(PurchaseDTO purchaseDTO) {
        double total = 0;

        List<ArticleDTO> articles = loadArticles();

        for (ArticleResumeDTO a :purchaseDTO.getArticles()) {
            ArticleDTO article = articles.stream()
                                         .filter(b -> b.getProductId().equals(a.getProductId()))
                                         .findFirst()
                                         .orElse(null);

            if(article == null)
                throw new ArticleNotFoundException(String.format("Artículo no encontrado (productId: %d)", a.getProductId()));
            if(!article.getName().equals(a.getName()) || !article.getBrand().equals(a.getBrand()))
                throw new ArticleNotFoundException(String.format("Información del artículo incorrecta (productId: %d)"));
            if(a.getQuantity() <= 0)
                throw new ArticleNotFoundException(String.format("Cantidad de artículos invalida (productId: %d)", a.getProductId()));
            if(article.getQuantity() < a.getQuantity())
                throw new ArticleNotFoundException(String.format("Artículo sin stock suficiente (productId: %d)", a.getProductId()));

            total += article.getPrice();
            article.setQuantity(article.getQuantity() - a.getQuantity());
        }

        TicketDTO ticketDTO = new TicketDTO(indexTicket.getAndIncrement(), purchaseDTO.getArticles(), total);

        saveArticles(articles);

        return ticketDTO;
    }

    private List<ArticleDTO> filterArticles(List<ArticleDTO> articles, Map<ArticleFilterTypes, String> filters){
        Stream<ArticleDTO> stream = articles.stream();

        for (Map.Entry<ArticleFilterTypes,String> entry : filters.entrySet()) {
            switch (entry.getKey()){
                case NAME:
                    stream = stream.filter(a -> a.getName().equals(entry.getValue().trim()));
                    break;
                case CATEGORY:
                    stream = stream.filter(a -> a.getCategory().equals(entry.getValue().trim()));
                    break;
                case BRAND:
                    stream = stream.filter(a -> a.getBrand().equals(entry.getValue().trim()));
                    break;
                case PRICE:
                    double priceValue;
                    try { priceValue = Double.parseDouble(entry.getValue().trim()); }
                    catch (Exception e) { throw new InvalidArgumentException("Valor de price invalido: " + entry.getValue()); }
                    stream = stream.filter(a -> a.getPrice().equals(priceValue));
                    break;
                case FREE_SHIPPING:
                    String strFreeShipping = entry.getValue().trim();
                    if(!strFreeShipping.equals("false") && !strFreeShipping.equals("true"))
                        throw new InvalidArgumentException("Valor de freeShipping invalido: " + strFreeShipping);
                    stream = stream.filter(a -> a.getFreeShipping().equals("SI") == strFreeShipping.equals("true"));
                    break;
                case PRESTIGE:
                    int prestige;
                    try { prestige = Integer.parseInt(entry.getValue().trim()); }
                    catch (Exception e) { throw new InvalidArgumentException("Valor de price invalido: " + entry.getValue()); }
                    stream = stream.filter(a -> a.getPrestige().length() == prestige);
                    break;
            }
        }

        return stream.collect(Collectors.toList());
    }

    private List<ArticleDTO> orderArticles(List<ArticleDTO> articles, ArticleOrderTypes order){
        Stream<ArticleDTO> stream = articles.stream();

        switch (order) {
            case ALF_ASC:
                stream = stream.sorted(Comparator.comparing(ArticleDTO::getName));
                break;
            case ALF_DESC:
                stream = stream.sorted((a, b) -> b.getName().compareTo(a.getName()));
                break;
            case MAX_TO_MIN:
                stream = stream.sorted((a, b) -> b.getPrice().compareTo(a.getPrice()));
                break;
            case MIN_TO_MAX:
                stream = stream.sorted(Comparator.comparing(ArticleDTO::getPrice));
                break;
        }

        return stream.collect(Collectors.toList());
    }

    private List<ArticleDTO> loadArticles() {
        List<ArticleDTO> articles = new ArrayList<>();
        List<String[]> dataLines = HelperCSV.readCSV(nameArticleFile);

        try {
            // Elimino la cabecera.
            if(dataLines.size() > 0)
                dataLines.remove(0);

            for (String[] line: dataLines) {
                ArticleDTO articleDTO = new ArticleDTO();
                articleDTO.setProductId(Integer.parseInt(line[0]));
                articleDTO.setName(line[1]);
                articleDTO.setCategory(line[2]);
                articleDTO.setBrand(line[3]);
                articleDTO.setPrice(Double.parseDouble(line[4].replace("$","").replace(".","")));
                articleDTO.setQuantity(Integer.parseInt(line[5]));
                articleDTO.setFreeShipping(line[6]);
                articleDTO.setPrestige(line[7]);

                articles.add(articleDTO);
            }
        }
        catch (Exception e){
            throw new InternalServerErrorException("Inconsistencia en base de datos");
        }

        return articles;
    }

    private void saveArticles(List<ArticleDTO> articles) {
        List<String[]> dataLines = new ArrayList<>();

        dataLines.add(headArticleFile);
        for (ArticleDTO a: articles) {
            String[] arr = new String[8];

            arr[0] = a.getProductId().toString();
            arr[1] = a.getName();
            arr[2] = a.getCategory();
            arr[3] = a.getBrand();
            arr[4] = String.format("$%.0f", a.getPrice());
            arr[5] = a.getQuantity().toString();
            arr[6] = a.getFreeShipping();
            arr[7] = a.getPrestige();

            dataLines.add(arr);
        }

        HelperCSV.writeCSV(dataLines, nameArticleFile);
    }
}
