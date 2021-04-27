package com.example.demo.repositories;

import com.example.demo.dtos.User;
import com.mercadolibre.kvsclient.ContainerKvsLowLevelClient;
import com.mercadolibre.kvsclient.exceptions.KvsException;
import com.mercadolibre.kvsclient.item.Item;
import com.mercadolibre.kvsclient.kvsapi.KvsapiConfiguration;

public class UserRepositoryImpl implements UserRepository {
    ContainerKvsLowLevelClient client;

    public UserRepositoryImpl(){
        KvsapiConfiguration config = KvsapiConfiguration.builder().build();
        client = new ContainerKvsLowLevelClient(config, "KEY_VALUE_STORE_MY_CONTAINER_CONTAINER_NAME");
    }


    @Override
    public User get(String key) throws KvsException {
        Item item = client.get(key);

        return null;
    }

    @Override
    public void save(User user) throws KvsException {

    }

    @Override
    public void update(User user) throws KvsException {

    }

    @Override
    public void delete(String key) throws KvsException {

    }
}
