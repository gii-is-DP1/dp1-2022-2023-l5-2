package org.springframework.samples.bossmonster.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.bossmonster.game.card.Card;
import org.springframework.samples.bossmonster.game.card.CardRepository;
import org.springframework.samples.bossmonster.game.card.CardService;
import org.springframework.samples.bossmonster.game.card.TreasureType;
import org.springframework.samples.bossmonster.game.card.hero.HeroCard;
import org.springframework.samples.bossmonster.game.card.room.RoomCard;
import org.springframework.samples.bossmonster.game.gameState.GamePhase;
import org.springframework.samples.bossmonster.game.player.Player;
import org.springframework.samples.bossmonster.game.player.PlayerService;
import org.springframework.samples.bossmonster.gameLobby.GameLobby;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameService {

    GameRepository repo;
    PlayerService playerService;
    CardService cardService;
    GameBuilder gameBuilder;

    private static final Integer PLAYER_HAND_CARD_LIMIT = 5;

    @Autowired
    public GameService(GameRepository repo, PlayerService playerService, GameBuilder gameBuilder) {
        this.repo=repo;
        this.playerService=playerService;
        this.gameBuilder=gameBuilder;
    }

    public Game saveGame(Game g) {
        return repo.save(g);
    }

    public Game createNewGameFromLobby(GameLobby lobby) {
        Game newGame = gameBuilder.buildNewGame(lobby);
        repo.save(newGame);
        return newGame;
    }

    public Optional<Game> findGame(Integer id) {
        return repo.findById(id);
    }

    public List<Game> findAllGames() {return repo.findAll();}

    ////////////////////////   FUNCIONES DENTRO DEL JUEGO   ////////////////////////

    public void processTurn(GamePhase phase, Integer currentPlayer) {
        switch (phase) {
            case START_GAME:  processStartGameTurn();  break;
            case START_ROUND: processStartRoundTurn(); break;
            case BUILD:       processBuildRound();     break;
            case LURE:        processLureRound();      break;
            case ADVENTURE:   processAdventureRound(); break;
        }
    }

    public void processStartGameTurn() {
        
    }

    public void processStartRoundTurn() {
        // 1): Se revelan X héroes en la ciudad, siendo X el numero de jugadores.
        // 2): Cada jugador roba una carta de habitación
    }

    public void processBuildRound() {
        // 1): Construir una habitación si el jugador quiere.
        // 2): Usar una carta de hechizo de construccion si el jugador quiere.
        // 3): Revelar salas de hechizo cuando todos los jugadores terminen el turno
    }

    public void processLureRound() {
        // 1): Mover los heroes de la ciudad a su mejor mazmorra si no hay empate
    }

    public void processAdventureRound() {
        // 1): Cada heroe de cada mazmorra avanza. (1 sala o todas las salas?)
    }

    ////////////////////////////   AUXILIAR FUNCTIONS   ////////////////////////////



}
