package org.springframework.samples.bossmonster.game.gameState;

import lombok.Getter;

@Getter
public enum GamePhase {
    START_GAME("Start of Game","Time to start the game!"),
    START_ROUND("Start of Round","Starting a new round!"),
    BUILD("Build Phase", "Time to build rooms!"),
    LURE("Lure Phase", "The heroes are looking for a place to explore!"),
    ADVENTURE("Adventure Phase","The heroes prepare to advance!"),
    END_GAME("End of game","The game has finished!"),
    EFFECT("Someone interrupts with a spell!","");

    String startPhaseMessage;
    String displayName;

    GamePhase(String displayName, String startPhaseMessage) {
        this.displayName = displayName;
        this.startPhaseMessage = startPhaseMessage;
    }
}


