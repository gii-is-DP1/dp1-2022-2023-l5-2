package org.springframework.samples.bossmonster.gameLobby;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.bossmonster.game.Game;
import org.springframework.samples.bossmonster.game.GameService;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.samples.bossmonster.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/lobby")
public class GameLobbyController {

    public static final String JOIN_LOBBY_FORM = "gameLobbies/joinGameLobbyForm";
    public static final String CREATE_LOBBY_FORM = "gameLobbies/createGameLobbyForm";
    public static final String LOBBY_SCREEN = "gameLobbies/waitingLobby";
    public static final String CURRENT_GAMES = "games/currentGames";


    @Autowired
    GameLobbyService lobbyService;

    @Autowired
    UserService userService;

    @Autowired
    GameService gameService;

    @GetMapping("/listCurrentGames")
    public ModelAndView show(){
        ModelAndView result= new ModelAndView(CURRENT_GAMES);
        result.addObject("games", lobbyService.findCurrentGames());
        return result;
    }

    @GetMapping("/")
    public ModelAndView showJoinLobbyForm() {

        ModelAndView result = new ModelAndView(JOIN_LOBBY_FORM);
        return result;
    }

    @PostMapping("/")
    @Transactional
    public ModelAndView processJoinLobby(Integer roomCode, boolean spectate ) {

        ModelAndView result = new ModelAndView(JOIN_LOBBY_FORM);
        User user = userService.getLoggedInUser().get();

        if(roomCode == null) {
            result.addObject("message", "You must specify a game to join");
            return result;
        }
        Optional<GameLobby> lobby = lobbyService.getLobbyById(roomCode);
        if (lobby.isEmpty()) {
            result.addObject("message", "No room exists with id " + roomCode);
            return result;
        }

        GameLobby presentLobby = lobby.get();
        String lobbyView = "redirect:/lobby/" + roomCode;
        if(presentLobby.getJoinedUsers().contains(user) || spectate) {
            result.setViewName(lobbyView);
            return result;
        }
        if(lobbyService.userIsPlaying(user)) {
            result.addObject("message","You are already in a game!");
            return result;
        }
        if (!presentLobby.isAcceptingNewPlayers()) {
            result.addObject("message", "Game is full!");
            return result;
        }
        presentLobby.addUser(user);
        lobbyService.saveLobby(presentLobby);
        result.setViewName(lobbyView);
        return result;
    }

    @GetMapping("/{id}")
    public ModelAndView joinLobby(@PathVariable Integer id, HttpServletResponse response) {

        GameLobby lobby = lobbyService.getLobbyById(id).get();
        User user = userService.getLoggedInUser().get();
        ModelAndView result = new ModelAndView();

        if(lobby.gameStarted()) {
            result.setViewName("redirect:/games/" + lobby.getGame().getId());
            return result;
        }

        result.setViewName(LOBBY_SCREEN);
        result.addObject("currentUser", user);
        result.addObject("lobby", lobby);
        response.addHeader("Refresh","2");
        return result;
    }

    @PostMapping("/{id}")
    @Transactional
    public ModelAndView leaveLobby(@PathVariable Integer id) {

        GameLobby lobby = lobbyService.getLobbyById(id).get();
        User currentUser = userService.getLoggedInUser().get();
        User leader = lobby.getLeaderUser();
        ModelAndView result = new ModelAndView("redirect:/");

        if(currentUser == leader) {
            lobbyService.deleteLobby(lobby);
        } else {
            if(lobby.getJoinedUsers().contains(currentUser)) lobby.removeUser(currentUser);
        }

        return result;
    }

    @GetMapping("/new")
    public ModelAndView createLobby() {

        ModelAndView result = new ModelAndView();

        result.setViewName(CREATE_LOBBY_FORM);
        result.addObject("gameLobby", new GameLobby());

        return result;
    }

    @PostMapping("/new")
    @Transactional
    public ModelAndView createLobby(@Valid GameLobby lobby, BindingResult br) {
        ModelAndView result = new ModelAndView();
        User user = userService.getLoggedInUser().get();


        if(lobbyService.userIsPlaying(user)) {
            result.setViewName(CREATE_LOBBY_FORM);
            result.addObject("message","You are already in a game!");
            return result;
        }

        if(br.hasErrors()) {
            result.setViewName(CREATE_LOBBY_FORM);
        } else {
            lobby.setLeaderUser(user);
            lobby.setJoinedUsers(new ArrayList<>());
            lobby.addUser(user);
            lobbyService.saveLobby(lobby);
            result.setViewName("redirect:/lobby/"+lobby.getId());
        }

        return result;
    }

    @Transactional
    @GetMapping("/{lobbyId}/newGame")
    public ModelAndView startGame(@PathVariable Integer lobbyId) {
        ModelAndView result = new ModelAndView();
        GameLobby lobby = lobbyService.getLobbyById(lobbyId).get();
        User user = userService.getLoggedInUser().get();

        if(lobby.getLeaderUser() == user && lobby.getJoinedUsers().size() == lobby.getMaxPlayers()) {

            Game game = gameService.createNewGameFromLobby(lobby);
            lobby.setGame(game);
            lobbyService.saveLobby(lobby);
            gameService.saveGame(game);
            result.setViewName("redirect:/games/" + game.getId());

        } else {
            result.setViewName("redirect:/lobby/" + lobbyId);
        }

        return result;
    }

}
