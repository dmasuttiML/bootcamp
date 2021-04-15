package com.mercadolibre.desafio_spting.services;

import com.mercadolibre.desafio_spting.dtos.*;
import com.mercadolibre.desafio_spting.enums.ArticleFilterTypes;
import com.mercadolibre.desafio_spting.enums.ArticleOrderTypes;
import com.mercadolibre.desafio_spting.enums.ClientFilterTypes;
import com.mercadolibre.desafio_spting.enums.ClientOrderTypes;
import com.mercadolibre.desafio_spting.exceptions.InvalidArgumentException;
import com.mercadolibre.desafio_spting.repositories.ClientRepository;
import com.mercadolibre.desafio_spting.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;

    /* getClients
      Metodo que a partir de un mapa de parametros, obtiene una lista de clientes.
      En la misma se validan los parametros recibidos y se los pasa a la función getClients
      de CLientRepository, para que le devuelva la lista de clientes filtrada y ordenada.
    */
    @Override
    public List<ClientDTO> getClients(Map<String, String> params) {
        // Validation of order parameter
        ClientOrderTypes order = null;
        if(params.containsKey("order")){
            order = ClientOrderTypes.valueOfId(params.get("order").trim());
            if(order == null)
                throw new InvalidArgumentException("Metodo de ordenamiento de clientes invalido: " + params.get("order"));//invalid input
            params.remove("order");
        }

        // Valdation of filters parameters
        Map<ClientFilterTypes, String> filters = new HashMap<>();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            ClientFilterTypes filter = ClientFilterTypes.valueOfKey(entry.getKey().trim());
            if(filter == null)
                throw new InvalidArgumentException("Filtro de clientes invalido: " + entry.getKey());
            filters.put(filter, entry.getValue());
        }

        return clientRepository.getClients(filters, order);
    }


    /* registerClient
        Metodo utilizado para registrar un cliente.
        En la misma se validan los campos del cliente y se los pasa a la función registerClient
        de ClientRepository, para que efecue el registro.
     */
    @Override
    public RegisterClientResponseDTO registerClient(ClientDTO clientDTO) {

        if(!ValidatorUtil.isEmail(clientDTO.getEmail())) {
            throw new InvalidArgumentException("El email no tiene un formato correcto: " + clientDTO.getEmail());
        }
        if(!ValidatorUtil.isDNI(clientDTO.getDni())){
            throw new InvalidArgumentException("El dni no tiene un formato correcto: " + clientDTO.getDni());
        }

        clientDTO = clientRepository.registerClient(clientDTO);
        StatusCodeDTO statusCodeDTO = new StatusCodeDTO(HttpStatus.OK.value(), "El cliente fue registrado con éxito");

        return new RegisterClientResponseDTO(clientDTO, statusCodeDTO);
    }


}
