package org.springframework.samples.bossmonster.game.gameState;

import lombok.Getter;
import org.springframework.samples.bossmonster.game.Game;

import java.util.function.Function;

@Getter
public enum GameSubPhase {

    // COMMON
    ANNOUNCE_NEW_PHASE(g->g.getState().getPhase().getStartPhaseMessage()),
    ANNOUNCE_NEW_PLAYER(g->String.format("It is now %s's turn!",g.getCurrentPlayer())),
    USE_SPELLCARD(g->String.format("%s considers their spells...",g.getCurrentPlayer()),
        "Choose a spell to activate"),


    // START_GAME
    DISCARD_2_STARTING_CARDS(g->String.format("%s is discarding their cards...",g.getCurrentPlayer()),
        "Choose a card to discard"),
    PLACE_FIRST_ROOM(g->String.format("%s is building their first room...",g.getCurrentPlayer()),
        "Choose a room to build"),

    // START_ROUND
    REVEAL_HEROES(g->"New heroes arrived at the city!"),
    GET_ROOM_CARD(g->"The Bosses get a new room to build..."),

    // BUILD
    BUILD_NEW_ROOM(g->String.format("%s is considering building a room...",g.getCurrentPlayer()),
        "Choose a room to build and its place in your dungeon"),
    REVEAL_NEW_ROOMS(g->"The newly built rooms get revealed!"),

    // LURE
    HEROES_ENTER_DUNGEON(g->"The heroes enter the dungeons!"),

    // ADVENTURE
    HEROES_EXPLORE_DUNGEON(g->String.format("The heroes advance through %s's Dungeon!",g.getCurrentPlayer())),

    // EFFECT
    CHOOSE_A_CARD_FROM_DISCARD_PILE(g->String.format("%s is getting a card from the discard pile...",g.getCurrentPlayer()),
        "Choose a card to add to your hand"),
    CHOOSE_A_ROOM_CARD_FROM_DISCARD_PILE(g->String.format("%s is getting a room from the discard pile...",g.getCurrentPlayer()),
        "Choose a room to add to your hand"),
    CHOOSE_A_MONSTER_ROOM_CARD_FROM_DISCARD_PILE(g->String.format("%s is hetting a monster room from the discard pile...",g.getCurrentPlayer()),
        "Choose a monster room to add to your hand"),
    DISCARD_A_SPELL_CARD(g->String.format("%s is choosing a spell to discard...",g.getCurrentPlayer()),
        "Choose a spell to discard");

    Function<Game, String> contextualMessage;
    String choiceMessage;

    GameSubPhase(Function<Game, String> contextualMessage, String choiceMessage) {
        this.contextualMessage = contextualMessage;
        this.choiceMessage = choiceMessage;
    }

    GameSubPhase(Function<Game, String> contextualMessage) {
        this.contextualMessage = contextualMessage;
    }
}
