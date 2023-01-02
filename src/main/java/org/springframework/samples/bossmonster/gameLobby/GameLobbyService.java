package org.springframework.samples.bossmonster.gameLobby;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.stereotype.Service;

@Service
public class GameLobbyService {

	@Autowired
	private GameLobbyRepository lobbyRepo;

    public Optional<GameLobby> getLobbyById(int id) {
        return lobbyRepo.findById(id);
    }

    public void saveLobby(GameLobby lobby) {
        lobbyRepo.save(lobby);
    }

    public void deleteLobby(GameLobby lobby) {lobbyRepo.delete(lobby);}

    public List<GameLobby> findByUser(User user) {return lobbyRepo.findByUser(user);}

    public boolean userIsPlaying(User user) {
        List<GameLobby> lobbies = lobbyRepo.findByUser(user);
        return !(lobbies.isEmpty() || lobbies.stream()
            .anyMatch(l -> l.gameStarted() && !l.gameIsActive()));
    }

    public List<GameLobby> findAll() {
        return lobbyRepo.findAll();
    }
}
