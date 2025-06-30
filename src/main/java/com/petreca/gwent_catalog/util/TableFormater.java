package com.petreca.gwent_catalog.util;

import com.petreca.gwent_catalog.model.Card;
import com.petreca.gwent_catalog.model.Faction;
import com.petreca.gwent_catalog.model.Favorite;
import com.petreca.gwent_catalog.model.Rarity;

import java.util.List;

public class TableFormater {

    private static final String HORIZONTAL_LINE = "+" + "-".repeat(100) + "+";
    private static final String HEADER_FORMAT = "| %-3s | %-25s | %-12s | %-10s | %-8s | %-8s | %-10s |%n";
    private static final String ROW_FORMAT = "| %-3d | %-25s | %-12s | %-10s | %-8s | %-8s | %-10s |%n";

    public static void printCardsTable(List<Card> cards) {
        if (cards.isEmpty()) {
            System.out.println(ConsoleColors.warning("Nenhuma carta encontrada."));
            return;
        }

        System.out.println(HORIZONTAL_LINE);
        System.out.printf(ConsoleColors.header(HEADER_FORMAT),
                "#", "Nome", "Facção", "Raridade", "Poder", "Provisão", "Tipo");
        System.out.println(HORIZONTAL_LINE);

        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            System.out.printf(ROW_FORMAT,
                    i + 1,
                    truncate(card.getName(), 25),
                    card.getFaction().getApiValue(),
                    card.getRarity().getApiValue(),
                    card.getPower() != null ? card.getPower().toString() : "N/A",
                    card.getProvision() != null ? card.getProvision().toString() : "N/A",
                    truncate(card.getType(), 10)
            );
        }

        System.out.println(HORIZONTAL_LINE);
        System.out.println(ConsoleColors.info("Total: " + cards.size() + " cartas."));
    }

    public static void printCardDetails(Card card) {
        System.out.println(HORIZONTAL_LINE);
        System.out.println(ConsoleColors.header("DETALHES DA CARTA"));
        System.out.println(HORIZONTAL_LINE);
        System.out.println("Nome: " + ConsoleColors.colorize(card.getName(), ConsoleColors.BOLD_WHITE));
        System.out.println("Facção: " + getFactionColor(card.getFaction()) + card.getFaction().getApiValue() + ConsoleColors.RESET);
        System.out.println("Raridade: " + getRarityColor(card.getRarity()) + card.getRarity().getApiValue() + ConsoleColors.RESET);
        System.out.println("Tipo: " + (card.getType() != null ? card.getType() : "N/A"));
        System.out.println("Poder: " + (card.getPower() != null ? card.getPower() : "N/A"));
        System.out.println("Provisão: " + (card.getProvision() != null ? card.getProvision() : "N/A"));

        if (card.getCategories() != null && !card.getCategories().isEmpty()) {
            System.out.println("Categorias: " + String.join(", ", card.getCategories()));
        }

        if (card.getAbility() != null && !card.getAbility().trim().isEmpty()) {
            System.out.println("Habilidade: " + card.getAbility());
        }

        System.out.println(HORIZONTAL_LINE);
    }

    public static void printFavoritesTable(List<Favorite> favorites) {
        if (favorites.isEmpty()) {
            System.out.println("Você ainda não tem cartas favoritas.");
            return;
        }

        System.out.println(HORIZONTAL_LINE);
        System.out.println(ConsoleColors.header("SUAS CARTAS FAVORITAS"));
        System.out.println(HORIZONTAL_LINE);

        for (int i = 0; i < favorites.size(); i++) {
            Favorite fav = favorites.get(i);
            Card card = fav.getCard();
            System.out.printf("%-3d | %s | %s%n",
                    i + 1,
                    card.getName(),
                    fav.getNotes() != null ? fav.getNotes() : ""
            );
        }
        System.out.println(HORIZONTAL_LINE);
    }

    private static String truncate(String text, int maxLength) {
        if (text == null) return "N/A";
        return text.length() > maxLength ? text.substring(0, maxLength - 3) + "..." : text;
    }

    public static String getFactionColor(Faction faction) {
        return switch (faction) {
            case MONSTERS -> ConsoleColors.RED;
            case NILFGAARD -> ConsoleColors.BLACK;
            case NORTHERN_REALMS -> ConsoleColors.BLUE;
            case SCOIA_TAEL -> ConsoleColors.GREEN;
            case SKELLIGE -> ConsoleColors.CYAN;
            case SYNDICATE -> ConsoleColors.YELLOW;
            default -> ConsoleColors.WHITE;
        };
    }

    public static String getRarityColor(Rarity rarity) {
        return switch (rarity) {
            case COMMON -> ConsoleColors.WHITE;
            case RARE -> ConsoleColors.BLUE;
            case EPIC -> ConsoleColors.PURPLE;
            case LEGENDARY -> ConsoleColors.YELLOW;
        };
    }
}
