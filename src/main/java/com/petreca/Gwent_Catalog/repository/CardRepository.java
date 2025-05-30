package com.petreca.Gwent_Catalog.repository;

import com.petreca.Gwent_Catalog.model.Card;
import com.petreca.Gwent_Catalog.model.Faction;
import com.petreca.Gwent_Catalog.model.Rarity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
/*
Permite que o Spring trate exceções específicas de persistência e faça a injeção automática desse componente onde necessário.
 */
public interface CardRepository extends JpaRepository<Card, Long>, JpaSpecificationExecutor<Card>{
    /*
    JpaRepository<Card, Long> herda métodos prontos para operações CRUD (create, read, update, delete) sobre a entidade Card, com chave primária do tipo Long.

    O Spring Data JPA gera automaticamente a implementação dessas operações.
     */

    //Busca por nome (case-insensitive)
    List<Card> findByNameContainingIgnoreCase(String name);

    //Busca por facção
    List<Card> findByFaction(Faction faction);

    //Busca por raridade
    List<Card> findByRarity(Rarity rarity);

    //Busca por poder mínimo igual ao valor informado
    List<Card> findByPowerGreaterThanEqual(Integer minPower);

    //Busca customizada - cartas por facção e raridade
    @Query("SELECT c FROM Card c WHERE c.faction = :faction AND c.rarity = :rarity")
    /*
    Consultas personalizada diretamente da interface de repositório, usando JPQL (Java Persistence Query Language) ou SQL nativo.

    Os parâmetros da consulta são precedidos por dois pontos, :faction e :rarity.
     */
    List<Card> findByFactionAndRarity(@Param("faction") Faction faction,
                                      @Param("rarity") Rarity rarity);
    /*
    Mapear os parâmetros do metodo Java aos parâmetros nomeados na consulta JPQL ou SQL definida no @Query, garantindo que os valores corretos sejam usados.
     */

    //Busca a carta pelo nome
    Optional<Card> findByName(String name);
    /*
    Permite tratar o caso em que a carta não existe (evita NullPointerException).
     */

    //Busca as cartas por ordem de poder
    @Query("SELECT c FROM Card c ORDER BY c.power DESC")
    List<Card> FindByPowerfulCards();
}
