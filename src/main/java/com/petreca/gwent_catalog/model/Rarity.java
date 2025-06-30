package com.petreca.gwent_catalog.model;

import lombok.Getter;

@Getter
public enum Rarity {

    COMMON("Common"),
    RARE("Rare"),
    EPIC("Epic"),
    LEGENDARY("Legendary");

    private final String apiValue;

    Rarity(String apiValue) {
        this.apiValue = apiValue;
    }

    public static Rarity fromApiValue(String apiValue) {
        for (Rarity rarity : values()) {
            if (rarity.apiValue.equals(apiValue)){
                return rarity;
            }
        }
        return COMMON;
    }
}
