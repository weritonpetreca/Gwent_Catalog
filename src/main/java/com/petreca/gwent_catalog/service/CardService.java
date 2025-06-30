package com.petreca.gwent_catalog.service;

import com.petreca.gwent_catalog.dto.CardDTO;
import com.petreca.gwent_catalog.dto.GwentApiResponse;
import com.petreca.gwent_catalog.model.Card;
import com.petreca.gwent_catalog.model.Faction;
import com.petreca.gwent_catalog.model.Rarity;
import com.petreca.gwent_catalog.model.SearchFilter;
import com.petreca.gwent_catalog.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import jakarta.persistence.criteria.Predicate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardService {

    @Autowired
    private final RestTemplate restTemplate;
    private final CardRepository cardRepository;

    private static final String GWENT_API_URL = "https://api.gwent.one/?key=data&response=json";

    //Sincroniza as cartas da API com o banco local
     public void syncCardsFromApi(){
         log.info("Iniciando sincronização de cartas da API: {}",
                 GWENT_API_URL);

         try {
             //Chama a API
             ResponseEntity<GwentApiResponse> response = restTemplate.getForEntity(
                     GWENT_API_URL,
                     GwentApiResponse.class
             );

             if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                 GwentApiResponse apiResponse = response.getBody();

                 if (apiResponse.getRequest() != null) {
                     log.info("API Response - Status: {}, Message: {}",
                             apiResponse.getRequest().getStatus(),
                             apiResponse.getRequest().getMessage());
                 }

                 //Converter o Map<String, CardDTO> para List<CardDTO>
                 if (apiResponse.getResponse() != null) {
                     List<CardDTO> cardsDTOs = new ArrayList<>(apiResponse.getResponse().values());

                     if (cardsDTOs != null && !cardsDTOs.isEmpty()) {

                         //Converte DTOs para entidades e salva
                         List<Card> cards = cardsDTOs.stream()
                                 .map(this::convertDtoToEntity)
                                 .filter(card -> cardRepository.findByName(card.getName()).isEmpty())
                                 .collect(Collectors.toList());

                         cardRepository.saveAll(cards);
                         log.info("Sincronização concluída. {} cartas adicionadas.", cards.size());
                     } else {
                         log.info("Cartas já sincronizadas.");
                     }

                 } else {
                     log.warn("Resposta da API não contém cartas");
                 }

             } else {
                 log.error("Erro na requisição à API. Status: {}",
                         response.getStatusCode());
             }

         } catch (RestClientException e) {
             log.error("Erro ao conectar com a API: {}",
                     e.getMessage(), e);
         } catch (Exception e) {
             log.error("Erro inesperado ao sincronizar cartas: {}",
                     e.getMessage(), e);
         }
     }

     //Converte CardDTO para Card
    private Card convertDtoToEntity(CardDTO dto) {
         Card card = new Card();
         card.setName(dto.getName());
         card.setPower(dto.getAttributes().getPower());
         card.setProvision(dto.getAttributes().getProvision());
         card.setFaction(Faction.fromApiValue(dto.getAttributes().getFaction()));
         card.setRarity(Rarity.fromApiValue(dto.getAttributes().getRarity()));
         card.setType(dto.getAttributes().getType());
         card.setAbility(dto.getAbility());
         card.setCategories(Collections.singletonList(dto.getCategory()));
         card.setFlavor(dto.getFlavor());
         return card;
    }

    //CRUD Methods
    public List<Card> findAllCards() {
         return cardRepository.findAll();
    }

    public Optional<Card> findCardById(Long id) {
         return cardRepository.findById(id);
    }

    public List<Card> searchCardsByName(String name) {
         return cardRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Card> findCardsByFaction(Faction faction) {
         return cardRepository.findByFaction(faction);
    }

    public List<Card> findCardsByRarity(Rarity rarity) {
         return cardRepository.findByRarity(rarity);
    }

    public Card saveCard(Card card) {
         return cardRepository.save(card);
    }

    public void deleteCard(Long id) {
         cardRepository.deleteById(id);
    }

    public long getTotalCards(){
         return cardRepository.count();
    }

    public List<Card> searchWithFilters(SearchFilter filter) {
         if (filter.isEmpty()) {
             return findAllCards();
         }

         //Usando Specification para busca dinâmica
        return cardRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getName() != null && !filter.getName().trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + filter.getName().toLowerCase() + "%"
                ));
            }

            if (filter.getFaction() != null) {
                predicates.add(criteriaBuilder.equal(root.get("faction"),
                        filter.getFaction()));
            }

            if (filter.getRarity() != null) {
                predicates.add(criteriaBuilder.equal(root.get("rarity"),
                        filter.getRarity()));
            }

            if (filter.getMinPower() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("power"), filter.getMinPower()));
            }

            if (filter.getMaxPower() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("power"), filter.getMaxPower()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }

    public List<Card> getRandomCards(int count) {
         List<Card> allCards = findAllCards();
         Collections.shuffle(allCards);
         return allCards.stream().limit(count)
                 .collect(Collectors.toList());
    }

    public Map<Faction, Long> getCardCountByFaction() {
         return cardRepository.findAll().stream()
                 .collect(Collectors.groupingBy(Card::getFaction, Collectors.counting()));
    }
}
