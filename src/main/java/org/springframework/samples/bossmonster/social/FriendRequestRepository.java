package org.springframework.samples.bossmonster.social;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface FriendRequestRepository extends CrudRepository<FriendRequest,Integer> {


    @Query(value = "SELECT f FROM FriendRequest f WHERE receiver.username=?1")
    List<FriendRequest> findAllReceived(@Param(value = "username") String username);

    @Query(value = "SELECT f FROM FriendRequest f WHERE requester.username=?1 ")
    List<FriendRequest> findAllRequested(@Param(value = "username") String username);

    @Query(value = "SELECT u.username FROM FriendRequest f JOIN User u ON f.requester.username=?1 AND u.username=f.receiver.username WHERE f.accepted=TRUE")
    List<String> findAllRequestedAccepted(@Param(value = "username") String username);

    @Query(value="SELECT u.username FROM FriendRequest f JOIN User u ON f.requester.username=u.username AND f.receiver.username=?1 WHERE f.accepted=TRUE")
    List<String> findAllReceivedAccepted(@Param(value = "username") String username);

    @Query(value = "SELECT f FROM FriendRequest f WHERE f.receiver.username=?1 AND f.accepted=FALSE")
    List<FriendRequest> findAllNotAccepted(@Param(value="username") String username);

    @Modifying
    @Query(value = "UPDATE FriendRequest SET accepted=TRUE WHERE requester.username=?1 AND receiver.username=?2")
    void acceptFriendRequest(@Param(value = "usernameR") String usernameRequester, @Param(value = "username") String myUsername);
    @Modifying
    @Query(value = "DELETE FROM FriendRequest f WHERE f.requester.username=?1 AND receiver.username=?2")
    void declineFriendRequest(@Param(value = "usernameR") String usernameRequester, @Param(value = "username") String myUsername);
    @Modifying
    @Query(value = "DELETE FROM FriendRequest f WHERE f.requester.username=?1 OR f.receiver.username=?1")
    void deleteFriendRequestWhenUserDeleted(@Param(value = "username") String username);
    @Modifying
    @Query(value = "DELETE FROM friend_requests fr WHERE (fr.requester=?1 AND fr.receiver=?2) OR (fr.receiver=?1 AND fr.requester=?2)",nativeQuery = true)
    void unFriend(@Param(value = "username") String noLongerFriendUsername, @Param(value = "username") String myUsername);
    
}
