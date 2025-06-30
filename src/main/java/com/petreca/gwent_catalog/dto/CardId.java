package com.petreca.gwent_catalog.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardId {

    private int art;
    private int card;
    private int audio;
}
