package org.springframework.samples.bossmonster.game.gameState;

public enum GameSubPhase {

    // COMMON
    ANNOUNCE_NEW_PHASE, ANNOUNCE_NEW_PLAYER, USE_SPELLCARD,

    // START_GAME
    DISCARD_2_STARTING_CARDS, PLACE_FIRST_ROOM,

    // START_ROUND
    REVEAL_HEROES, GET_ROOM_CARD,

    // BUILD
    BUILD_NEW_ROOM, REVEAL_NEW_ROOMS,

    // LURE
    HEROES_ENTER_DUNGEON,

    // ADVENTURE
    HEROES_EXPLORE_DUNGEON


}