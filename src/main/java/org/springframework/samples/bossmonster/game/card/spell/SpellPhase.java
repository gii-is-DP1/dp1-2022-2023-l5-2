package org.springframework.samples.bossmonster.game.card.spell;

import lombok.Getter;
import org.springframework.samples.bossmonster.game.gameState.GamePhase;

import java.util.List;

@Getter
public enum SpellPhase {
    constructionPhase(List.of(GamePhase.BUILD)),
    adventurePhase(List.of(GamePhase.ADVENTURE)),
    adventureAndConstructionPhase(List.of(GamePhase.ADVENTURE,GamePhase.BUILD));

    List<GamePhase> triggerPhases;

    SpellPhase(List<GamePhase> phases) {
       this.triggerPhases=phases;
    }
}

