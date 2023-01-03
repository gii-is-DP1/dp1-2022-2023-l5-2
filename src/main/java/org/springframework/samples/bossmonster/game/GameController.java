package org.springframework.samples.bossmonster.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.bossmonster.game.card.CardService;
import org.springframework.samples.bossmonster.game.card.TreasureType;
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
@RequestMapping("/games")
public class GameController {

    public static final String GAME_SCREEN = "games/gameScreen";
    public static final String GAMES_DATA = "games/currentGames";
    CardService cardService;
    GameService gameService;
    UserService userService;

    @Autowired
    public GameController(GameService service, UserService userService, CardService cardService) {
        this.gameService = service;
        this.userService = userService;
        this.cardService = cardService;
    }

    @PostMapping("/{gameId}")
    public ModelAndView handleChoice(@PathVariable Integer gameId, @RequestParam Integer choice, HttpServletResponse response) {

        ModelAndView result=new ModelAndView(GAME_SCREEN);
        Game game = gameService.findGame(gameId).get();
        User currentUser = userService.getLoggedInUser().get();
        Player currentPlayer = game.getPlayerFromUser(currentUser);

        if(game.getPlayerHasToChoose(currentPlayer)) game.makeChoice(choice);
        else {
            game.getState().checkStateStatus();
        };
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

        if(!(game.getPlayerHasToChoose(currentPlayer))) {
            game.getState().checkStateStatus();
            gameService.saveGame(game);
            response.addHeader("Refresh",game.getState().getWaitingTime().toString());
        } else {
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

    @GetMapping("/listActiveGames")
    public ModelAndView show(){
        ModelAndView result= new ModelAndView(GAMES_DATA);
        result.addObject("game", gameService.findActiveGames());
        return result;
    }

}
