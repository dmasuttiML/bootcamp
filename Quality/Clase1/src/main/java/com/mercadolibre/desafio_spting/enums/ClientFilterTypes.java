package com.mercadolibre.desafio_spting.enums;

import java.util.Arrays;

public enum ClientFilterTypes {
    DNI("dni"),
    NAME("name"),
    PROVINCIA("provincia"),
    EMAIL("email");

    private final String key;

    ClientFilterTypes(String key) {
        this.key = key;
    }

    public static ClientFilterTypes valueOfKey(String key){
        return Arrays.stream(ClientFilterTypes.values())
                     .filter(v -> v.key.equals(key))
                     .findFirst()
                     .orElse(null);
    }
}
