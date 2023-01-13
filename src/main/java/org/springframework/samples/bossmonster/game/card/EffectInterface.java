package org.springframework.samples.bossmonster.game.card;

import org.jpatterns.gof.StrategyPattern;
import org.springframework.samples.bossmonster.game.Game;
import org.springframework.samples.bossmonster.game.player.Player;

@StrategyPattern.Strategy
public interface EffectInterface {

    void apply(Player player, Integer dungeonPosition, Game game);
    
}
