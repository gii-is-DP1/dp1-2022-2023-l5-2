package org.springframework.samples.bossmonster.social;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.bossmonster.user.User;

public interface FriendRequestRepository extends CrudRepository<FriendRequest,Integer> {


    @Query(value = "SELECT * FROM friend_requests WHERE receiver=?1",nativeQuery = true)
    List<FriendRequest> findAllReceived(@Param(value = "username") String username);

    @Query(value = "SELECT * FROM friend_requests WHERE requester=?1 ",nativeQuery = true)
    List<FriendRequest> findAllRequested(@Param(value = "username") String username);

    @Modifying
    @Query(value="UPDATE friend_requests SET accepted=TRUE WHERE id=?1",nativeQuery = true)
    void acceptFriendRequest(@Param(value = "id") int requestId);

    @Modifying
    @Query(value = "UPDATE friend_requests SET accepted=FALSE WHERE id=?1",nativeQuery = true)
    void declineFriendRequest(@Param(value = "id") int requestId);

    @Query(value = "SELECT DISTINCT u.username FROM friend_requests fr INNER JOIN users u ON FR.requester=?1 WHERE FR.accepted=TRUE",nativeQuery = true)
    List<String> findAllRequestedAccepted(@Param(value = "username") String username);

    @Query(value="SELECT DISTINCT u.username FROM friend_requests fr INNER JOIN users u ON FR.receiver=?1 WHERE FR.accepted=TRUE",nativeQuery = true)
    List<String> findAllReceivedAccepted(@Param(value = "username") String username);

    
}
