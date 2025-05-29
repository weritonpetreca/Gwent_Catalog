package com.petreca.Gwent_Catalog.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data // Criação de getters/setters/equals()/hashCode()/toString() pelo lombok
@JsonIgnoreProperties(ignoreUnknown = true)
/*
serialização/desserialização de objetos Java para JSON e vice-versa. Instrui a biblioteca Jackson a ignorar quaisquer propriedades desconhecidas presentes no JSON que não existam na classe CardDTO evitando erros.
*/
public class CardDTO {

    private CardId id;

    private CardAttributes attributes;

    private String name;

    private String category;

    private String ability;

    @JsonProperty("ability_html")
    private String abilityHtml;

    @JsonProperty("keyword_html")
    private String keywordHtml;

    private String flavor;
}
