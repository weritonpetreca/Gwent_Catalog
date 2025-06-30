package com.petreca.gwent_catalog.repository;

import com.petreca.gwent_catalog.model.Card;
import com.petreca.gwent_catalog.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    List<Favorite> findByUserNameOrderByAddedAtDesc(String userName);

    Optional<Favorite> findByCardAndUserName(Card card, String userName);

    long countByUserName(String userName);

    void deleteByCardAndUserName(Card card, String userName);
}
