package org.springframework.samples.bossmonster.gameLobby;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.bossmonster.user.User;

public interface GameLobbyRepository extends CrudRepository<GameLobby, Integer> {

    List<GameLobby> findAll();
    Optional<GameLobby> findById(int id);

    @Query("SELECT l FROM GameLobby l WHERE ?1 member of l.joinedUsers")
    List<GameLobby> findByUser(User u);

    @Modifying
    @Query(value="DELETE FROM lobby_users WHERE user_id=?1",nativeQuery=true)
    void deleteJoinedUserIfUserGetsDeleted(@Param(value = "username") String user);

    @Modifying
    @Query(value = "DELETE FROM LOBBY_USERS WHERE lobby_id IN (SELECT id FROM lobbies WHERE leader = ?1);",nativeQuery = true)
    void deleteParticipantsIfLeaderIsDeleted(@Param(value = "username") String user);

    @Modifying
    @Query(value="DELETE FROM GameLobby g WHERE g.leaderUser.username=?1")
    void deleteLobbyIfLeaderDeleted(@Param(value = "username") String user);

    @Modifying
    @Query(value = "UPDATE GameLobby g SET g.leaderUser=null WHERE g.leaderUser.username=?1")
    void setLeaderToNullBeforeDelete(@Param(value = "username") String username);

    @Query("SELECT gl FROM GameLobby gl INNER JOIN gl.game g WHERE g.active=TRUE")
    List<GameLobby> findCurrentGames();

}
