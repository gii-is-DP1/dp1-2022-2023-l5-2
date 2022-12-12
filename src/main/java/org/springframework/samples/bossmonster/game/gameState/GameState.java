package org.springframework.samples.bossmonster.game.gameState;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.extern.slf4j.Slf4j;
import org.springframework.samples.bossmonster.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Slf4j
public class GameState extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private GamePhase phase;
    @Enumerated(EnumType.STRING)
    private GameSubPhase subPhase;
    private Integer currentPlayer;
    private Integer totalPlayers;

    // Used to count player actions
    private Integer counter;
    // If counter reaches this value, the state updates if checkClock is false
    private Integer actionLimit;
    // Used to control time based aspects of the game
    private LocalDateTime clock;
    // If true, game updates when clock matches current time. If false, when counter matches limit
    private Boolean checkClock;

    private static final Integer START_GAME_DISCARDED_CARDS = 2;
    private static final Integer START_GAME_ROOMS_PLACED = 1;
    private static final Integer NEW_ROUND_GIVEN_ROOM_CARDS = 1;
    private static final Integer BUILD_PHASE_BUILDED_ROOMS_LIMIT = 1;

    private static final Integer PHASE_COOLDOWN_SECONDS = 5;
    private static final Integer PLAYER_COOLDOWN_SECONDS = 3;
    private static final Integer SHOW_HEROES_COOLDOWN_SECONDS = 5;
    private static final Integer SHOW_NEW_ROOMCARD_COOLDOWN_SECONDS = 5;
    private static final Integer SHOW_ROOMS_COOLDOWN_SECONDS = 5;

    ////////////////////////////   CHANGE STATE   ////////////////////////////

    private void updateChangeConditionClock(Integer seconds) {
        checkClock = true;
        clock = LocalDateTime.now().plusSeconds(seconds);
    }

    private void updateChangeConditionCounter(Integer newLimit) {
        checkClock = false;
        counter = 0;
        actionLimit = newLimit;
    }

    public void checkStateStatus() {
        log.debug("Checking game status..." );
        log.debug("Clock time until update: " + clock.compareTo(LocalDateTime.now()));
        if ( (checkClock == false && counter >= actionLimit) ||
             (checkClock == true && clock.isBefore(LocalDateTime.now())) )
        { log.debug("Game update condition met, updating...");
            updateGameState(); }
    }

    private void updateGameState() {
        switch (phase) {
            case START_GAME: updateStartGameState(); break;
            case START_ROUND: updateStartRoundState(); break;
            case BUILD: updateBuildState(); break;
            case LURE: updateLureState(); break;
            case ADVENTURE: updateAdventureState(); break;
            case END_GAME: break;
        }
    }

    ////////////////////////////   COMMON STATE CHANGES   ////////////////////////////

    private void changePhase(GamePhase newPhase) {
        phase = newPhase;
        subPhase = GameSubPhase.ANNOUNCE_NEW_PHASE;
        updateChangeConditionClock(PHASE_COOLDOWN_SECONDS);
        currentPlayer = 0;
    }

    private void announcePlayerTurn() {
        subPhase = GameSubPhase.ANNOUNCE_NEW_PLAYER;
        updateChangeConditionClock(PLAYER_COOLDOWN_SECONDS);
    }

    ////////////////////////////   START GAME   ////////////////////////////

    private void updateStartGameState() {
        switch (subPhase) {
            case ANNOUNCE_NEW_PHASE: {
                subPhase = GameSubPhase.ANNOUNCE_NEW_PLAYER;
                updateChangeConditionClock(PLAYER_COOLDOWN_SECONDS);
                break;
            }
            case ANNOUNCE_NEW_PLAYER: {
                subPhase = GameSubPhase.DISCARD_2_STARTING_CARDS;
                updateChangeConditionCounter(START_GAME_DISCARDED_CARDS);
                break;
            }
            case DISCARD_2_STARTING_CARDS: {
                subPhase = GameSubPhase.PLACE_FIRST_ROOM;
                updateChangeConditionCounter(START_GAME_ROOMS_PLACED);
                break;
            }
            case PLACE_FIRST_ROOM: {
                currentPlayer ++;
                if (currentPlayer < totalPlayers) { announcePlayerTurn(); }
                else { changePhase(GamePhase.START_ROUND); }
                break;
            }
        }
        log.debug("Updated start subphase to "+getSubPhase());
    }

    ////////////////////////////   START ROUND   ////////////////////////////

    private void updateStartRoundState() {
        switch (subPhase) {
            case ANNOUNCE_NEW_PHASE: {
                subPhase = GameSubPhase.REVEAL_HEROES;
                updateChangeConditionClock(SHOW_HEROES_COOLDOWN_SECONDS);
                break;
            }
            case REVEAL_HEROES: {
                subPhase = GameSubPhase.GET_ROOM_CARD;
                updateChangeConditionClock(SHOW_NEW_ROOMCARD_COOLDOWN_SECONDS);
                break;
            }
            case GET_ROOM_CARD: {
                changePhase(GamePhase.BUILD);
                break;
            }
        }
    }

    ////////////////////////////   BUILD   ////////////////////////////

    private void updateBuildState() {
        switch (subPhase) {
            case ANNOUNCE_NEW_PHASE: {
                subPhase = GameSubPhase.ANNOUNCE_NEW_PLAYER;
                updateChangeConditionClock(PLAYER_COOLDOWN_SECONDS);
                break;
            }
            case ANNOUNCE_NEW_PLAYER: {
                subPhase = GameSubPhase.BUILD_NEW_ROOM;
                updateChangeConditionCounter(BUILD_PHASE_BUILDED_ROOMS_LIMIT);
                break;
            }
            case BUILD_NEW_ROOM: {
                subPhase = GameSubPhase.USE_SPELLCARD;
                // There is no limit here, the current player chooses when this phase ends
                updateChangeConditionCounter(1);
                break;
            }
            case USE_SPELLCARD: {
                currentPlayer ++;
                if (currentPlayer < totalPlayers) { announcePlayerTurn(); }
                else {
                    subPhase = GameSubPhase.REVEAL_NEW_ROOMS;
                    updateChangeConditionClock(SHOW_ROOMS_COOLDOWN_SECONDS);
                }
                break;
            }
            case REVEAL_NEW_ROOMS: {
                changePhase(GamePhase.LURE);
                break;
            }
        }
    }

    ////////////////////////////   LURE   ////////////////////////////

    private void updateLureState() {
        switch (subPhase) {
            case ANNOUNCE_NEW_PHASE: {
                subPhase = GameSubPhase.HEROES_ENTER_DUNGEON;
                // TODO Ni idea de como poner esto ahora mismo
                break;
            }
            case HEROES_ENTER_DUNGEON: {
                changePhase(GamePhase.ADVENTURE);
                break;
            }
        }
    }

    ////////////////////////////   ADVENTURE   ////////////////////////////

    private void updateAdventureState() {
        switch (subPhase) {
            case ANNOUNCE_NEW_PHASE: {
                subPhase = GameSubPhase.ANNOUNCE_NEW_PLAYER;
                updateChangeConditionClock(PLAYER_COOLDOWN_SECONDS);
                break;
            }
            case ANNOUNCE_NEW_PLAYER: {
                subPhase = GameSubPhase.HEROES_EXPLORE_DUNGEON;
                // TODO Ni idea de como poner esto ahora mismo
                break;
            }
            case HEROES_EXPLORE_DUNGEON: {
                subPhase = GameSubPhase.USE_SPELLCARD;
                // There is no limit here, the current player chooses when this phase ends
                updateChangeConditionCounter(1);
                break;
            }
            case USE_SPELLCARD: {
                currentPlayer ++;
                if (currentPlayer < totalPlayers) { announcePlayerTurn(); }
                else { changePhase(GamePhase.START_ROUND); } // TODO Ver si alguien ha ganado el juego
                break;
            }
        }
    }

    ////////////////////////////   END GAME   ////////////////////////////

    // TODO

}
