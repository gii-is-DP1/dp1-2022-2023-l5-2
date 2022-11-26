package org.springframework.samples.bossmonster.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.bossmonster.game.gamePhase.GamePhase;
import org.springframework.samples.bossmonster.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/games")
public class GameController {

    GameService gameService;
    UserService userService;

    @Autowired
    public GameController(GameService service, UserService userService) {
        this.gameService = service;
        this.userService = userService;}

    @GetMapping("/{gameId}")
    public ModelAndView joinGame(@PathVariable Integer gameId) {

        ModelAndView result=new ModelAndView();
        Game game = gameService.findGame(gameId).get();
        // TODO: CREATE GAME-RELATED ASSETS
        result.setViewName("redirect:/");
        return result;
    }

    // No se si van aquí. Si no van aqui lo cambiaré

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
        // 1): Obligar a descartar dos cartas
        // 2): Colocar una habitación en la mazmorra
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

}
