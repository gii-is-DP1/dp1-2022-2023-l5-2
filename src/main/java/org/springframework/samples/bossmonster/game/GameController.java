package org.springframework.samples.bossmonster.game;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.bossmonster.game.player.Player;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.samples.bossmonster.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/games")
public class GameController {

    public static final String GAME_SCREEN = "games/gameScreen";
    public static final String GAMES_DATA = "games/currentGames";
    public static final String RESULTS_SCREEN = "statistics/resultsScreen";
    GameService gameService;
    UserService userService;

    @Autowired
    public GameController(GameService service, UserService userService) {
        this.gameService = service;
        this.userService = userService;
    }

    @PostMapping("/{gameId}")
    public ModelAndView handleChoice(@PathVariable Integer gameId, @RequestParam Integer choice, HttpServletResponse response) {

        ModelAndView result=new ModelAndView(GAME_SCREEN);
        Game game = gameService.findGame(gameId).get();
        User currentUser = userService.getLoggedInUser().get();
        Player currentPlayer = game.getPlayerFromUser(currentUser);

        if(game.getPlayerHasToChoose(currentPlayer)) game.makeChoice(choice);
        else game.getState().checkStateStatus();
        response.addHeader("Refresh","2");
        gameService.saveGame(game);

        setUpGameScreen(result, game, currentPlayer);
        return result;
    }

    @GetMapping("/{gameId}")
    public ModelAndView showGame(@PathVariable Integer gameId, HttpServletResponse response) {

        ModelAndView result=new ModelAndView(GAME_SCREEN);
        Game game = gameService.findGame(gameId).get();
        User currentUser = userService.getLoggedInUser().get();
        Player currentPlayer = game.getPlayerFromUser(currentUser);

        if(game.getResult() != null) {
            result.setViewName("redirect:/games/" + gameId + "/results");
            return result;
        }

        if(!(game.getPlayerHasToChoose(currentPlayer) && game.getChoice() != null)) {
            game.getState().checkStateStatus();
            try {
                gameService.saveGame(game);
            } catch (StaleObjectStateException e) {
                log.debug("Game has a newer version, update prevented");
                result.setViewName("redirect:/"+gameId);
            }
            response.addHeader("Refresh",game.getState().getWaitingTime().toString());
        } else {
            log.debug(String.format("%s has to choose, triggering model", currentPlayer.getUser().getNickname()));
            log.debug("Choice: "+game.getChoice());
            result.addObject("triggerModal", true);
        }
        setUpGameScreen(result, game, currentPlayer);

        return result;
    }

    private static void setUpGameScreen(ModelAndView result, Game game, Player currentPlayer) {
        List<Player> otherPlayers = new ArrayList<>(game.getPlayers());
        otherPlayers.remove(currentPlayer);

        result.addObject("game", game);
        result.addObject("currentPlayer", currentPlayer);
        result.addObject("players", otherPlayers);
    }

    @GetMapping("/{gameId}/results")
    public ModelAndView showResults(@PathVariable Integer gameId) {
        ModelAndView result = new ModelAndView(RESULTS_SCREEN);
        Game game = gameService.findGame(gameId).get();

        result.addObject("result",game.getResult());

        return result;
    }

    @GetMapping("/listActiveGames")
    public ModelAndView show(){
        ModelAndView result= new ModelAndView(GAMES_DATA);
        result.addObject("game", gameService.findActiveGames());
        return result;
    }



}
