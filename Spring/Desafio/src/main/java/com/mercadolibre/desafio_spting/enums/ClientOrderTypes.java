package com.mercadolibre.desafio_spting.enums;

import java.util.Arrays;

public enum ClientOrderTypes {
    NAME_ASC("0"),
    NAME_DESC("1");

    private final String key;

    ClientOrderTypes(String key) {
        this.key = key;
    }

    public static ClientOrderTypes valueOfId(String key){
        return Arrays.stream(ClientOrderTypes.values())
                     .filter(v -> v.key.equals(key))
                     .findFirst()
                     .orElse(null);
    }
}
