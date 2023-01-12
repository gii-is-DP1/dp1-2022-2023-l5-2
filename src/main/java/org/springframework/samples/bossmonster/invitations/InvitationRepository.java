package org.springframework.samples.bossmonster.invitations;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface InvitationRepository extends CrudRepository<Invitation,Integer>{

    @Query(value = "SELECT i FROM Invitation i WHERE i.user.username=?1")
    List<Invitation> findAllInvitationsUser(@Param(value = "username") String username);

    @Modifying
    @Query(value="DELETE FROM Invitation i WHERE i.id=?1")
    void deleteAfterJoin(@Param(value = "id") Integer id);

    @Modifying
    @Query(value = "DELETE FROM Invitation i WHERE i.lobby.id=?1")
    void deleteAfterLobbyDedge(@Param(value = "id") Integer id);

    @Modifying
    @Query(value = "DELETE FROM Invitation i WHERE i.user.username=?1")
    void deleteAfterUserDedge(@Param(value = "username") String username);

}
