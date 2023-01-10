package org.springframework.samples.bossmonster.invitations;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface InvitationRepository extends CrudRepository<Invitation,Integer>{

    @Query(value = "SELECT * FROM invitations i WHERE i.user=?1",nativeQuery = true)
    List<Invitation> findAllInvitationsUser(@Param(value = "username") String username);

    @Modifying
    @Query(value="DELETE FROM invitations i WHERE i.id=?1", nativeQuery = true)
    void deleteAfterJoin(@Param(value = "id") Integer id);

}
