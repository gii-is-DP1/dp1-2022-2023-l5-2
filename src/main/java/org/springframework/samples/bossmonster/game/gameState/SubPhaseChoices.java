package org.springframework.samples.bossmonster.game.gameState;

import org.springframework.samples.bossmonster.game.Game;
import org.springframework.samples.bossmonster.game.card.Card;

import java.util.List;

public interface SubPhaseChoices {
    default List<Card> getChoice(Game game) {
        return null;
    }

    default void makeChoice(Game game, Integer choice) {}

    default Boolean isValidChoice(Integer choice, Game game) {
        return false;
    }

    default Boolean isOptional() {return true;}

    default Integer getActionLimit() {return 1;}

    default Integer getClockLimit() {return null;};
}
