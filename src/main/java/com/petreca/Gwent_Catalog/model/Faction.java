package com.petreca.Gwent_Catalog.model;


import lombok.Getter;

@Getter // lombok: gera os getters.
public enum Faction {
    /*
    Separar a representação interna (usada no código) da representação externa (usada na API e na interface do usuário)
     */
    NEUTRAL("Neutral"),
    MONSTERS("Monster"),
    NILFGAARD("Nilfgaard"),
    NORTHERN_REALMS("Norther Realms"),
    SCOIA_TAEL("Scoiatael"),
    SKELLIGE("Skellige"),
    SYNDICATE("Syndicate");

    private final String apiValue;

    Faction(String apiValue) {
        this.apiValue = apiValue;
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
