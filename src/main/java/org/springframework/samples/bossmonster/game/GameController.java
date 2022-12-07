package org.springframework.samples.bossmonster.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.bossmonster.game.card.CardService;
import org.springframework.samples.bossmonster.game.player.Player;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.samples.bossmonster.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/games")
public class GameController {

    CardService cardService;
    GameService gameService;
    UserService userService;

    @Autowired
    public GameController(GameService service, UserService userService, CardService cardService) {
        this.gameService = service;
        this.userService = userService;
        this.cardService = cardService;
    }

    @GetMapping("/{gameId}")
    public ModelAndView joinGame(@PathVariable Integer gameId) {

        ModelAndView result=new ModelAndView();
        Game game = gameService.findGame(gameId).get();
        User currentUser = userService.getLoggedInUser().get();
        Player currentPlayer = game.getPlayerFromUser(currentUser);
        List<Player> otherPlayers = new ArrayList<>(game.getPlayers());
        otherPlayers.remove(currentPlayer);
        System.out.println("other players: " + otherPlayers);

        result.setViewName("games/gameScreen");
        result.addObject("game", game);
        result.addObject("currentPlayer", currentPlayer);
        result.addObject("players", otherPlayers);
        return result;
    }

}
