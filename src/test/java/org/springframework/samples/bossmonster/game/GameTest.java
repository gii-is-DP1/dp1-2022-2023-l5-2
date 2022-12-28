package org.springframework.samples.bossmonster.game;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.bossmonster.game.card.Card;
import org.springframework.samples.bossmonster.game.card.CardService;
import org.springframework.samples.bossmonster.game.card.room.RoomCard;
import org.springframework.samples.bossmonster.game.card.spell.SpellCard;
import org.springframework.samples.bossmonster.game.player.Player;
import org.springframework.samples.bossmonster.gameLobby.GameLobby;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest(includeFilters = {@ComponentScan.Filter(Service.class)},
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = GameService.class))
public class GameTest {

    protected Game game;

    GameLobby lobby;

    @Autowired
    protected CardService cardService;

    GameBuilder gameBuilder;

    @BeforeEach
    void setUp() {
        gameBuilder = new GameBuilder(cardService);
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

    User setUpTestUser(Integer uniqueNumber) {
        User testUser = new User();
        testUser.setUsername(String.format("TestUserName%s", uniqueNumber));
        testUser.setPassword(String.format("TestUserPassword%s", uniqueNumber));
        testUser.setEmail(String.format("TestUserEmail%s", uniqueNumber));
        testUser.setDescription(String.format("TestUserDescription%s", uniqueNumber));
        testUser.setNickname(String.format("Nickname%s", uniqueNumber));
        testUser.setEnabled(true);
        return testUser;
    }

    @Test
    void shouldGetPlayerFromUser() {
        for (Player testPlayer: game.getPlayers()) {
            User testPlayerUser = testPlayer.getUser();
            assertEquals(testPlayer, game.getPlayerFromUser(testPlayerUser));
        }
    }

    void shouldDiscardCard() {
        List<Card> expectedDiscardPile = game.getDiscardPile();
        for (Player testPlayer: game.getPlayers()) {
            List<Card> expectedHand = testPlayer.getHand();

            game.discardCard(testPlayer, 0);
            Card discardedCard = expectedHand.remove(0);
            expectedDiscardPile.add(discardedCard);

            List<Card> trueHand = testPlayer.getHand();
            List<Card> trueDiscardPile = game.getDiscardPile();
            assertEquals(trueHand, expectedHand);
            assertEquals(expectedDiscardPile, trueDiscardPile);
        }
    }

    void shouldGetNewRoomCard() {
        List<RoomCard> expectedRoomPile = game.getRoomPile();
        for (Player testPlayer: game.getPlayers()) {
            List<Card> expectedHand = testPlayer.getHand();

            game.getNewRoomCard(testPlayer);
            RoomCard newRoomCard = expectedRoomPile.remove(0);
            expectedHand.add(newRoomCard);

            List<Card> trueHand = testPlayer.getHand();
            List<RoomCard> trueRoomPile = game.getRoomPile();
            assertEquals(trueHand, expectedHand);
            assertEquals(expectedRoomPile, trueRoomPile);
        }
    }

    void shouldGetNewSpellCard() {
        List<SpellCard> expectedSpellPile = game.getSpellPile();
        for (Player testPlayer: game.getPlayers()) {
            List<Card> expectedHand = testPlayer.getHand();

            game.getNewRoomCard(testPlayer);
            SpellCard newRoomCard = expectedSpellPile.remove(0);
            expectedHand.add(newRoomCard);

            List<Card> trueHand = testPlayer.getHand();
            List<SpellCard> trueSpellPile = game.getSpellPile();
            assertEquals(trueHand, expectedHand);
            assertEquals(expectedSpellPile, trueSpellPile);
        }
    }

    void shouldGetCardFromDiscardedPile() {

    }

    void shouldRefillRoomPile() {
        // Uups...
    }

    void shouldRefillSpellPile() {
        // Uups x2...
    }

    void shouldLureHeroToBestDungeon() {
        // UFFFFFFFFFFFFFFFF
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
