package com.mercadolibre.desafio_spting.enums;

import java.util.Arrays;

public enum ArticleOrderTypes {
    ALF_ASC("0"),
    ALF_DESC("1"),
    MAX_TO_MIN("2"),
    MIN_TO_MAX("3");

    private final String key;

    ArticleOrderTypes(String key) {
        this.key = key;
    }

    public static ArticleOrderTypes valueOfId(String key){
        return Arrays.stream(ArticleOrderTypes.values())
                     .filter(v -> v.key.equals(key))
                     .findFirst()
                     .orElse(null);
    }
}
