package com.mercadolibre.desafio_spting.controllers;

import com.mercadolibre.desafio_spting.dtos.ErrorDTO;
import com.mercadolibre.desafio_spting.dtos.PurchaseDTO;
import com.mercadolibre.desafio_spting.dtos.StatusCodeDTO;
import com.mercadolibre.desafio_spting.exceptions.ApiException;
import com.mercadolibre.desafio_spting.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /* getArticles
        Metdo GET solicitado en el punto 1 del desafio.
        Retorna una lista de ClientDTO.
     */
    @GetMapping("/articles")
    public ResponseEntity getArticles(@RequestParam() Map<String, String> params){
        return ResponseEntity.ok(articleService.getArticles(params));
    }

    /* generatePurchase
        Metdo POST solicitado en el punto 7 del desafio.
        Retorna una lista de ClientDTO.
    */
    @PostMapping("/purchase-request")
    public ResponseEntity generatePurchase(@RequestBody PurchaseDTO purchaseDTO){
        return ResponseEntity.ok(articleService.generatePurchase(purchaseDTO));
    }

    /* generatePurchase
        Agrega una compra al carro de un cliente.
     */
    @PostMapping("/addToCart/{clientId}")
    public ResponseEntity generatePurchase(@PathVariable Integer clientId, @RequestBody PurchaseDTO purchaseDTO){
        return ResponseEntity.ok("Compra agregada al carro del usuario: " + clientId);
    }

    /* purchaseCart
        Finaliza la compra del carro para un cliente.
     */
    @PostMapping("/purchaseCart/{clientId}")
    public ResponseEntity purchaseCart(@PathVariable Integer clientId){
        return ResponseEntity.ok("Compra finalizada para el cliente: " + clientId);
    }

    /* emptyPurchaseCart
       Limpia el carro para un cliente.
     */
    @PostMapping("/emptyPurchaseCart/{clientId}")
    public ResponseEntity emptyPurchaseCart(@PathVariable Integer clientId){
        return ResponseEntity.ok("Carro vacio para el cliente: " + clientId);
    }

    /* handleApiException
        ExceptionHandler para el control de excepociones personalizadas de la api.
        Retorna un ErrorDTO con los valores correspondientes de la excepción del tipo ApiException.
     */
    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<ErrorDTO> handleApiException(ApiException e) {
        StatusCodeDTO statusCodeDTO = new StatusCodeDTO(e.getStatusCode(), e.getDescription());
        return ResponseEntity.status(e.getStatusCode())
                             .body(new ErrorDTO(statusCodeDTO));
    }

    /* handleUnknownException
        ExceptionHandler para el control de excepociones no controladas en la api.
        Retorna un ErrorDTO con los valores correspondientes de la excepción del tipo ApiException.
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorDTO> handleUnknownException(Exception e) {
        StatusCodeDTO statusCodeDTO = new StatusCodeDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Internal server error");
        return ResponseEntity.status(statusCodeDTO.getCode())
                             .body(new ErrorDTO(statusCodeDTO));
    }
}
