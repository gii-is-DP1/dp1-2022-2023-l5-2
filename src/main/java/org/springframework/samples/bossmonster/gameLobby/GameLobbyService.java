package org.springframework.samples.bossmonster.gameLobby;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.bossmonster.exceptions.FullLobbyException;
import org.springframework.samples.bossmonster.exceptions.GameNotFullException;
import org.springframework.samples.bossmonster.exceptions.UserAlreadyPlayingException;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.samples.bossmonster.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameLobbyService {

	@Autowired
	private GameLobbyRepository lobbyRepo;

    @Transactional
    public Optional<GameLobby> getLobbyById(int id) {
        return lobbyRepo.findById(id);
    }

    @Transactional
    public void saveLobby(GameLobby lobby) throws UserAlreadyPlayingException, FullLobbyException, GameNotFullException {
        for (User user: lobby.getJoinedUsers()) {
            if (userIsPlaying(user))
                throw new UserAlreadyPlayingException();
            if (lobby.getJoinedUsers().size() > lobby.getMaxPlayers())
                throw new FullLobbyException();
            if(lobby.getGame() != null && lobby.getJoinedUsers().size() != lobby.getMaxPlayers())
                throw new GameNotFullException();
        }

        lobbyRepo.save(lobby);
    }

    @Transactional
    public void deleteLobby(GameLobby lobby) {lobbyRepo.delete(lobby);}

    @Transactional
    public List<GameLobby> findByUser(User user) {return lobbyRepo.findByUser(user);}

    @Transactional
    public boolean userIsPlaying(User user) {
        List<GameLobby> lobbies = lobbyRepo.findByUser(user);
        return !(lobbies.isEmpty() || lobbies.stream()
            .anyMatch(l -> l.gameStarted() && !l.gameIsActive()));
    }

    @Transactional
    public List<GameLobby> findAll() {
        return lobbyRepo.findAll();
    }

    @Transactional
    public List<GameLobby> findCurrentGames() {
        return lobbyRepo.findCurrentGames();
    }
}
