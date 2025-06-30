package com.petreca.gwent_catalog.service;

import com.petreca.gwent_catalog.model.Card;
import com.petreca.gwent_catalog.model.Favorite;
import com.petreca.gwent_catalog.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private static final String DEFAULT_USER = "default";

    public Favorite addToFavorites(Card card, String notes) {
         //Verifica se já está nos favoritos
        Optional<Favorite> existing = favoriteRepository.findByCardAndUserName(card, DEFAULT_USER);
        if (existing.isPresent()) {
            log.warn("Carta {} já está nos favoritos.", card.getName());
            return existing.get();
        }

        Favorite favorite = new Favorite();
        favorite.setCard(card);
        favorite.setUserName(DEFAULT_USER);
        favorite.setNotes(notes);

        Favorite saved = favoriteRepository.save(favorite);
        log.info("Carta {} adicionada aos favoritos.", card.getName());
        return saved;
    }

    public void removeFromFavorites(Card card) {
        favoriteRepository.findByCardAndUserName(card, DEFAULT_USER)
                .ifPresent(favorite -> {
                    favoriteRepository.delete(favorite);
                    log.info("Carta {} removida dos favoritos.", card.getName());
                });
    }

    public List<Favorite> getAllFavorites() {
        return favoriteRepository.findByUserNameOrderByAddedAtDesc(DEFAULT_USER);
    }

    public boolean isFavorite(Card card) {
        return favoriteRepository.findByCardAndUserName(card, DEFAULT_USER)
                .isPresent();
    }

    public long getTotalFavorites() {
        return favoriteRepository.countByUserName(DEFAULT_USER);
    }

    public void updateNotes(Card card, String newNotes) {
        favoriteRepository.findByCardAndUserName(card, DEFAULT_USER)
                .ifPresent(favorite -> {
                    favorite.setNotes(newNotes);
                    favoriteRepository.save(favorite);
                    log.info("Notas atualizadas para a carta {}.",
                            card.getName());
                });
    }
}
