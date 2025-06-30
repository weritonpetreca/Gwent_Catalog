package com.petreca.gwent_catalog.model;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;
import java.util.stream.Stream;

@Data
@Builder
public class SearchFilter {

    private String name;
    private Faction faction;
    private Rarity rarity;
    private Integer minPower;
    private Integer maxPower;
    private Integer minProvision;
    private Integer maxProvision;
    private String type;
    private String category;

    public boolean isEmpty() {
        return Stream.of(
                name,
                faction,
                rarity,
                minPower,
                maxPower,
                minProvision,
                maxProvision,
                type,
                category
        ).allMatch(Objects::isNull);
    }
}
