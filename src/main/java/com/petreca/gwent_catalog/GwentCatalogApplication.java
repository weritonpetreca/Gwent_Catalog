package com.petreca.gwent_catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GwentCatalogApplication  { //implements CommandLineRunner


    public static void main(String[] args) {
        // Desabilita banner do Spring para interface limpa
        System.setProperty("spring.main.banner-mode", "off");
        SpringApplication.run(GwentCatalogApplication.class, args);
    }

    /*
    @Override
    public void run(String... args) throws Exception {
        //Testa a sincronização na inicialização
        System.out.println("=== INICIANDO TESTE DA API ===");
        cardService.syncCardsFromApi();

        System.out.println("Total de cartas no banco: " + cardService.getTotalCards());

        //Testa algumas buscas
        System.out.println("\n=== TESTANDO BUSCAS ===");
        cardService.findCardsByFaction(Faction.MONSTERS)
                .forEach(card -> System.out.println("Monsters: " + card.getName()));

        cardService.findCardsByRarity(Rarity.LEGENDARY)
                .forEach(card -> System.out.println("Legendary: " + card.getName()));
    }
    */
}
