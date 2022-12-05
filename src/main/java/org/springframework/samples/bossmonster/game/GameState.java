package org.springframework.samples.bossmonster.game;

import javax.persistence.Entity;

import org.springframework.samples.bossmonster.game.gamePhase.GamePhase;
import org.springframework.samples.bossmonster.game.gamePhase.GameSubPhase;
import org.springframework.samples.bossmonster.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class GameState extends BaseEntity {
    
    private GamePhase phase;
    private GameSubPhase subPhase;
    private Integer currentPlayer;
    private Integer totalPlayers;

    public void updateGameState() {
        switch (phase) {
            case START_GAME: updateStartGameState(); break;
            case START_ROUND: updateStartRoundState(); break;
            case BUILD: updateBuildState(); break;
            case LURE: updateLureState(); break;
            case ADVENTURE: updateAdventureState(); break;
        }
    }

    public void updateStartGameState() {
        switch (subPhase) {
            case ANNOUNCE_NEW_PHASE: subPhase = GameSubPhase.ANNOUNCE_NEW_PLAYER; break;
            case ANNOUNCE_NEW_PLAYER: subPhase = GameSubPhase.DISCARD_2_STARTING_CARDS; break;
            case DISCARD_2_STARTING_CARDS: subPhase = GameSubPhase.PLACE_FIRST_ROOM; break;
            case PLACE_FIRST_ROOM: {
                currentPlayer ++;
                if (currentPlayer < totalPlayers) { subPhase = GameSubPhase.ANNOUNCE_NEW_PLAYER; }
                else {
                    phase = GamePhase.START_ROUND;
                    subPhase = GameSubPhase.ANNOUNCE_NEW_PHASE;
                    currentPlayer = 0;
                }
                break;
            }
            //default: subPhase = GameSubPhase.ANNOUNCE_NEW_PLAYER; break;
        }
    }

    public void updateStartRoundState() {
        switch (subPhase) {
            case ANNOUNCE_NEW_PHASE: subPhase = GameSubPhase.REVEAL_HEROES; break;
            case REVEAL_HEROES: subPhase = GameSubPhase.ANNOUNCE_NEW_PLAYER; break;
            case ANNOUNCE_NEW_PLAYER: subPhase = GameSubPhase.GET_ROOM_CARD; break;
            case GET_ROOM_CARD: {
                currentPlayer ++;
                if (currentPlayer < totalPlayers) { subPhase = GameSubPhase.ANNOUNCE_NEW_PLAYER; }
                else {
                    phase = GamePhase.BUILD;
                    subPhase = GameSubPhase.ANNOUNCE_NEW_PHASE;
                    currentPlayer = 0;
                }
                break;
            }
            //default: subPhase = GameSubPhase.ANNOUNCE_NEW_PLAYER; break;
        }
    }

    public void updateBuildState() {
        switch (subPhase) {
            case ANNOUNCE_NEW_PHASE: subPhase = GameSubPhase.ANNOUNCE_NEW_PLAYER; break;
            case ANNOUNCE_NEW_PLAYER: subPhase = GameSubPhase.BUILD_NEW_ROOM; break;
            case BUILD_NEW_ROOM: subPhase = GameSubPhase.USE_SPELLCARD; break;
            case USE_SPELLCARD: {
                currentPlayer ++;
                if (currentPlayer < totalPlayers) { subPhase = GameSubPhase.ANNOUNCE_NEW_PLAYER; }
                else {
                    subPhase = GameSubPhase.REVEAL_NEW_ROOMS;
                    currentPlayer = 0;
                }
                break;
            }
            case REVEAL_NEW_ROOMS: {
                phase = GamePhase.LURE;
                subPhase = GameSubPhase.ANNOUNCE_NEW_PHASE;
                break;
            }
        }
    }

    public void updateLureState() {
        switch (subPhase) {
            case ANNOUNCE_NEW_PHASE: subPhase = GameSubPhase.HEROES_ENTER_DUNGEON; break;
            case HEROES_ENTER_DUNGEON: {
                phase = GamePhase.ADVENTURE;
                subPhase = GameSubPhase.ANNOUNCE_NEW_PHASE;
                break;
            }
        }
    }

    public void updateAdventureState() {
        switch (subPhase) {
            case ANNOUNCE_NEW_PHASE: subPhase = GameSubPhase.ANNOUNCE_NEW_PLAYER; break;
            case ANNOUNCE_NEW_PLAYER: subPhase = GameSubPhase.HEROES_EXPLORE_DUNGEON; break;
            case HEROES_EXPLORE_DUNGEON: subPhase = GameSubPhase.USE_SPELLCARD; break;
            case USE_SPELLCARD: {
                currentPlayer ++;
                if (currentPlayer < totalPlayers) { subPhase = GameSubPhase.ANNOUNCE_NEW_PLAYER; }
                else {
                    phase = GamePhase.START_ROUND;
                    subPhase = GameSubPhase.ANNOUNCE_NEW_PHASE;
                    currentPlayer = 0;
                }
            }
        }
    }

}
