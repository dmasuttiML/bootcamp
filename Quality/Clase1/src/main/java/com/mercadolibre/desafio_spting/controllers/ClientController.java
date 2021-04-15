package com.mercadolibre.desafio_spting.controllers;

import com.mercadolibre.desafio_spting.dtos.ClientDTO;
import com.mercadolibre.desafio_spting.dtos.ErrorDTO;
import com.mercadolibre.desafio_spting.dtos.PurchaseDTO;
import com.mercadolibre.desafio_spting.dtos.StatusCodeDTO;
import com.mercadolibre.desafio_spting.exceptions.ApiException;
import com.mercadolibre.desafio_spting.services.ArticleService;
import com.mercadolibre.desafio_spting.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class ClientController {
    @Autowired
    private ClientService clientService;

    /* getClientes
        Metdo GET para la obtención de clientes almacenados en la base de datos.
        Retorna una lista de ClientDTO.
        Parametros posibles:
            - Filtros:
                   * dni: String
                   * name: String
                   * provincia: String
                   * email: String;
            - Metodos de ordenamiento:
                * order: 0 (nombre ascendente) ó 1 (nombre descendente)
     */
    @GetMapping("/clients")
    public ResponseEntity getClient(@RequestParam() Map<String, String> params) {
            return ResponseEntity.ok(clientService.getClients(params));
    }

    /* registerClient
        Metdo POST para el registrado de un nuevo cliente.
        Retorna una objeto del tipo  RegisterClientResponseDTO.
    */
    @PostMapping("/registerClient")
    public ResponseEntity registerClient(@RequestBody ClientDTO clientDTO){
        return ResponseEntity.ok(clientService.registerClient(clientDTO));
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
