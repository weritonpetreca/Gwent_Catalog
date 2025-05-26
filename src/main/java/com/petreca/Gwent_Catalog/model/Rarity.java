package com.petreca.Gwent_Catalog.model;

import lombok.Getter;

@Getter
public enum Rarity {

    COMMON("common", "Common"),
    RARE("rare", "Rare"),
    EPIC("epic", "Epic"),
    LEGENDARY("legendary", "Legendary");

    private final String apiValue;
    private final String displayValue;

    Rarity(String apiValue, String displayValue) {
        this.apiValue = apiValue;
        this.displayValue = displayValue;
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
