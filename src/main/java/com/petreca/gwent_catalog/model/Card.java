package com.petreca.gwent_catalog.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
/*
Indica que esta classe é uma entidade JPA, ou seja, será mapeada para uma tabela no banco de dados.
Cada instância desta classe representa uma linha da tabela.
Necessário para que o framework de persistência (JPA/Hibernate) gerencie os dados desta classe.
 */
@Table(name = "cards") // Personalizar o nome da tabela.
@Data // lombok: gera getters, setters, toString, equals, hashCode
@NoArgsConstructor
/*
Gera automaticamente um construtor sem argumentos, necessário para que frameworks (como JPA/Hibernate) consigam instanciá-la automaticamente.
 */
@AllArgsConstructor //Cria um construtor com todos os campos da classe como parâmetros.
public class Card {

    @Id //Marca o campo como chave primária da tabela.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Faz com que o valor desse campo seja gerado automaticamente pelo banco, geralmente como auto-incremento.
    private Long id;

    @Column(nullable = false) // Adiciona a restrição NOT NULL à coluna desse campo.
    private String name;

    private Integer power;

    private Integer provision;

    @Enumerated(EnumType.STRING) // O campo é um enum, e será salvo como texto no banco.
    @Column(nullable = false)
    private Faction faction;

    @Enumerated(EnumType.STRING)
    private Rarity rarity;

    private String type;

    @Column(length = 1000) //Define que a coluna terá tamanho máximo de 1000 caracteres, garantindo que o banco aceite textos maiores do que o padrão (255).
    private String ability;

    @ElementCollection(fetch = FetchType.EAGER) //Indica que o campo é uma coleção de elementos simples (tipos primitivos ou Strings) ou de objetos Embeddable.
    @CollectionTable(name = "card_categories",
            joinColumns = @JoinColumn(name = "card_id"))
    /*
    @CollectionTable Define o nome da tabela que será criada para armazenar a coleção.

    joinColumns = @JoinColumn(name = "card_id") define a coluna que fará a ligação (chave estrangeira) entre a tabela da entidade principal (cards) e a tabela da coleção (card_categories).

    Permite armazenar uma lista de Strings (ou outro tipo simples) relacionada a uma entidade, numa tabela separada, sem precisar criar uma entidade específica para isso.
     */
    @Column(name = "category")
    private List<String> categories;

    @Column(length = 1000)
    private String flavor;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist // Deve ser executado automaticamente antes de o objeto ser salvo (persistido) pela primeira vez no banco de dados.
    protected void onCreate(){
        createdAt = LocalDateTime.now();
    }
}
