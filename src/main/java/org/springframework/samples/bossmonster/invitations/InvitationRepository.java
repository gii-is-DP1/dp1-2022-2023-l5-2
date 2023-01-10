package org.springframework.samples.bossmonster.invitations;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface InvitationRepository extends CrudRepository<Invitation,Integer>{

    @Query(value = "SELECT * FROM invitations i WHERE i.user=?1",nativeQuery = true)
    List<Invitation> findAllInvitationsUser(@Param(value = "username") String username);


    
}
