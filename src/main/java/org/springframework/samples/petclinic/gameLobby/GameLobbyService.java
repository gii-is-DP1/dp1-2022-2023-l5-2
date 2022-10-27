package org.springframework.samples.petclinic.gameLobby;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class GameLobbyService {
 
	@Autowired
	private GameLobbyRepository lobbyRepo;

	public GameLobbyService(GameLobbyRepository lobbyRepo) {
		this.lobbyRepo = lobbyRepo;
	}

}
