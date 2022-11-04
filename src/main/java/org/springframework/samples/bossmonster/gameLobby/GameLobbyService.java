package org.springframework.samples.bossmonster.gameLobby;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

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
}
