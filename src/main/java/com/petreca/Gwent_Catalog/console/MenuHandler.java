package com.petreca.Gwent_Catalog.console;

import com.petreca.Gwent_Catalog.model.Card;
import com.petreca.Gwent_Catalog.model.Faction;
import com.petreca.Gwent_Catalog.model.Rarity;
import com.petreca.Gwent_Catalog.model.SearchFilter;
import com.petreca.Gwent_Catalog.service.CardService;
import com.petreca.Gwent_Catalog.service.FavoriteService;
import com.petreca.Gwent_Catalog.util.ConsoleColors;
import com.petreca.Gwent_Catalog.util.TableFormater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MenuHandler {

    private final CardService cardService;
    private final FavoriteService favoriteService;

    public void listAllCards() {
        System.out.println(ConsoleColors.header("\uD83D\uDCCB LISTANDO TODAS AS CARTAS"));
        List<Card> cards = cardService.findAllCards();
        TableFormater.printCardsTable(cards);
    }

    public void searchCards() {
        System.out.println(ConsoleColors.header("\uD83D\uDD0D BUSCAR CARTAS POR NOME"));
        String name = InputValidator.getValidString("Digite o nome da carta (ou parte): ", false);

        List<Card> cards = cardService.searchCardsByName(name);
        if (cards.isEmpty()) {
            System.out.println(ConsoleColors.warning("Nenhuma carta encontrada com o nome: " + name));
        } else {
            TableFormater.printCardsTable(cards);
        }
    }

    public void filterCards() {
        System.out.println(ConsoleColors.header("\uD83C\uDFAF FILTRO AVANÇADO DE CARTAS"));

        SearchFilter.SearchFilterBuilder builder = SearchFilter.builder();

        // Nome
        String name = InputValidator.getValidString("Nome da Carta (opcional", true);
        if (name != null) builder.name(name);

        // Facção
        if (InputValidator.getYesNoConfirmation("Filtrar por Facção?")) {
            Faction faction = InputValidator.getValidFaction("Escolha a facção:");
            builder.faction(faction);
        }

        // Raridade
        if (InputValidator.getYesNoConfirmation("Filtrar por Raridade?")) {
            Rarity rarity = InputValidator.getValidRarity("Escolha a raridade:");
            builder.rarity(rarity);
        }

        // Poder
        if (InputValidator.getYesNoConfirmation("Filtrar por Poder?")) {
            Integer minPower = InputValidator.getValidInteger("Poder mínimo: ", 0, 100);
            Integer maxPower = InputValidator.getValidInteger("Poder máximo: ", minPower, 100);
            builder.minPower(minPower).maxPower(maxPower);
        }

        SearchFilter filter = builder.build();
        List<Card> cards = cardService.searchWithFilters(filter);

        if (cards.isEmpty()) {
            System.out.println(ConsoleColors.warning("Nenhuma carta encontrada com os filtros aplicados."));
        } else {
            TableFormater.printCardsTable(cards);
        }
    }

    public void showRandomCards () {
        System.out.println(ConsoleColors.header("\uD83C\uDFB2 CARTAS ALEATÓRIAS"));
        int count = InputValidator.getValidInteger("Quantas cartas aleatórias? (1-20): ", 1, 20);

        List<Card> randomCards = cardService.getRandomCards(count);
        TableFormater.printCardsTable(randomCards);
    }

    public void manageFavorites() {
        System.out.println(ConsoleColors.header("⭐ GERENCIAR FAVORITOS"));
        System.out.println("1. Ver Favoritos");
        System.out.println("2. Adicionar aos Favoritos");
        System.out.println("3. Remover dos Favoritos");

        int choice = InputValidator.getValidInteger("Escolha uma opção: ", 1, 3);

        switch (choice) {
            case 1 -> showFavorites();
            case 2 -> addToFavorites();
            case 3 -> removeFromFavorites();
        }
    }

    private void addToFavorites() {
        String cardName = InputValidator.getValidString("Digite o nome da carta: ", false);
        List<Card> cards = cardService.searchCardsByName(cardName);

        if (cards.isEmpty()) {
            System.out.println(ConsoleColors.warning("Carta não econtrada."));
            return;
        }

        if (cards.size() > 1) {
            System.out.println("Múltiplas Cartas encontradas:");
            TableFormater.printCardsTable(cards);
            int choice = InputValidator.getValidInteger("Escolha uma carta (número): ", 1 , cards.size());
            cards = List.of(cards.get(choice -1));
        }

        Card card = cards.get(0);

        if (favoriteService.isFavorite(card)) {
            System.out.println(ConsoleColors.warning("Esta carta já está nos seus Favoritos!"));
            return;
        }

        String notes = InputValidator.getValidString("Adicionar notas (opcional): ", true);
        favoriteService.addToFavorites(card,notes);
        System.out.println(ConsoleColors.success("Carta adicionada aos favoritos! ⭐"));
    }

    public void removeFromFavorites() {

    }
}
