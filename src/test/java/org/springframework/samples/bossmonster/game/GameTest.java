package org.springframework.samples.bossmonster.game;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.metamodel.model.domain.internal.SetAttributeImpl;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.samples.bossmonster.game.player.Player;
import org.springframework.samples.bossmonster.gameLobby.GameLobby;
import org.springframework.samples.bossmonster.user.User;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    
    protected Game game;

    GameLobby lobby;

    GameBuilder gameBuilder;

    @BeforeEach
    void setUp() {
        lobby = setUpGameLobby();
        game = gameBuilder.buildNewGame(lobby);
    }

    GameLobby setUpGameLobby() {

        GameLobby lobby = new GameLobby();
        List<User> joinedUsers = new ArrayList<>();
        
        User leaderUser = setUpTestUser(1);
        User joinedUser1 = setUpTestUser(2);
        User joinedUser2 = setUpTestUser(3);
        User joinedUser3 = setUpTestUser(4);

        joinedUsers.add(joinedUser1);
        joinedUsers.add(joinedUser2);
        joinedUsers.add(joinedUser3);
        
        lobby.setLeaderUser(leaderUser);
        lobby.setJoinedUsers(joinedUsers);
        lobby.setMaxPlayers(4);
        
        return lobby;

    }

    User setUpTestUser(Integer number) {
        User testUser = new User();
        testUser.setUsername(String.format("TestUserName%s", number));
        testUser.setPassword(String.format("TestUserPassword%s", number));
        testUser.setEmail(String.format("TestUserEmail%s", number));
        testUser.setDescription(String.format("TestUserDescription%s", number));
        testUser.setNickname(String.format("Nickname%s", number));
        testUser.setEnabled(true);
        return testUser;
    }

    void shouldGetPlayerFromUser() {
        for (Player testPlayer: game.getPlayers()) {
            User testPlayerUser = testPlayer.getUser();
            assertEquals(testPlayer, game.getPlayerFromUser(testPlayerUser));
        }
    }

    void shouldDiscardCard() {

    }

    void shouldGetNewRoomCard() {

    }

    void shouldGetNewSpellCard() {

    }

    void shouldGetCardFromDiscardedPile() {

    }

    void shouldRefillRoomPile() {

    }

    void shouldRefillSpellPile() {

    }

    void shouldLureHeroToBestDungeon() {

    }

    void shouldPlaceHeroInCity() {

    }

    void shouldPlaceFirstRoom() {

    }

    void shouldCheckPlaceableRoomInDungeonPosition() {

    }

    void shouldPlaceDungeonRoom() {

    }

    

}
