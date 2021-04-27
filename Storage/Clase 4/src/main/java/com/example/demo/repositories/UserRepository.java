package com.example.demo.repositories;

import com.example.demo.dtos.User;
import com.mercadolibre.kvsclient.exceptions.KvsException;

public interface UserRepository {
    User get(String key) throws KvsException;
    void save(User user) throws KvsException;
    void update(User user) throws KvsException;
    void delete(String key) throws KvsException;
}
