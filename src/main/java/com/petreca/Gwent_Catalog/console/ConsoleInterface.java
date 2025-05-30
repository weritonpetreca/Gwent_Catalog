package com.petreca.Gwent_Catalog.console;

import com.petreca.Gwent_Catalog.service.CardService;
import com.petreca.Gwent_Catalog.service.FavoriteService;
import com.petreca.Gwent_Catalog.util.ConsoleColors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class ConsoleInterface {

    private final CardService cardService;
    private final FavoriteService favoriteService;
    private final MenuHandler menuHandler;
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void run(String... args) throws Exception {

        //Sincronizar as cartas
        System.out.println(ConsoleColors.info("Sincronizando cartas da API..."));
        cardService.syncCardsFromApi();

        //Inicia a interface
        showWelcome();
        mainMenu();
    }

    public void showWelcome(){
        System.out.println(ConsoleColors.header("""
                ╔══════════════════════════════════════════════════════════════╗
                ║                    GWENT CARD CATALOG                        ║
                ║                   Bem-vindo ao catálogo!                     ║
                ╚══════════════════════════════════════════════════════════════╝
                """));

        long totalCards = cardService.getTotalCards();
        long totalFavorites = favoriteService.getTotalFavorites();

        System.out.println(ConsoleColors.info("📚 Total de cartas no catálogo: " + totalCards));
        System.out.println(ConsoleColors.info("⭐ Suas cartas favoritas: " + totalFavorites));
        System.out.println();
    }

    private void mainMenu() {
        while (true) {
            showMainMenu();
            int choice = InputValidator.getValidInteger("Digite sua opção: ", 0, 9);

            switch (choice) {
                case 1 -> menuHandler.listAllCards();
                case 2 -> menuHandler.searchCards();
                case 3 -> menuHandler.filterCards();
                case 4 -> menuHandler.showRandomCards();
                case 5 -> menuHandler.manageFavorites();
                case 6 -> menuHandler.showStatistics();
                case 7 -> menuHandler.cardDetails();
                case 8 -> menuHandler.syncCards();
                case 9 -> menuHandler.showHelp();
                case 0 -> {
                    System.out.println(ConsoleColors.success("Obrigado por usar o Gwent Catalog! \uD83C\uDFB4"));
                    System.exit(0);
                }
            }

            System.out.println("\nPressione Enter para continuar...");
            scanner.nextLine();
        }
    }

    private void showMainMenu() {
        System.out.println(ConsoleColors.header("""
                ┌────────────────────────────────────────────────────────────┐
                │                        MENU PRINCIPAL                      │
                ├────────────────────────────────────────────────────────────┤
                │ 1. 📋 Listar todas as cartas                               │
                │ 2. 🔍 Buscar cartas por nome                               │
                │ 3. 🎯 Filtrar cartas avançado                              │
                │ 4. 🎲 Cartas aleatórias                                    │
                │ 5. ⭐ Gerenciar favoritos                                  │
                │ 6. 📊 Estatísticas                                         │
                │ 7. 🔎 Detalhes de uma carta                                │
                │ 8. 🔄 Sincronizar com API                                  │
                │ 9. ❓ Ajuda                                                │
                │ 0. 🚪 Sair                                                 │
                └────────────────────────────────────────────────────────────┘            
                """));
    }
}
