package org.springframework.samples.bossmonster.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.bossmonster.game.card.Card;
import org.springframework.samples.bossmonster.game.card.CardService;
import org.springframework.samples.bossmonster.game.card.room.RoomCard;
import org.springframework.samples.bossmonster.game.card.spell.SpellCard;
import org.springframework.samples.bossmonster.game.gameState.GameState;
import org.springframework.samples.bossmonster.game.gameState.GameSubPhase;
import org.springframework.samples.bossmonster.game.player.Player;
import org.springframework.samples.bossmonster.gameLobby.GameLobby;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@DataJpaTest(includeFilters = {@ComponentScan.Filter(Service.class)},
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = GameService.class))
public class GameTest {

    protected Game game;

    GameLobby lobby;

    @Autowired
    protected CardService cardService;

    GameBuilder gameBuilder;

    Player player;
    @BeforeEach
    void setUp() {
        gameBuilder = new GameBuilder(cardService);
        lobby = setUpGameLobby();
        game = gameBuilder.buildNewGame(lobby);
        player=game.getCurrentPlayer();

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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    static Stream<Arguments> shouldMakeChoice() {
        RoomCard someRoom = new RoomCard();
        someRoom.setName("Some Room");
        RoomCard anotherRoom = new RoomCard();
        anotherRoom.setName("Another Room");
        SpellCard someSpell = new SpellCard();
        someSpell.setName("Some spell");
        return Stream.of(
            Arguments.of(GameSubPhase.BUILD_NEW_ROOM)
        );
    }

    @ParameterizedTest
    @CsvSource({
        "USE_SPELLCARD,2,0,2",
        "DISCARD_2_STARTING_CARDS,0,0,2",
        "PLACE_FIRST_ROOM,0,1,2",
        "BUILD_NEW_ROOM,0,0,3",
        "USE_SPELLCARD,-1,0,3",
    })
    void shouldMakeChoice(GameSubPhase subPhase, Integer choice, Integer expectedBuiltRooms, Integer expectedCardsInHand) {
        player.setHand(new ArrayList<>(List.of(
                new RoomCard(), new RoomCard(), new SpellCard()
            )));
        game.getState().setSubPhase(subPhase);

        game.makeChoice(choice);

        assertThat(player.getHand()).hasSize(expectedCardsInHand);
        assertThat(player.getDungeon().getBuiltRooms()).isEqualTo(expectedBuiltRooms);
    }

    @Test
    void shouldNotAdvanceGameWithInvalidChoice() {
        player.setHand(new ArrayList<>(List.of(
            new RoomCard(), new RoomCard(), new SpellCard()
        )));
        GameState mockState = mock(GameState.class);
        when(mockState.getSubPhase()).thenReturn(GameSubPhase.PLACE_FIRST_ROOM);
        game.setState(mockState);

        game.makeChoice(2);
        verify(mockState,never()).setCounter(any(Integer.class));
    }


    static Stream<Arguments> shouldGetUnplayableCards() {
        return Stream.of(Arguments.of(GameSubPhase.USE_SPELLCARD, List.of(2)),
            Arguments.of(GameSubPhase.PLACE_FIRST_ROOM, List.of(0,1)),
            Arguments.of(GameSubPhase.BUILD_NEW_ROOM, List.of(0,1)),
            Arguments.of(GameSubPhase.DISCARD_2_STARTING_CARDS, List.of()),
            Arguments.of(GameSubPhase.ANNOUNCE_NEW_PHASE, List.of()));
    }
    @ParameterizedTest
    @MethodSource
    void shouldGetUnplayableCards(GameSubPhase subphase, List<Integer> expected) {
        game.getState().setSubPhase(subphase);
        Player player = game.getCurrentPlayer();
        player.setHand(List.of(new SpellCard(), new SpellCard(), new RoomCard()));
        assertThat(game.getUnplayableCards()).isEqualTo(expected);
    }
}
