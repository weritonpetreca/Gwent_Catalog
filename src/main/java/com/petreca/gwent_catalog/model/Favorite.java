package com.petreca.gwent_catalog.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "favorites")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @Column(name = "user_name")
    private String userName; //Para multi-usuários

    @Column(name = "added_at")
    private LocalDateTime addedAt;

    private String notes; //Anotações pessoais sobre a carta

    @PrePersist
    protected void onCreate() {
        addedAt = LocalDateTime.now();
        if (userName == null) {
            userName = "default";
        }
    }
}
