package org.springframework.samples.bossmonster.game.gameState;

import java.time.Duration;
import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.extern.slf4j.Slf4j;
import org.jpatterns.gof.StrategyPattern;
import org.springframework.samples.bossmonster.game.Game;
import org.springframework.samples.bossmonster.game.card.room.RoomPassiveTrigger;
import org.springframework.samples.bossmonster.game.player.Player;
import org.springframework.samples.bossmonster.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Slf4j
@StrategyPattern.Context
public class GameState extends BaseEntity {

    public static final int DEFAULT_WAITING_TIME = 2;
    @Enumerated(EnumType.STRING)
    private GamePhase phase;
    @Enumerated(EnumType.STRING)
    private GameSubPhase subPhase;
    private Integer currentPlayer;
    private Integer totalPlayers;
    @OneToOne(cascade = CascadeType.ALL)
    private Game game;

    // Used to count player actions
    private Integer counter;
    // If counter reaches this value, the state updates if checkClock is false
    private Integer actionLimit;
    // Used to control time based aspects of the game
    private LocalDateTime clock;
    // If true, game updates when clock matches current time. If false, when counter matches limit
    private Boolean checkClock;

    private Integer currentRound;

    // These variables are used to store the current state when a card effect needs a special state
    @Enumerated(EnumType.STRING)
    private GamePhase phaseBeforeEffect;
    @Enumerated(EnumType.STRING)
    private GameSubPhase subPhaseBeforeEffect;
    private Integer counterBeforeEffect;
    private Integer actionLimitBeforeEffect;
    private LocalDateTime clockBeforeEffect;
    private Boolean checkClockBeforeEffect;

    private Boolean effectIsBeingTriggered;

    private static final Integer START_GAME_DISCARDED_CARDS = 2;
    private static final Integer START_GAME_ROOMS_PLACED = 1;
    private static final Integer BUILD_PHASE_BUILDED_ROOMS_LIMIT = 1;   // If a card effect changes the limit, this value will update automatically
    private static final Integer EFFECT_STATE_COUNTER_LIMIT = 1;        // All special card effects have only one action
    private static final Integer BUILD_ROOM_ACTIONS = 2;                // Choosing a card + Choosing a dungeon slot
    private static final Integer PHASE_COOLDOWN_SECONDS = 3;
    private static final Integer PLAYER_COOLDOWN_SECONDS = 1;
    private static final Integer SHOW_HEROES_COOLDOWN_SECONDS = 3;
    private static final Integer SHOW_NEW_ROOMCARD_COOLDOWN_SECONDS = 3;
    private static final Integer SHOW_ROOMS_COOLDOWN_SECONDS = 3;

    private static final Integer PLAYER_HAND_CARD_LIMIT = 5;

    ////////////////////////////   CHANGE STATE   ////////////////////////////

    private void updateChangeConditionClock(Integer seconds) {
        checkClock = true;
        clock = LocalDateTime.now().plusSeconds(seconds);
    }

    public void updateChangeConditionCounter(Integer newLimit) {
        checkClock = false;
        counter = 0;
        actionLimit = newLimit;
    }


    public void checkStateStatus() {
        if ( (checkClock == false && counter >= actionLimit) ||
             (checkClock == true && clock.isBefore(LocalDateTime.now())) )
        { log.debug("Game update condition met, updating...");
            updateGameState();
            log.debug("Subphase is now "+ getSubPhase());
        }
    }

    private void updateGameState() {
        switch (phase) {
            case START_GAME:  updateStartGameState(); break;
            case START_ROUND: updateStartRoundState(); break;
            case BUILD:       updateBuildState(); break;
            case LURE:        updateLureState(); break;
            case ADVENTURE:   updateAdventureState(); break;
            case END_GAME:    break;
            case EFFECT:      rollbackPreEffectState(); break;
        }
        if(getSubPhase().hasToCheckClock())
            updateChangeConditionClock(getSubPhase().getClockLimit());
        else
            updateChangeConditionCounter(getSubPhase().getActionLimit());
    }

    public void triggerSpecialCardEffectState(GameSubPhase triggeredSubPhase) {
        log.debug("State Flow Interrumpted by card effect");
        phaseBeforeEffect = phase;
        subPhaseBeforeEffect = subPhase;
        counterBeforeEffect = counter;
        actionLimitBeforeEffect = actionLimit;
        clockBeforeEffect = clock;
        checkClockBeforeEffect = checkClock;
        phase = GamePhase.EFFECT;
        subPhase = triggeredSubPhase;
        updateChangeConditionCounter(triggeredSubPhase.getActionLimit());
    }

    public Integer getWaitingTime() {
        int time = (int) Duration.between(LocalDateTime.now(), clock).getSeconds() + 1;
        return Integer.max(time,DEFAULT_WAITING_TIME);
    }

    public void getFirstNonEliminatedPlayer() {
        currentPlayer = -1;
        Boolean detected = false;
        while (!detected) {
            currentPlayer ++;
            if ((currentPlayer == game.getPlayers().size()) || !game.getPlayers().get(currentPlayer).isDead()) detected = true;
        }
    }

