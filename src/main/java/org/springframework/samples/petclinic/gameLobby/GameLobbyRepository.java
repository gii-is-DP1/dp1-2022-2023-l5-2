package org.springframework.samples.petclinic.gameLobby;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameLobbyRepository extends CrudRepository<GameLobby, Long> {
    
    List<GameLobby> findAll();

}