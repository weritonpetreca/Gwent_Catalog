package com.petreca.Gwent_Catalog;

import com.petreca.Gwent_Catalog.model.Faction;
import com.petreca.Gwent_Catalog.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class GwentCatalogApplication implements CommandLineRunner {

	private final CardService cardService;

	public static void main(String[] args) {
		SpringApplication.run(GwentCatalogApplication.class, args);
	}

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
	}

}
