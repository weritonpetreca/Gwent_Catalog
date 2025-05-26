package com.petreca.Gwent_Catalog.model;


import lombok.Getter;

@Getter // lombok: gera os getters.
public enum Faction {
    /*
    Separar a representação interna (usada no código) da representação externa (usada na API e na interface do usuário)
     */
    NEUTRAL("neutral", "Neutral"),
    MONSTERS("monsters", "Monsters"),
    NILFGAARD("nilfgaard", "Nilfgaard"),
    NORTHERN_REALMS("norther_realms", "Norther_Realms"),
    SCOIA_TAEL("scoia_tael", "Scoia_Tael"),
    SKELLIGE("skellige", "Skellige"),
    SYNDICATE("syndicate", "Syndicate");

    private final String apiValue;
    private final String displayName;

    Faction(String apiValue, String displayName) {
        this.apiValue = apiValue;
        this.displayName = displayName;
    }

    //Converter a string da API para enum.
    public static Faction fromApiValue(String apiValue) {
        for (Faction faction : values()) {
            if (faction.apiValue.equals(apiValue)) {
                return faction;
            }
        }
        return NEUTRAL; // Deafault.
    }


}
