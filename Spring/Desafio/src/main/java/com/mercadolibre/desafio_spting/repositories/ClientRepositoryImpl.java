package com.mercadolibre.desafio_spting.repositories;


import com.mercadolibre.desafio_spting.dtos.ArticleDTO;
import com.mercadolibre.desafio_spting.dtos.ClientDTO;
import com.mercadolibre.desafio_spting.dtos.RegisterClientResponseDTO;
import com.mercadolibre.desafio_spting.enums.ArticleFilterTypes;
import com.mercadolibre.desafio_spting.enums.ArticleOrderTypes;
import com.mercadolibre.desafio_spting.enums.ClientFilterTypes;
import com.mercadolibre.desafio_spting.enums.ClientOrderTypes;
import com.mercadolibre.desafio_spting.exceptions.ClientNotFoundException;
import com.mercadolibre.desafio_spting.exceptions.InternalServerErrorException;
import com.mercadolibre.desafio_spting.exceptions.InvalidArgumentException;
import com.mercadolibre.desafio_spting.utils.HelperCSV;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class ClientRepositoryImpl implements ClientRepository{
    private final String[] headArticleFile = new String[] { "clientId", "dni", "name", "provincia", "email" };
    private static final String nameClientFile = "dbClientes.csv";

    /* getClients
        Obtiene, filtra y ordena los clientes mediante un mapa de filtros y un metodo de ordenamiento.
        Si el parametro order es null, no los ordena.
    */
    @Override
    public List<ClientDTO> getClients(Map<ClientFilterTypes, String> filters, ClientOrderTypes order) {
        List<ClientDTO> clients = loadClients();

        clients = filterClients(clients, filters);

        if(order != null)
            clients = orderClients(clients, order);

        return clients;
    }

    /* registerClient
        Registra un nuevo cliente.
        Retorna el cliente con su correspondiente id
     */
    @Override
    public ClientDTO registerClient(ClientDTO clientDTO) {
        List<ClientDTO> clients = loadClients();

        for (ClientDTO c: clients) {
            if(c.getDni().equals(clientDTO.getDni()))
                throw new ClientNotFoundException("El DNI ya existe en la base de datos");
            if(c.getEmail().equals(clientDTO.getEmail()))
                throw new ClientNotFoundException("El email ya existe en la base de datos");
        }

        // Aplico el index al nuevo cliente.
        int lastIndex = clients.size() > 0 ? Collections.max(clients.stream().map(ClientDTO::getClientID).collect(Collectors.toList())) : 0;
        clientDTO.setClientID(lastIndex + 1);
        clients.add(clientDTO);

        // Guardo los cliebtes
        saveClient(clients);

        return clientDTO;
    }

    private List<ClientDTO> filterClients(List<ClientDTO> clients, Map<ClientFilterTypes, String> filters){
        Stream<ClientDTO> stream = clients.stream();

        for (Map.Entry<ClientFilterTypes,String> entry : filters.entrySet()) {
            switch (entry.getKey()){
                case NAME:
                    // En este caso lo hago por contains
                    stream = stream.filter(a -> a.getName().contains(entry.getValue().trim()));
                    break;
                case DNI:
                    stream = stream.filter(a -> a.getDni().equals(entry.getValue().trim()));
                    break;
                case PROVINCIA:
                    stream = stream.filter(a -> a.getProvincia().equals(entry.getValue().trim()));
                    break;
                case EMAIL:
                    stream = stream.filter(a -> a.getEmail().equals(entry.getValue().trim()));
                    break;
            }
        }

        return stream.collect(Collectors.toList());
    }

    private List<ClientDTO> orderClients(List<ClientDTO> clients, ClientOrderTypes order){
        Stream<ClientDTO> stream = clients.stream();

        switch (order) {
            case NAME_ASC:
                stream = stream.sorted(Comparator.comparing(ClientDTO::getName));
                break;
            case NAME_DESC:
                stream = stream.sorted((a, b) -> b.getName().compareTo(a.getName()));
                break;
        }

        return stream.collect(Collectors.toList());
    }

    private List<ClientDTO> loadClients() {
        List<ClientDTO> clients = new ArrayList<>();
        List<String[]> dataLines = HelperCSV.readCSV(nameClientFile);

        try {
            // Elimino la cabecera.
            if(dataLines.size() > 0)
                dataLines.remove(0);

            for (String[] line: dataLines) {
                ClientDTO clientDTO = new ClientDTO();

                clientDTO.setClientID(Integer.parseInt(line[0]));
                clientDTO.setDni(line[1]);
                clientDTO.setName(line[2]);
                clientDTO.setProvincia(line[3]);
                clientDTO.setEmail(line[4]);

                clients.add(clientDTO);
            }
        }
        catch (Exception e){
            throw new InternalServerErrorException("Inconsistencia en base de datos");
        }

        return clients;
    }

    private void saveClient(List<ClientDTO> clients) {
        List<String[]> dataLines = new ArrayList<>();

        dataLines.add(headArticleFile);
        for (ClientDTO c: clients) {
            String[] arr = new String[5];

            arr[0] = c.getClientID().toString();
            arr[1] = c.getDni();
            arr[2] = c.getName();
            arr[3] = c.getProvincia();
            arr[4] = c.getEmail();

            dataLines.add(arr);
        }

        HelperCSV.writeCSV(dataLines, nameClientFile);
    }
}
