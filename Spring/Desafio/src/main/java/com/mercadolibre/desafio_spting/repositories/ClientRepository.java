package com.mercadolibre.desafio_spting.repositories;

import com.mercadolibre.desafio_spting.dtos.ClientDTO;
import com.mercadolibre.desafio_spting.enums.ClientFilterTypes;
import com.mercadolibre.desafio_spting.enums.ClientOrderTypes;

import java.util.List;
import java.util.Map;

public interface ClientRepository {
    List<ClientDTO> getClients(Map<ClientFilterTypes, String> filters, ClientOrderTypes order);
    ClientDTO registerClient(ClientDTO clientDTO);
}
