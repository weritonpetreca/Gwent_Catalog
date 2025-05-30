package com.petreca.Gwent_Catalog.console;

import com.petreca.Gwent_Catalog.model.*;
import com.petreca.Gwent_Catalog.service.CardService;
import com.petreca.Gwent_Catalog.service.FavoriteService;
import com.petreca.Gwent_Catalog.util.ConsoleColors;
import com.petreca.Gwent_Catalog.util.TableFormater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.petreca.Gwent_Catalog.util.TableFormater.getFactionColor;
import static com.petreca.Gwent_Catalog.util.TableFormater.getRarityColor;

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
        String name = InputValidator.getValidString("Nome da Carta (opcional)", true);
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

    public void showRandomCards() {
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

    private void showFavorites() {
        List<Favorite> favorites = favoriteService.getAllFavorites();
        TableFormater.printFavoritesTable(favorites);
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
            int choice = InputValidator.getValidInteger("Escolha uma carta (número): ", 1, cards.size());
            cards = List.of(cards.get(choice - 1));
        }

        Card card = cards.get(0);

        if (favoriteService.isFavorite(card)) {
            System.out.println(ConsoleColors.warning("Esta carta já está nos seus Favoritos!"));
            return;
        }

        String notes = InputValidator.getValidString("Adicionar notas (opcional): ", true);
        favoriteService.addToFavorites(card, notes);
        System.out.println(ConsoleColors.success("Carta adicionada aos favoritos! ⭐"));
    }

    public void removeFromFavorites() {
        List<Favorite> favorites = favoriteService.getAllFavorites();
        if (favorites.isEmpty()) {
            System.out.println(ConsoleColors.warning("Você não tem cartas favoritas."));
            return;
        }

        TableFormater.printFavoritesTable(favorites);
        int choice = InputValidator.getValidInteger("Qual carta remover? (número): ", 1, favorites.size());

        Card card = favorites.get(choice - 1).getCard();

        if (InputValidator.getYesNoConfirmation("Confirma a remoção da Carta '" + card.getName() + "'?")) {
            favoriteService.removeFromFavorites(card);
            System.out.println(ConsoleColors.success("Carta removida dos Favoritos!"));
        }
    }

    public void showStatistics() {
        System.out.println(ConsoleColors.header("\uD83D\uDCCA ESTATÍSTICAS DO CATÁLOGO"));

        long totalCards = cardService.getTotalCards();
        long totalFavorites = favoriteService.getTotalFavorites();

        System.out.println("📚 Total de cartas: " + totalCards);
        System.out.println("⭐ Cartas favoritas: " + totalFavorites);
        System.out.println();

        // Estatística por Facção
        System.out.println(ConsoleColors.info("\uD83D\uDCCA CARTAS POR FACÇÃO:"));
        Map<Faction, Long> factionStats = cardService.getCardCountByFaction();
        factionStats.entrySet().stream()
                .sorted(Map.Entry.<Faction, Long>comparingByValue().reversed())
                .forEach(entry -> {
                    String color = getFactionColor(entry.getKey());
                    System.out.printf("%s%-15s: %d cartas%s%n",
                            color,
                            entry.getKey().getApiValue(),
                            entry.getValue(),
                            ConsoleColors.RESET
                    );
                });

        //Estatistica por raridade
        System.out.println();
        System.out.println(ConsoleColors.header("\uD83D\uDC8E CARTAS POR RARIDADE:"));
        cardService.findAllCards().stream()
                .collect(Collectors.groupingBy(Card::getRarity, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<Rarity, Long>comparingByValue().reversed())
                .forEach(entry -> {
                    String color = getRarityColor(entry.getKey());
                    System.out.printf("%s%-10s: %d cartas%s%n",
                            color,
                            entry.getKey().getApiValue(),
                            entry.getValue(),
                            ConsoleColors.RESET
                    );
                });
    }

    public void cardDetails() {
        System.out.println(ConsoleColors.header("\uD83D\uDD0E DETALHES DA CARTA"));
        String cardName = InputValidator.getValidString("Digite o nome da carta: ", false);

        List<Card> cards = cardService.searchCardsByName(cardName);

        if (cards.isEmpty()) {
            System.out.println(ConsoleColors.warning("Carta não encontrada."));
            return;
        }

        Card selectedCard;
        if (cards.size() > 1) {
            System.out.println("Múltiplas cartas encontradas:");
            TableFormater.printCardsTable(cards);
            int choice = InputValidator.getValidInteger("Escolha uma carta (número): ", 1, cards.size());
            selectedCard = cards.get(choice - 1);
        } else {
            selectedCard = cards.get(0);
        }

        TableFormater.printCardDetails(selectedCard);

        // Mostra se é favorita
        if (favoriteService.isFavorite(selectedCard)) {
            System.out.println(ConsoleColors.success("⭐ Esta carta está nos seus favoritos!"));

            //Opção de gerenciar favorito
            if (InputValidator.getYesNoConfirmation("Deseja gerenciar este favorito?")) {
                manageSingleFavorite(selectedCard);
            }
        } else {
            if (InputValidator.getYesNoConfirmation("Deseja adicionar esta Carta aos favoritos?")) {
                String notes = InputValidator.getValidString("Adicionar notas (opcional): ", true);
                favoriteService.addToFavorites(selectedCard, notes);
                System.out.println(ConsoleColors.success("Carta adicionada aos Favoritos!"));
            }
        }
    }

    public void manageSingleFavorite(Card card) {
        System.out.println("1. Atualizar notas");
        System.out.println("2. Remover dos favoritos");

        int choice = InputValidator.getValidInteger("Escolha uma opção", 1, 2);

        switch (choice) {
            case 1 -> {
                String newNotes = InputValidator.getValidString("Novas notas (opcional): ", true);
                favoriteService.updateNotes(card, newNotes);
                System.out.println(ConsoleColors.success("Notas atualizadas!"));
            }
            case 2 -> {
                if (InputValidator.getYesNoConfirmation("Confirma a remoção?")) {
                    favoriteService.removeFromFavorites(card);
                    System.out.println(ConsoleColors.success("Carta removida dos Favoritos!"));
                }
            }
        }
    }

    public void syncCards() {
        System.out.println(ConsoleColors.header("\uD83D\uDD04 SINCRONIZANDO COM A API"));
        System.out.println(ConsoleColors.warning("Esta operação pode demorar alguns segundos..."));

        try {
            long cardsBefore = cardService.getTotalCards();
            cardService.syncCardsFromApi();
            long cardsAfter = cardService.getTotalCards();
            long newCards = cardsAfter - cardsBefore;

            if (newCards > 0) {
                System.out.println(ConsoleColors.success("Sincronização concluída! " + newCards + "novas cartas adicionadas"));
            } else {
                System.out.println(ConsoleColors.info("Sincronização concluída! Nenhuma carta nova encontrada."));
            }
            System.out.println("Total de cartas no Catálogo: " + cardsAfter);

        } catch (Exception e) {
            System.out.println(ConsoleColors.error("Erro na sincronização: " + e.getMessage()));
            System.out.println("Verifique sua conexão com a internet e tente novamente.");
        }
    }

    public void showHelp() {
        System.out.println(ConsoleColors.header("❓ AJUDA - COMO USAR O GWENT CATALOG"));
        System.out.println("""
                🎯 FUNCIONALIDADES PRINCIPAIS:
                
                📋 LISTAR CARTAS: Mostra todas as cartas em formato de tabela
                
                🔍 BUSCAR: Digite parte do nome da carta para encontrá-la
                   Exemplo: "Geralt" encontra "Geralt of Rivia"
                
                🎯 FILTRO AVANÇADO: Combine múltiplos critérios:
                   • Nome, Facção, Raridade
                   • Faixa de poder e provisão
                   • Tipo de carta
                
                🎲 CARTAS ALEATÓRIAS: Descubra cartas que você não conhece
                
                ⭐ FAVORITOS: Salve suas cartas preferidas com notas pessoais
                
                📊 ESTATÍSTICAS: Veja distribuição por facção e raridade
                
                🔎 DETALHES: Informações completas sobre qualquer carta
                
                🔄 SINCRONIZAÇÃO: Atualiza o catálogo com a API oficial
                
                💡 DICAS:
                • Use nomes parciais nas buscas (mais fácil)
                • Favoritos podem ter notas personalizadas
                • Estatísticas mostram quais facções têm mais cartas
                • A sincronização só adiciona cartas novas (não duplica)
                
                🎨 CORES NO TERMINAL:
                • Facções têm cores específicas
                • Raridades também são coloridas
                • Verde = sucesso, Vermelho = erro, Azul = informação            
                """);
    }

}
