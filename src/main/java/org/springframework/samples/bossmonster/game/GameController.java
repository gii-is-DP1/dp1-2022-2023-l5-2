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



}
