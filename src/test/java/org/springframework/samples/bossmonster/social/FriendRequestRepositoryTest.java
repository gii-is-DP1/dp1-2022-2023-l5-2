package org.springframework.samples.bossmonster.social;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.stereotype.Repository;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Repository.class))
public class FriendRequestRepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FriendRequestRepository repository;

    private User requester;
    private User receiver;
    private FriendRequest request;
    private FriendRequest request2;

    @BeforeEach
    public void setUp() {
        requester = new User();
        requester.setUsername("requester");
        requester.setPassword("password");
        requester.setEmail("email@example.com");
        requester.setNickname("null");
        requester.setDescription("blablablabla");
        receiver = new User();
        receiver.setUsername("receiver");
        receiver.setPassword("password");
        receiver.setEmail("email@example.com");
        receiver.setNickname("null");
        receiver.setDescription("blablablabla");
        request = new FriendRequest();
        request.setRequester(requester);
        request.setReceiver(receiver);
        request.setAccepted(true);
        request2 = new FriendRequest();
        request2.setRequester(receiver);
        request2.setReceiver(requester);
        request2.setAccepted(false);

        entityManager.persist(requester);
        entityManager.persist(receiver);
        entityManager.persist(request);
        entityManager.persist(request2);
        entityManager.flush();
    }

    @Test
    public void testFindAllReceived() {
        List<FriendRequest> requests = repository.findAllReceived("receiver");
        assertEquals(1, requests.size());
        assertEquals("requester", requests.get(0).getRequester().getUsername());
    }

    @Test
    public void testFindAllRequested() {
        List<FriendRequest> requests = repository.findAllRequested("requester");
        assertEquals(1, requests.size());
        assertEquals("receiver", requests.get(0).getReceiver().getUsername());
    }

    @Test
    public void testFindAllRequestedAccepted() {
        List<String> usernames = repository.findAllRequestedAccepted("requester");
        assertEquals(1, usernames.size());
        assertEquals("receiver", usernames.get(0));
    }

    @Test
    public void testFindAllReceivedAccepted() {
        List<String> usernames = repository.findAllReceivedAccepted("receiver");
        assertEquals(1, usernames.size());
        assertEquals("requester", usernames.get(0));
    }

    @Test
    public void testFindAllNotAccepted() {
        List<FriendRequest> requests = repository.findAllNotAccepted("receiver");
        assertEquals(0, requests.size());
    }

    @Test
    public void testAcceptFriendRequest() {
        repository.acceptFriendRequest("requester", "receiver");
        List<FriendRequest> requests = repository.findAllReceived("receiver");
        assertTrue(requests.get(0).getAccepted());
    }

    @Test
    public void testDeclineFriendRequest() {
        repository.declineFriendRequest("requester", "receiver");
        List<FriendRequest> requests = repository.findAllReceived("receiver");
        assertEquals(0, requests.size());
    }

    @Test
    public void testDeleteFriendRequestWhenUserDeleted() {
        repository.deleteFriendRequestWhenUserDeleted("requester");
        List<FriendRequest> requests = repository.findAllReceived("receiver");
        assertEquals(0, requests.size());
    }

    @Test
    public void testUnFriend() {
        repository.unFriend("requester", "receiver");
        List<FriendRequest> requests = repository.findAllReceived("receiver");
        assertEquals(0, requests.size());
    }

}
