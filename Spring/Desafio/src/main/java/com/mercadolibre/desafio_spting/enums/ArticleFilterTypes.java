package com.mercadolibre.desafio_spting.enums;

import java.util.Arrays;

public enum ArticleFilterTypes {
    NAME("product"),
    CATEGORY("category"),
    BRAND("brand"),
    PRICE("price"),
    FREE_SHIPPING("freeShipping"),
    PRESTIGE("prestige");

    private final String key;

    ArticleFilterTypes(String key) {
        this.key = key;
    }

    public static ArticleFilterTypes valueOfKey(String key) {
        return Arrays.stream(ArticleFilterTypes.values())
                     .filter(v -> v.key.equals(key))
                     .findFirst()
                     .orElse(null);
    }
}
