package org.springframework.samples.bossmonster.social;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface FriendRequestRepository extends CrudRepository<FriendRequest,Integer> {


    @Query(value = "SELECT * FROM friend_requests WHERE receiver=?1",nativeQuery = true)
    List<FriendRequest> findAllReceived(@Param(value = "username") String username);

    @Query(value = "SELECT * FROM friend_requests WHERE requester=?1 ",nativeQuery = true)
    List<FriendRequest> findAllRequested(@Param(value = "username") String username);

    @Query(value = "SELECT DISTINCT u.username FROM friend_requests fr INNER JOIN users u ON FR.requester=?1 AND u.username=fr.receiver WHERE FR.accepted=TRUE",nativeQuery = true)
    List<String> findAllRequestedAccepted(@Param(value = "username") String username);

    @Query(value="SELECT DISTINCT u.username FROM friend_requests fr INNER JOIN users u ON FR.requester=u.username AND fr.receiver=?1 WHERE FR.accepted=TRUE",nativeQuery = true)
    List<String> findAllReceivedAccepted(@Param(value = "username") String username);

    @Query(value = "SELECT * FROM friend_requests fr WHERE fr.receiver=?1 AND fr.accepted=FALSE", nativeQuery = true)
    List<FriendRequest> findAllNotAccepted(@Param(value="username") String username);

    @Modifying
    @Query(value = "UPDATE friend_requests SET accepted=TRUE WHERE requester=?1 AND receiver=?2", nativeQuery = true)
    void acceptFriendRequest(@Param(value = "usernameR") String usernameRequester, @Param(value = "username") String myUsername);
    @Modifying
    @Query(value = "DELETE FROM friend_requests fr WHERE fr.requester=?1 AND receiver=?2",nativeQuery = true)
    void declineFriendRequest(@Param(value = "usernameR") String usernameRequester, @Param(value = "username") String myUsername);
    
}
