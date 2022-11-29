package org.springframework.samples.bossmonster.game.card;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CardRepository<T extends Card> extends CrudRepository<T, Integer> {
    List<T> findAll();
}
