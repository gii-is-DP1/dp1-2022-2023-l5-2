package org.springframework.samples.bossmonster.gameLobby;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.bossmonster.invitations.InvitationRepository;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GameLobbyService {

	@Autowired
	private GameLobbyRepository lobbyRepo;
    @Autowired
    private InvitationRepository invitationRepository;

    @Transactional
    public Optional<GameLobby> getLobbyById(int id) {
        return lobbyRepo.findById(id);
    }

    public void saveLobby(GameLobby lobby) {
        lobbyRepo.save(lobby);
    }

    @Transactional
    public void deleteLobby(GameLobby lobby) {
        invitationRepository.deleteAfterLobbyDedge(lobby.getId());
        lobbyRepo.delete(lobby);
    }

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
