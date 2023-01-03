package org.springframework.samples.bossmonster.game.card;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CardRepository<T extends Card> extends CrudRepository<T, Integer> {
    List<T> findAll();
}
