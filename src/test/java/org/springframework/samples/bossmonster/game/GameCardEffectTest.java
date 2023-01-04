package org.springframework.samples.bossmonster.game;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.bossmonster.game.card.CardService;
import org.springframework.samples.bossmonster.game.card.EffectEnum;
import org.springframework.samples.bossmonster.game.card.TreasureType;
import org.springframework.samples.bossmonster.game.card.hero.HeroCard;
import org.springframework.samples.bossmonster.game.card.hero.HeroCardStateInDungeon;
import org.springframework.samples.bossmonster.game.card.room.RoomCard;
import org.springframework.samples.bossmonster.game.card.room.RoomPassiveTrigger;
import org.springframework.samples.bossmonster.game.card.spell.SpellCard;
import org.springframework.samples.bossmonster.game.gameState.GamePhase;
import org.springframework.samples.bossmonster.game.player.Player;
import org.springframework.samples.bossmonster.gameLobby.GameLobby;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.stereotype.Service;

import static org.hamcrest.Matchers.*;

@DataJpaTest(includeFilters = {@ComponentScan.Filter(Service.class)},
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = GameService.class))
public class GameCardEffectTest {

    protected Game game;

    GameLobby lobby;

    @Autowired
    protected CardService cardService;

    GameBuilder gameBuilder;

    Player testPlayer;

