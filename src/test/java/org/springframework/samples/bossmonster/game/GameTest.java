package org.springframework.samples.bossmonster.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
import org.springframework.samples.bossmonster.game.card.TreasureType;
import org.springframework.samples.bossmonster.game.card.finalBoss.FinalBossCard;
import org.springframework.samples.bossmonster.game.card.hero.HeroCard;
import org.springframework.samples.bossmonster.game.card.hero.HeroCardStateInDungeon;
import org.springframework.samples.bossmonster.game.card.room.RoomCard;
import org.springframework.samples.bossmonster.game.card.room.RoomPassiveTrigger;
import org.springframework.samples.bossmonster.game.card.spell.SpellCard;
import org.springframework.samples.bossmonster.game.dungeon.Dungeon;
import org.springframework.samples.bossmonster.game.dungeon.DungeonRoomSlot;
import org.springframework.samples.bossmonster.game.gameState.GameState;
import org.springframework.samples.bossmonster.game.gameState.GameSubPhase;
import org.springframework.samples.bossmonster.game.player.Player;
import org.springframework.samples.bossmonster.gameLobby.GameLobby;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.stereotype.Service;


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
        joinedUsers.add(leaderUser);

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

    RoomCard setUpDummyRoomCard(String treasure) {
        RoomCard roomCard = new RoomCard();
        roomCard.setTreasure(treasure);
        return roomCard;
    }

    Dungeon setUpDummyDungeon(String[] treasure) {
        Dungeon dungeon = new Dungeon();
        DungeonRoomSlot[] slots = new DungeonRoomSlot[5];
        for (int i = 0; i < 5; i ++) {
            RoomCard roomCard = setUpDummyRoomCard(treasure[i]);
            slots[i] = new DungeonRoomSlot();
            slots[i].setRoom(roomCard);
        }
        dungeon.setRoomSlots(slots);
        FinalBossCard boss = new FinalBossCard();
        boss.setTreasure(TreasureType.SWORD);
        dungeon.setBossCard(boss);
        return dungeon;
    }

    void setUpAllDummyDungeons() {
        List<Player> players = game.getPlayers();

        // Book: 9, Sword: 1+1, Cross: 0, Bag: 1
        String[] dungeon1 = {"3000", "1101", "2000", "3000", "0000"};
        // Book: 1, Sword: 1+1, Cross: 0, Bag: 1
        String[] dungeon2 = {"1000", "0100", "0000", "0001", "0000"};
        // Book: 0, Sword: 6+1, Cross: 0, Bag: 9
        String[] dungeon3 = {"0203", "0203", "0203", "0000", "0000"};
        // Book: 1, Sword: 6+1, Cross: 0, Bag: 5
        String[] dungeon4 = {"1000", "0200", "0300", "3105", "0000"};

        // Book: Player 1 has the most
        // Sword: Tie Between Player 3 and 4 (+1 is from Final Boss)
        // Cross: No one has a cross
        // Bag: Player 3 has the most
        // Fool: Player 3 has the least souls
        players.get(0).setDungeon(setUpDummyDungeon(dungeon1));
        players.get(1).setDungeon(setUpDummyDungeon(dungeon2));
        players.get(2).setDungeon(setUpDummyDungeon(dungeon3));
        players.get(3).setDungeon(setUpDummyDungeon(dungeon4));
        players.get(0).setSouls(4);
        players.get(1).setSouls(3);
        players.get(2).setSouls(2);
        players.get(3).setSouls(5);
    }

    void setUpDummyCity() {
        List<HeroCard> city = new ArrayList<>();
        city.add(setUpDummyHero(TreasureType.BOOK, 4, false));
        city.add(setUpDummyHero(TreasureType.SWORD, 10, true));
        city.add(setUpDummyHero(TreasureType.CROSS, 4, false));
        city.add(setUpDummyHero(TreasureType.BAG, 4, false));
        city.add(setUpDummyHero(TreasureType.FOOL, 4, false));
        game.setCity(city);
    }

    HeroCard setUpDummyHero(TreasureType treasureType, Integer health, Boolean isEpic) {
        HeroCard hero = new HeroCard();
        hero.setTreasure(treasureType);
        hero.setHealth(health);
        hero.setIsEpic(isEpic);
        return hero;
    }

    @ParameterizedTest
    @CsvSource(
        {"6,7",
        "-6,2"})
    void shouldGetWaitingTime(int seconds, int expected) {
        game.getState().setClock(LocalDateTime.now().plusSeconds(seconds));
        Integer time = game.getState().getWaitingTime();
        assertThat(time).isEqualTo(expected);
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
        List<Card> expectedDiscardPile = new ArrayList<>(game.getDiscardPile());
        for (Player testPlayer: game.getPlayers()) {
            List<Card> expectedHand = new ArrayList<>(testPlayer.getHand());

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
        List<RoomCard> expectedRoomPile = new ArrayList<>(game.getRoomPile());
        for (Player testPlayer: game.getPlayers()) {
            List<Card> expectedHand = new ArrayList<>(testPlayer.getHand());

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
        List<SpellCard> expectedSpellPile = new ArrayList<>(game.getSpellPile());
        for (Player testPlayer: game.getPlayers()) {
            List<Card> expectedHand = new ArrayList<>(testPlayer.getHand());

            game.getNewSpellCard(testPlayer);
            SpellCard newSpellCard = expectedSpellPile.remove(0);
            expectedHand.add(newSpellCard);

            List<Card> trueHand = testPlayer.getHand();
            List<SpellCard> trueSpellPile = game.getSpellPile();
            assertEquals(trueHand, expectedHand);
            assertEquals(expectedSpellPile, trueSpellPile);
        }
    }

    @Test
    void shouldGetCardFromDiscardedPile() {
        for(Player p: game.getPlayers()) for(int i = 4; i >= 0; i --) game.discardCard(p, i);
        List<Card> expectedDiscardPile = new ArrayList<>(game.getDiscardPile());
        for (Player testPlayer: game.getPlayers()) {
            List<Card> expectedHand = new ArrayList<>(testPlayer.getHand());

            game.getCardFromDiscardPile(testPlayer, 0);
            Card newCard = expectedDiscardPile.remove(0);
            expectedHand.add(newCard);

            List<Card> trueHand = testPlayer.getHand();
            List<Card> trueDiscardPile = game.getDiscardPile();
            assertEquals(trueHand, expectedHand);
            assertEquals(expectedDiscardPile, trueDiscardPile);
        }
    }

    @Test
    void shouldRefillRoomPile() {
        for(Player p: game.getPlayers()) for(int i = 4; i >= 0; i --) game.discardCard(p, i);
        List<RoomCard> expectedRoomPile = new ArrayList<>(game.getRoomPile());
        List<Card> expectedDiscardPile = new ArrayList<>(game.getDiscardPile());
        Iterator<Card> iterator = expectedDiscardPile.iterator();
        while (iterator.hasNext()) {
            Card c = iterator.next();
            if (c.getClass() == RoomCard.class) {
                iterator.remove();
                expectedRoomPile.add((RoomCard) c);
            }
        }
        List<RoomCard> trueRoomPile = game.getRoomPile();
        List<Card> trueDiscardPile = game.getDiscardPile();
        assertEquals(new HashSet<>(trueRoomPile), new HashSet<>(expectedRoomPile));
        assertEquals(new HashSet<>(trueDiscardPile), new HashSet<>(expectedDiscardPile));
    }

    @Test
    void shouldRefillSpellPile() {
        for(Player p: game.getPlayers()) for(int i = 4; i >= 0; i --) game.discardCard(p, i);
        List<SpellCard> expectedSpellPile = new ArrayList<>(game.getSpellPile());
        List<Card> expectedDiscardPile = new ArrayList<>(game.getDiscardPile());
        Iterator<Card> iterator = expectedDiscardPile.iterator();
        while (iterator.hasNext()) {
            Card c = iterator.next();
            if (c.getClass() == SpellCard.class) {
                iterator.remove();
                expectedSpellPile.add((SpellCard) c);
            }
        }
        List<SpellCard> trueSpellPile = game.getSpellPile();
        List<Card> trueDiscardPile = game.getDiscardPile();
        assertEquals(new HashSet<>(trueSpellPile), new HashSet<>(expectedSpellPile));
        assertEquals(new HashSet<>(trueDiscardPile), new HashSet<>(expectedDiscardPile));
    }

    @Test
    void shouldLureHeroToBestDungeon() {
        setUpAllDummyDungeons();
        setUpDummyCity();
        List<HeroCard> expectedCity = new ArrayList<>();
        List<HeroCard> expectedPlayer1DungeonFirstRoom = new ArrayList<>();
        List<HeroCard> expectedPlayer2DungeonFirstRoom = new ArrayList<>();
        List<HeroCard> expectedPlayer3DungeonFirstRoom = new ArrayList<>();
        List<HeroCard> expectedPlayer4DungeonFirstRoom = new ArrayList<>();

        expectedPlayer1DungeonFirstRoom.add(game.getCity().get(0));  // Book: Player 1 has the most books
        expectedCity.add(game.getCity().get(1));                     // Sword: Tie between Player 3 and 4, so it stays in the city
        expectedCity.add(game.getCity().get(2));                     // Cross: No one has a cross, so it stays in the city
        expectedPlayer3DungeonFirstRoom.add(game.getCity().get(3));  // Bag: Player 3 has the most bags
        expectedPlayer3DungeonFirstRoom.add(game.getCity().get(4));  // Fool: Player 3 has the least souls

        game.lureHeroToBestDungeon();

        List<HeroCard> trueCity = game.getCity();
        List<HeroCard> truePlayer1DungeonFirstRoom = game.getPlayers().get(0).getDungeon().getRoomSlots()[game.getPlayers().get(0).getDungeon().getFirstRoomSlot()].getHeroesInRoom().stream().map(x -> x.getHeroCard()).collect(Collectors.toList());
        List<HeroCard> truePlayer2DungeonFirstRoom = game.getPlayers().get(1).getDungeon().getRoomSlots()[game.getPlayers().get(1).getDungeon().getFirstRoomSlot()].getHeroesInRoom().stream().map(x -> x.getHeroCard()).collect(Collectors.toList());
        List<HeroCard> truePlayer3DungeonFirstRoom = game.getPlayers().get(2).getDungeon().getRoomSlots()[game.getPlayers().get(2).getDungeon().getFirstRoomSlot()].getHeroesInRoom().stream().map(x -> x.getHeroCard()).collect(Collectors.toList());
        List<HeroCard> truePlayer4DungeonFirstRoom = game.getPlayers().get(3).getDungeon().getRoomSlots()[game.getPlayers().get(3).getDungeon().getFirstRoomSlot()].getHeroesInRoom().stream().map(x -> x.getHeroCard()).collect(Collectors.toList());

        assertEquals(expectedCity, trueCity);
        assertEquals(expectedPlayer1DungeonFirstRoom, truePlayer1DungeonFirstRoom);
        assertEquals(expectedPlayer2DungeonFirstRoom, truePlayer2DungeonFirstRoom);
        assertEquals(expectedPlayer3DungeonFirstRoom, truePlayer3DungeonFirstRoom);
        assertEquals(expectedPlayer4DungeonFirstRoom, truePlayer4DungeonFirstRoom);
    }

    void shouldPlaceHeroInCity() {

    }

    @Test
    void shouldPlaceFirstRoom() {
        for (Player p: game.getPlayers()) {
            RoomCard chosenCard = (RoomCard) p.getHand().stream().filter(x -> x.getClass() == RoomCard.class).findAny().get();
            Dungeon expectedDungeon = p.getDungeon();
            List<Card> expectedHand = new ArrayList<>(p.getHand());
            expectedHand.remove(chosenCard);
            expectedDungeon.getRoomSlots()[0].setRoom(chosenCard);

            List<Card> trueHand = p.getHand();
            Dungeon trueDungeon = p.getDungeon();
            assertEquals(expectedHand, trueHand);
            assertEquals(expectedDungeon, trueDungeon);
        }
    }

    void shouldCheckPlaceableRoomInDungeonPosition() {

    }

    void shouldPlaceDungeonRoom() {

    }

    void shouldDestroyDungeonRoom() {

    }

    void shouldHeroAdvanceRoomDungeon() {

    }

    void shouldHeroAutomaticallyMovesAfterDestroyingRoom() {

    }

    void shouldRevealAllDungeonRooms() {

    }

    @Test
    void shouldCheckPlayerRoomsEffectTrigger() {
        RoomCard dummyCard1 = new RoomCard();
        RoomCard dummyCard2 = new RoomCard();
        RoomCard dummyCard3 = new RoomCard();
        RoomCard dummyCard4 = new RoomCard();
        dummyCard1.setPassiveTrigger(RoomPassiveTrigger.BUILD_MONSTER_ROOM);
        dummyCard2.setPassiveTrigger(RoomPassiveTrigger.DESTROY_THIS_ROOM);
        dummyCard3.setPassiveTrigger(RoomPassiveTrigger.DESTROY_THIS_ROOM);
        dummyCard4.setPassiveTrigger(RoomPassiveTrigger.USE_SPELL_CARD);
        Player player = game.getPlayers().get(0);

        player.getDungeon().getRoomSlots()[0].setRoom(dummyCard1);
        player.getDungeon().getRoomSlots()[1].setRoom(dummyCard2);
        player.getDungeon().getRoomSlots()[2].setRoom(dummyCard3);
        player.getDungeon().getRoomSlots()[3].setRoom(dummyCard4);

        assertTrue(game.checkPlayerRoomsEffectTrigger(player, RoomPassiveTrigger.BUILD_MONSTER_ROOM, 0));
        assertTrue(game.checkPlayerRoomsEffectTrigger(player, RoomPassiveTrigger.DESTROY_THIS_ROOM, 1));
        assertTrue(game.checkPlayerRoomsEffectTrigger(player, RoomPassiveTrigger.DESTROY_THIS_ROOM, 2));
        assertTrue(game.checkPlayerRoomsEffectTrigger(player, RoomPassiveTrigger.USE_SPELL_CARD, 3));
        assertFalse(game.checkPlayerRoomsEffectTrigger(player, RoomPassiveTrigger.USE_SPELL_CARD, 4));
        assertFalse(game.checkPlayerRoomsEffectTrigger(player, RoomPassiveTrigger.NONE, 0));
        assertFalse(game.checkPlayerRoomsEffectTrigger(player, RoomPassiveTrigger.DESTROY_THIS_ROOM, 3));
    }

    @Test
    void shouldSortPlayersByFinalBossEx() {
        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);
        Player player3 = game.getPlayers().get(2);
        Player player4 = game.getPlayers().get(3);
        player1.getDungeon().getBossCard().setXp(100);
        player2.getDungeon().getBossCard().setXp(250);
        player3.getDungeon().getBossCard().setXp(750);
        player4.getDungeon().getBossCard().setXp(500);

        List<Player> expectedPlayerList = new ArrayList<>();
        expectedPlayerList.add(player3);
        expectedPlayerList.add(player4);
        expectedPlayerList.add(player2);
        expectedPlayerList.add(player1);

        game.sortPlayersByFinalBossEx();

        assertEquals(expectedPlayerList, game.getPlayers());
        Collections.reverse(expectedPlayerList);
        assertNotEquals(expectedPlayerList, game.getPlayers());
    }

    @Test
    void shouldGetCurrentPlayerHand() {
        for(int i = 0; i < 4; i ++) {
            game.getState().setCurrentPlayer(i);
            List<Card> expectedHand = game.getPlayers().get(i).getHand();
            assertEquals(expectedHand, game.getCurrentPlayerHand());
        }
    }

    @Test
    void shouldIncrementCounter() {
        Integer expectedCounter = game.getState().getCounter() + 1;
        game.incrementCounter();
        Integer trueCounter = game.getState().getCounter();
        assertEquals(expectedCounter, trueCounter);
    }

    @Test
    void shouldDecrementCounter() {
        Integer expectedCounter = game.getState().getCounter() - 1;
        game.decrementCounter();
        Integer trueCounter = game.getState().getCounter();
        assertEquals(expectedCounter, trueCounter);
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
