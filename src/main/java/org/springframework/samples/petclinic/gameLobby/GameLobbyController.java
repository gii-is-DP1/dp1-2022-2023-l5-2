package org.springframework.samples.petclinic.gameLobby;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/lobby")
public class GameLobbyController {

    public static final String JOIN_LOBBY_FORM = "gameLobbies/joinGameLobbyForm";
    public static final String CREATE_LOBBY_FORM = "gameLobbies/createGameLobbyForm";
    @Autowired
    GameLobbyService service;

    @GetMapping("/{id}")
    public ModelAndView joinLobby(@PathVariable Integer id) {

        Optional<GameLobby> lobby = service.getLobbyById(id);

        ModelAndView result;
        if(lobby.isPresent()) {
            // TODO: Cambiar a página de lobby con la id dada
            result = new ModelAndView("welcome");
        } else {
            result = new ModelAndView(JOIN_LOBBY_FORM);
            result.addObject("message", "No room exists with id " + id);
        }



        return result;
    }


    @GetMapping("/")
    public ModelAndView waitingLobby() {

        ModelAndView result = new ModelAndView(JOIN_LOBBY_FORM);
        return result;
    }

    @PostMapping("/")
    public ModelAndView joinLobby(String roomCode) {

        ModelAndView result = new ModelAndView("redirect:/lobby/" + roomCode);
        //TODO: actualizar lista de jugadores al unirse a la sala (¿como detectar usuarios que cierran la pagina?)

        return result;
    }

    @GetMapping("/new")
    public ModelAndView createLobby() {

        ModelAndView result = new ModelAndView(CREATE_LOBBY_FORM);
        result.addObject("gameLobby", new GameLobby());
        return result;
    }

    @PostMapping("/new")
    public ModelAndView createLobby(@Valid GameLobby lobby, BindingResult br) {
        ModelAndView result;
        if(br.hasErrors()) {
            result = new ModelAndView(CREATE_LOBBY_FORM);
        } else {
            service.saveLobby(lobby);
            result = new ModelAndView("redirect:/lobby/"+lobby.getId());
        }

        return result;
    }
}