    public void advanceCurrentPlayer() {
        Boolean changed = false;
        while (!changed) {
            currentPlayer ++;
            Boolean allPlayersTurnDone;
            Boolean playerIsNotEliminated;
            allPlayersTurnDone = (currentPlayer == game.getPlayers().size());

            if (allPlayersTurnDone) playerIsNotEliminated = false;
            else playerIsNotEliminated = !game.getPlayers().get(currentPlayer).isDead();

            if (!playerIsNotEliminated && !allPlayersTurnDone) log.debug(String.format("Player %s skipped", game.getPlayers().get(currentPlayer).getUser().getNickname()));
            if (allPlayersTurnDone || playerIsNotEliminated) changed = true;
        }
    }

    ////////////////////////////   COMMON STATE CHANGES   ////////////////////////////

    public void changePhase(GamePhase newPhase) {
        phase = newPhase;
        subPhase = GameSubPhase.ANNOUNCE_NEW_PHASE;
        updateChangeConditionClock(PHASE_COOLDOWN_SECONDS);
        getFirstNonEliminatedPlayer();
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
                break;
            }
            case ANNOUNCE_NEW_PLAYER: {
                subPhase = GameSubPhase.DISCARD_2_STARTING_CARDS;
                break;
            }
            case DISCARD_2_STARTING_CARDS: {
                subPhase = GameSubPhase.PLACE_FIRST_ROOM;
                break;
            }
            case PLACE_FIRST_ROOM: {
                advanceCurrentPlayer();
                if (currentPlayer < totalPlayers) { announcePlayerTurn(); }
                else {
                    changePhase(GamePhase.START_ROUND);
                    game.revealAllDungeonRooms();
                }
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
                game.placeHeroInCity();
                break;
            }
            case REVEAL_HEROES: {
                subPhase = GameSubPhase.GET_ROOM_CARD;
                for (Player player: game.getPlayers()) {
                    if (player.getHand().size() < PLAYER_HAND_CARD_LIMIT && !player.isDead()) game.getNewRoomCard(player);
                }
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
                break;
            }
            case ANNOUNCE_NEW_PLAYER: {
                subPhase = GameSubPhase.BUILD_NEW_ROOM;
                break;
            }
            case BUILD_NEW_ROOM: {
                game.getCurrentPlayer().getDungeon().setInitialRoomCardDamage();
                subPhase = GameSubPhase.USE_SPELLCARD;
                break;
            }
            case USE_SPELLCARD: {
                advanceCurrentPlayer();
                if (currentPlayer < totalPlayers) { announcePlayerTurn(); }
                else {
                    subPhase = GameSubPhase.REVEAL_NEW_ROOMS;
                    game.revealAllDungeonRooms();
                    setCurrentPlayer(0);
                }
                break;
            }
            case REVEAL_NEW_ROOMS: {
                changePhase(GamePhase.LURE);
                for(Player player: game.getPlayers()) {
                    for(int index = 0; index<player.getDungeon().getBuiltRooms();index++) {
                        game.tryTriggerRoomCardEffect(RoomPassiveTrigger.ADD_EXTRA_ROOM_DAMAGE,player,index);
                    }
                }
                break;
            }
        }
    }

    ////////////////////////////   LURE   ////////////////////////////

    private void updateLureState() {
        switch (subPhase) {
            case ANNOUNCE_NEW_PHASE: {
                subPhase = GameSubPhase.HEROES_ENTER_DUNGEON;
                game.lureHeroToBestDungeon();
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
                break;
            }
            case ANNOUNCE_NEW_PLAYER: {
                subPhase = GameSubPhase.HEROES_EXPLORE_DUNGEON;
                game.processAdventurePhase(game.getCurrentPlayer());
                break;
            }
            case HEROES_EXPLORE_DUNGEON: {
                subPhase = GameSubPhase.USE_SPELLCARD;
                // There is no limit here, the current player chooses when this phase ends
                break;
            }
            case USE_SPELLCARD: {
                advanceCurrentPlayer();
                if (currentPlayer < totalPlayers) { announcePlayerTurn(); }
                else {
                    if (game.checkGameEnded()) changePhase(GamePhase.END_GAME);
                    else {
                        currentRound ++;
                        for (Player p: game.getPlayers()) p.getDungeon().setJackpotStashEffectActivated(false);
                        changePhase(GamePhase.START_ROUND);
                    }
                }
                break;
            }
        }
    }

    ////////////////////////////   END GAME   ////////////////////////////

    // TODO

    ////////////////////////////   EFFECT GAME   ////////////////////////////

    private void rollbackPreEffectState() {
        log.debug("Card Effect State Ended. Returning to previous state...");
        phase = phaseBeforeEffect;
        subPhase = subPhaseBeforeEffect;
        counter = counterBeforeEffect;
        actionLimit = actionLimitBeforeEffect;
        clock = clockBeforeEffect;
        checkClock = checkClockBeforeEffect;
        checkStateStatus();
    }

}
