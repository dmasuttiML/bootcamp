package com.mercadolibre.desafio_spting.services;

import com.mercadolibre.desafio_spting.dtos.ClientDTO;
import com.mercadolibre.desafio_spting.dtos.RegisterClientResponseDTO;

import java.util.List;
import java.util.Map;

public interface ClientService {
    List<ClientDTO> getClients(Map<String, String> params);
    RegisterClientResponseDTO registerClient(ClientDTO clientDTO);
}