    @BeforeEach
    void setUp() {
        gameBuilder = new GameBuilder(cardService);
        lobby = setUpGameLobby();
        game = gameBuilder.buildNewGame(lobby);
        testPlayer = game.getPlayers().get(0);
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

    private RoomCard setUpDummyRoomCard(RoomPassiveTrigger trigger, EffectEnum effect) {
        RoomCard room = new RoomCard();
        room.setPassiveTrigger(trigger);
        room.setEffect(effect);
        return room;
    }

    HeroCardStateInDungeon setUpDummyHero(TreasureType treasureType, Integer health, Boolean isEpic) {
        HeroCard hero = new HeroCard();
        hero.setTreasure(treasureType);
        hero.setHealth(health);
        hero.setIsEpic(isEpic);
        HeroCardStateInDungeon heroInDungeon = new HeroCardStateInDungeon(hero);
        return heroInDungeon;
    }

    private void activateRoomCardEffect(RoomCard room) {
        Player testPlayer = game.getPlayers().get(0);
        testPlayer.getDungeon().getRoomSlots()[0].setRoom(room);
        game.triggerRoomCardEffect(testPlayer, 0);
    }

    @Test
    void shouldTriggerBottomlessPitRoomCardEffect() {
        RoomCard botommlessPit = setUpDummyRoomCard(RoomPassiveTrigger.DESTROY_THIS_ROOM, EffectEnum.KILL_ONE_HERO_IN_THIS_ROOM);
        HeroCardStateInDungeon dummyHero1 = setUpDummyHero(TreasureType.BAG, 10,true);
        HeroCardStateInDungeon dummyHero2 = setUpDummyHero(TreasureType.SWORD, 10,true);
        HeroCardStateInDungeon dummyHero3 = setUpDummyHero(TreasureType.CROSS, 10,true);
        testPlayer.getDungeon().getRoomSlots()[0].getHeroesInRoom().add(dummyHero1);
        testPlayer.getDungeon().getRoomSlots()[0].getHeroesInRoom().add(dummyHero2);
        testPlayer.getDungeon().getRoomSlots()[0].getHeroesInRoom().add(dummyHero3);
        activateRoomCardEffect(botommlessPit);
        assertThat("Incorrect amount of heroes killed", testPlayer.getDungeon().getRoomSlots()[0].getHeroesInRoom().size(), is(2));
        assertThat("The soul of the defeated hero was not given to the player", testPlayer.getSouls(), is(2));
    }

    @Ignore
    @Test
    void shouldTriggerTheCrushinatorRoomCardEffect() {
        RoomCard theCrushinator = setUpDummyRoomCard(RoomPassiveTrigger.DESTROY_ANOTHER_ROOM, EffectEnum.ADD_2_DAMAGE_TO_EVERY_ROOM);
        activateRoomCardEffect(theCrushinator);
    }

    @Test
    void shouldTriggerVampireBurdelloRoomCardEffect() {
        RoomCard vampireBurdello = setUpDummyRoomCard(RoomPassiveTrigger.HERO_DIES_IN_THIS_ROOM, EffectEnum.CONVERT_A_WOUND_INTO_A_SOUL);
        activateRoomCardEffect(vampireBurdello);
        assertThat("Player converted unexisting damage", testPlayer.getSouls(), is(0));
        assertThat("Player healed unexisting wound", testPlayer.getHealth(), is(5));
        testPlayer.setHealth(2);
        testPlayer.setSouls(2);
        game.triggerRoomCardEffect(testPlayer, 0);
        assertThat("Player didn't get a soul", testPlayer.getSouls(), is(3));
        assertThat("Player didn't heal a wound", testPlayer.getHealth(), is(3));
    }

    @Test
    void shouldTriggerMonsterBallroomRoomCardEffect() {
        // This one was tested in shouldSetInitialRoomCardDamage() in DungeonTest
    }

    @Ignore
    @Test
    void shouldTriggerBoulderRampRoomCardEffect() {
        RoomCard boulderRamp = setUpDummyRoomCard(RoomPassiveTrigger.DESTROY_ANOTHER_ROOM, EffectEnum.DEAL_5_DAMAGE_TO_A_HERO_IN_THIS_ROOM);
        HeroCardStateInDungeon dummyHero1 = setUpDummyHero(TreasureType.BAG, 10,true);
        HeroCardStateInDungeon dummyHero2 = setUpDummyHero(TreasureType.SWORD, 10,true);
        HeroCardStateInDungeon dummyHero3 = setUpDummyHero(TreasureType.CROSS, 10,true);
        testPlayer.getDungeon().getRoomSlots()[0].getHeroesInRoom().add(dummyHero1);
        testPlayer.getDungeon().getRoomSlots()[0].getHeroesInRoom().add(dummyHero2);
        testPlayer.getDungeon().getRoomSlots()[0].getHeroesInRoom().add(dummyHero3);
        activateRoomCardEffect(boulderRamp);
        assertThat("No hero should be killed", testPlayer.getDungeon().getRoomSlots()[0].getHeroesInRoom().size(), is(3));
        assertThat("Incorrect amount of damage dealt", (int) testPlayer.getDungeon().getRoomSlots()[0].getHeroesInRoom().stream().filter(x -> x.getHealthInDungeon() == 5).count(), is(1));
    }

    @Test
    void shouldTriggerConstructionZoneRoomCardEffect() {
        RoomCard constructionZone = setUpDummyRoomCard(RoomPassiveTrigger.BUILD_THIS_ROOM, EffectEnum.BUILD_ANOTHER_ROOM);
        Player testPlayer = game.getPlayers().get(0);
        testPlayer.getDungeon().getRoomSlots()[0].setRoom(constructionZone);
        game.triggerRoomCardEffect(testPlayer, 0);
        assertThat("The effect of this card shouldn't trigger when building the first room", game.getState().getActionLimit(), is(0));
        game.getState().changePhase(GamePhase.BUILD);
        game.triggerRoomCardEffect(testPlayer, 0);
        assertThat("The card effect didn't trigger", game.getState().getActionLimit(), is(2));
    }

    @Ignore
    @Test
    void shouldTriggerDarkAltarRoomCardEffect() {
        RoomCard darkAltar = setUpDummyRoomCard(RoomPassiveTrigger.DESTROY_THIS_ROOM, EffectEnum.CHOOSE_CARD_FROM_DISCARD_PILE);
        // TODO
    }

    @Test
    void shouldTriggerDragonHatcheryRoomCardEffect() {
        // This card doesn't have any effect
    }

    @Test
    void shouldTriggerNeandertalCaveRoomCardEffect() {
        // This one was tested in shouldCheckPlaceableRoomInDungeonPosition()
    }

    void shouldTriggerOpenGraveRoomCardEffect() {
        RoomCard openGrave = setUpDummyRoomCard(RoomPassiveTrigger.HERO_DIES_IN_THIS_ROOM, EffectEnum.CHOOSE_ROOM_CARD_FROM_DISCARD_PILE);
        // TODO
    }

    @Test
    void shouldTriggerRecyclingCenterRoomCardEffect() {
        RoomCard recyclingCenter = setUpDummyRoomCard(RoomPassiveTrigger.DESTROY_ANOTHER_ROOM, EffectEnum.DRAW_2_ROOM_CARDS);
        Integer priorAmountOfRoomCards = (int) testPlayer.getHand().stream().filter(x -> x.getClass() == RoomCard.class).count();
        activateRoomCardEffect(recyclingCenter);
        Integer postAmountOfRoomCards = (int) testPlayer.getHand().stream().filter(x -> x.getClass() == RoomCard.class).count();
        assertEquals(priorAmountOfRoomCards + 2, postAmountOfRoomCards);
    }

    @Test
    void shouldTriggerLigersDenRoomCardEffect() {
        RoomCard ligersDen = setUpDummyRoomCard(RoomPassiveTrigger.USE_SPELL_CARD, EffectEnum.DRAW_A_SPELL_CARD);
        Integer priorAmountOfSpellCards = (int) testPlayer.getHand().stream().filter(x -> x.getClass() == SpellCard.class).count();
        activateRoomCardEffect(ligersDen);
        Integer postAmountOfRoomCards = (int) testPlayer.getHand().stream().filter(x -> x.getClass() == SpellCard.class).count();
        assertEquals(priorAmountOfSpellCards + 1, postAmountOfRoomCards);
    }

    @Ignore
    @Test
    void shouldTriggerGoblinArmoryRoomCardEffect() {

    }

    @Ignore
    @Test
    void shouldTriggerGolemFactoryRoomCardEffect() {

    }
    
    @Ignore
    @Test
    void shouldTriggerJackpotStashRoomCardEffect() {

    }
    
    @Ignore
    @Test
    void shouldTriggerDarkLaboratoryRoomCardEffect() {

    }
    
    @Ignore
    @Test
    void shouldTriggerMonstrousMonumentRoomCardEffect() {

    }
    
    @Ignore
    @Test
    void shouldTriggerBeastMenagerieRoomCardEffect() {

    }
    
    @Ignore
    @Test
    void shouldTriggerBrainsuckerHiveRoomCardEffect() {

    }
    
    @Ignore
    @Test
    void shouldTriggerDizzygasHallwayRoomCardEffect() {

    }
    
    @Ignore
    @Test
    void shouldTriggerMinotaursMazeRoomCardEffect() {

    }
    
    @Ignore
    @Test
    void shouldTriggerGiantSizeSpellCardEffect() {

    }

}