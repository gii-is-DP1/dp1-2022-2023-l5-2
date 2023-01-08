package org.springframework.samples.bossmonster.game.gameState;

import org.jpatterns.gof.StrategyPattern;
import org.springframework.samples.bossmonster.game.Game;
import org.springframework.samples.bossmonster.game.card.Card;

import java.util.List;

@StrategyPattern.Strategy
public interface SubPhaseChoices {
    default List<Card> getChoice(Game game) {
        return null;
    }

    default void makeChoice(Game game, int choice) {}

    default Boolean isValidChoice(int choice, Game game) {
        return false;
    }

    default Boolean isOptional() {return true;}

    default Integer getActionLimit() {return 1;}

    default Integer getClockLimit() {return null;};

    default boolean canDecrementCounter() {return false;};
}
