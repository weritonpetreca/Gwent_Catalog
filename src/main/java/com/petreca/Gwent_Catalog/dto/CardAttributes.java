package com.petreca.Gwent_Catalog.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardAttributes {

    private String set;

    private String type;

    private int armor;

    private String color;

    private int power;

    private int reach;

    private String artist;

    private String rarity;

    private String faction;

    private String related;

    private int provision;

    private String factionSecondary;
}
