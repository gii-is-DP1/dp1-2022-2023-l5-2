package org.springframework.samples.bossmonster.gameLobby;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameLobbyRepository extends CrudRepository<GameLobby, Long> {

    List<GameLobby> findAll();
    Optional<GameLobby> findById(int id);

    @Query("SELECT l FROM GameLobby l WHERE ?1 member of l.joinedUsers")
    List<GameLobby> findByUser(User u);

}
