package org.springframework.samples.petclinic.gameLobby;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameLobbyRepository extends CrudRepository<GameLobby, Long> {

    List<GameLobby> findAll();
    Optional<GameLobby> findById(int id);

}
