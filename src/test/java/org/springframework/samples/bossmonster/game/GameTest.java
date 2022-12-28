package org.springframework.samples.bossmonster.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.bossmonster.game.card.Card;
import org.springframework.samples.bossmonster.game.card.CardService;
import org.springframework.samples.bossmonster.game.card.TreasureType;
import org.springframework.samples.bossmonster.game.card.hero.HeroCard;
import org.springframework.samples.bossmonster.game.card.room.RoomCard;
import org.springframework.samples.bossmonster.game.card.spell.SpellCard;
import org.springframework.samples.bossmonster.game.dungeon.Dungeon;
import org.springframework.samples.bossmonster.game.dungeon.DungeonRoomSlot;
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
        dungeon.setEntrance(new ArrayList<>());
        return dungeon;
    }

    void setUpAllDummyDungeons() {
        List<Player> players = game.getPlayers();

        // Book: 9, Sword: 1, Cross: 0, Bag: 1
        String[] dungeon1 = {"3000", "1101", "2000", "3000", "0000"};
        // Book: 1, Sword: 1, Cross: 0, Bag: 1
        String[] dungeon2 = {"1000", "0100", "0000", "0001", "0000"};
        // Book: 0, Sword: 6, Cross: 0, Bag: 9
        String[] dungeon3 = {"0203", "0203", "0203", "0000", "0000"};
        // Book: 1, Sword: 6, Cross: 0, Bag: 5
        String[] dungeon4 = {"1000", "0200", "0300", "3105", "0000"};

        // Book: Player 1 has the most
        // Sword: Tie Between Player 3 and 4
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
        for(Player p: game.getPlayers()) for(int i = 4; i >= 0; i --) game.discardCard(p, i);
        List<Card> expectedDiscardPile = game.getDiscardPile();
        for (Player testPlayer: game.getPlayers()) {
            List<Card> expectedHand = testPlayer.getHand();

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
        List<RoomCard> expectedRoomPile = game.getRoomPile();
        List<Card> expectedDiscardPile = game.getDiscardPile();
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
        List<SpellCard> expectedSpellPile = game.getSpellPile();
        List<Card> expectedDiscardPile = game.getDiscardPile();
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

    void shouldLureHeroToBestDungeon() {
        setUpAllDummyDungeons();
        setUpDummyCity();
        List<HeroCard> expectedCity = new ArrayList<>();

        List<HeroCard> expectedPlayer1DungeonEntrance = new ArrayList<>();
        List<HeroCard> expectedPlayer2DungeonEntrance = new ArrayList<>();
        List<HeroCard> expectedPlayer3DungeonEntrance = new ArrayList<>();
        List<HeroCard> expectedPlayer4DungeonEntrance = new ArrayList<>();

        expectedPlayer1DungeonEntrance.add(game.getCity().get(0));  // Book: Player 1 has the most
        expectedCity.add(game.getCity().get(1));                    // Sword: Tie Between Player 3 and 4
        expectedCity.add(game.getCity().get(2));                    // Cross: No one has a cross
        expectedPlayer3DungeonEntrance.add(game.getCity().get(3));  // Bag: Player 3 has the most
        expectedPlayer3DungeonEntrance.add(game.getCity().get(4));  // Fool: Player 3 has the least souls

        game.lureHeroToBestDungeon();

        List<HeroCard> trueCity = game.getCity();
        List<HeroCard> truePlayer1DungeonEntrance = game.getPlayers().get(0).getDungeon().getEntrance();
        List<HeroCard> truePlayer2DungeonEntrance = game.getPlayers().get(1).getDungeon().getEntrance();
        List<HeroCard> truePlayer3DungeonEntrance = game.getPlayers().get(2).getDungeon().getEntrance();
        List<HeroCard> truePlayer4DungeonEntrance = game.getPlayers().get(3).getDungeon().getEntrance();

        assertEquals(expectedCity, trueCity);
        assertEquals(expectedPlayer1DungeonEntrance, truePlayer1DungeonEntrance);
        assertEquals(expectedPlayer2DungeonEntrance, truePlayer2DungeonEntrance);
        assertEquals(expectedPlayer3DungeonEntrance, truePlayer3DungeonEntrance);
        assertEquals(expectedPlayer4DungeonEntrance, truePlayer4DungeonEntrance);
        
    }

    void shouldPlaceHeroInCity() {

    }

    void shouldPlaceFirstRoom() {

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

    void shouldCheckPlayerRoomsEffectTrigger() {
        
    }

    void shouldSortPlayersByFinalBossEx() {

    }

    void shouldGetCurrentPlayerHand() {

    }

    @Test
    void shouldMakeChoice() {

    }

}
