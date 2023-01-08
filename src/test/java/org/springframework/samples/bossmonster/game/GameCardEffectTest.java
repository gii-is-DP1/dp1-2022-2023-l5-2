package org.springframework.samples.bossmonster.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.bossmonster.game.card.Card;
import org.springframework.samples.bossmonster.game.card.CardService;
import org.springframework.samples.bossmonster.game.card.EffectEnum;
import org.springframework.samples.bossmonster.game.card.TreasureType;
import org.springframework.samples.bossmonster.game.card.finalBoss.FinalBossCard;
import org.springframework.samples.bossmonster.game.card.hero.HeroCard;
import org.springframework.samples.bossmonster.game.card.hero.HeroCardStateInDungeon;
import org.springframework.samples.bossmonster.game.card.room.RoomCard;
import org.springframework.samples.bossmonster.game.card.room.RoomPassiveTrigger;
import org.springframework.samples.bossmonster.game.card.room.RoomType;
import org.springframework.samples.bossmonster.game.card.spell.SpellCard;
import org.springframework.samples.bossmonster.game.chat.ChatService;
import org.springframework.samples.bossmonster.game.dungeon.Dungeon;
import org.springframework.samples.bossmonster.game.dungeon.DungeonRoomSlot;
import org.springframework.samples.bossmonster.game.gameState.GamePhase;
import org.springframework.samples.bossmonster.game.gameState.GameSubPhase;
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
    @Autowired
    protected ChatService chatService;

    GameBuilder gameBuilder;

    Player testPlayer;

    @BeforeEach
    void setUp() {
        gameBuilder = new GameBuilder(cardService,chatService);
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

    private SpellCard setUpDummySpellCard(EffectEnum effect) {
        SpellCard spell = new SpellCard();
        spell.setEffect(effect);
        return spell;
    }

    private FinalBossCard setUpFinalBossCard(EffectEnum effect) {
        FinalBossCard boss = new FinalBossCard();
        boss.setEffect(effect);
        return boss;
    }

    private void setUpDummyRoomCardInDungeon(RoomType type, Integer damage, Integer slot) {
        RoomCard room = new RoomCard();
        room.setRoomType(type);
        room.setDamage(damage);
        testPlayer.getDungeon().getRoomSlots()[slot].setRoom(room);
        testPlayer.getDungeon().getRoomSlots()[slot].setRoomTrueDamage(damage);
    }

    HeroCardStateInDungeon setUpDummyHero(TreasureType treasureType, Integer health, Boolean isEpic) {
        HeroCard hero = new HeroCard();
        hero.setTreasure(treasureType);
        hero.setHealth(health);
        hero.setIsEpic(isEpic);
        HeroCardStateInDungeon heroInDungeon = new HeroCardStateInDungeon(hero);
        return heroInDungeon;
    }

    private void levelUpDungeonFinalBoss(FinalBossCard boss) {
        testPlayer.getDungeon().setBossCard(boss);
        testPlayer.getDungeon().getBossCard().getEffect().apply(testPlayer, null, game);
    }

    private void activateRoomCardEffect(RoomCard room, Integer position) {
        Player testPlayer = game.getPlayers().get(0);
        testPlayer.getDungeon().getRoomSlots()[position].setRoom(room);
        game.triggerRoomCardEffect(testPlayer, position);
    }

    private Integer getAmountOfSpellCards() {
        return (int) testPlayer.getHand().stream().filter(x -> x.getClass() == SpellCard.class).count();
    }

    private Integer getAmountOfRoomCards() {
        return (int) testPlayer.getHand().stream().filter(x -> x.getClass() == RoomCard.class).count();
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
        activateRoomCardEffect(botommlessPit, 0);
        assertThat("Incorrect amount of heroes killed", testPlayer.getDungeon().getRoomSlots()[0].getHeroesInRoom().size(), is(2));
        assertThat("The soul of the defeated hero was not given to the player", testPlayer.getSouls(), is(2));
    }

    @Test
    void shouldTriggerTheCrushinatorRoomCardEffect() {
        RoomCard theCrushinator = setUpDummyRoomCard(RoomPassiveTrigger.DESTROY_ANOTHER_ROOM, EffectEnum.ADD_2_DAMAGE_TO_EVERY_ROOM);
        setUpDummyRoomCardInDungeon(RoomType.MONSTER, 1, 1);
        setUpDummyRoomCardInDungeon(RoomType.TRAP, 2, 2);
        setUpDummyRoomCardInDungeon(RoomType.ADVANCED_MONSTER, 3, 3);
        setUpDummyRoomCardInDungeon(RoomType.ADVANCED_TRAP, 4, 4);
        activateRoomCardEffect(theCrushinator, 0);
        assertEquals(3, testPlayer.getDungeon().getRoomSlots()[1].getRoomTrueDamage());
        assertEquals(4, testPlayer.getDungeon().getRoomSlots()[2].getRoomTrueDamage());
        assertEquals(5, testPlayer.getDungeon().getRoomSlots()[3].getRoomTrueDamage());
        assertEquals(6, testPlayer.getDungeon().getRoomSlots()[4].getRoomTrueDamage());
    }

    @Test
    void shouldTriggerVampireBurdelloRoomCardEffect() {
        RoomCard vampireBurdello = setUpDummyRoomCard(RoomPassiveTrigger.HERO_DIES_IN_THIS_ROOM, EffectEnum.CONVERT_A_WOUND_INTO_A_SOUL);
        activateRoomCardEffect(vampireBurdello, 0);
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

    @Test
    void shouldTriggerBoulderRampRoomCardEffect() {
        RoomCard boulderRamp = setUpDummyRoomCard(RoomPassiveTrigger.DESTROY_ANOTHER_ROOM, EffectEnum.DEAL_5_DAMAGE_TO_A_HERO_IN_THIS_ROOM);
        HeroCardStateInDungeon dummyHero1 = setUpDummyHero(TreasureType.BAG, 10,true);
        HeroCardStateInDungeon dummyHero2 = setUpDummyHero(TreasureType.SWORD, 10,true);
        HeroCardStateInDungeon dummyHero3 = setUpDummyHero(TreasureType.CROSS, 10,true);
        testPlayer.getDungeon().getRoomSlots()[0].getHeroesInRoom().add(dummyHero1);
        testPlayer.getDungeon().getRoomSlots()[0].getHeroesInRoom().add(dummyHero2);
        testPlayer.getDungeon().getRoomSlots()[0].getHeroesInRoom().add(dummyHero3);
        activateRoomCardEffect(boulderRamp, 0);
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

    @Test
    void shouldTriggerDarkAltarRoomCardEffect() {
        for (Player p: game.getPlayers()) game.discardCard(p, 0);
        RoomCard darkAltar = setUpDummyRoomCard(RoomPassiveTrigger.DESTROY_THIS_ROOM, EffectEnum.CHOOSE_CARD_FROM_DISCARD_PILE);
        game.getState().setPhase(GamePhase.START_GAME);
        game.getState().setSubPhase(GameSubPhase.PLACE_FIRST_ROOM);
        game.getState().setActionLimit(1);
        game.getState().setCounter(0);
        activateRoomCardEffect(darkAltar,0);
        assertThat("The state didn't change", game.getState().getPhase(), is(GamePhase.EFFECT));
        assertThat("The substate didn't change", game.getState().getSubPhase(), is(GameSubPhase.CHOOSE_A_CARD_FROM_DISCARD_PILE));
        assertThat("The action counter wasn't correctly updated", game.getState().getCounter(), is(0));
        assertThat("The limit wasn't correctly updated", game.getState().getActionLimit(), is(1));
        List<Card> expectedHand = new ArrayList<>(testPlayer.getHand());
        Card chosenCard = game.getDiscardPile().get(0);
        game.makeChoice(0);
        expectedHand.add(chosenCard);
        assertEquals(expectedHand, testPlayer.getHand());
        assertThat("The state didn't change", game.getState().getPhase(), is(GamePhase.START_GAME));
        assertThat("The substate didn't change", game.getState().getSubPhase(), is(GameSubPhase.PLACE_FIRST_ROOM));
        assertThat("The limit wasn't correctly updated", game.getState().getActionLimit(), is(1));
        assertThat("The action counter wasn't correctly updated", game.getState().getCounter(), is(0));
    }

    @Test
    void shouldTriggerDragonHatcheryRoomCardEffect() {
        // This card doesn't have any effect
    }

    @Test
    void shouldTriggerNeandertalCaveRoomCardEffect() {
        // This one was tested in shouldCheckPlaceableRoomInDungeonPosition()
    }

    @Test
    void shouldTriggerOpenGraveRoomCardEffect() {
        RoomCard openGrave = setUpDummyRoomCard(RoomPassiveTrigger.HERO_DIES_IN_THIS_ROOM, EffectEnum.CHOOSE_ROOM_CARD_FROM_DISCARD_PILE);
        game.discardAllCards(game.getPlayers().get(2));
        game.getState().setPhase(GamePhase.START_GAME);
        game.getState().setSubPhase(GameSubPhase.PLACE_FIRST_ROOM);
        game.getState().setActionLimit(1);
        game.getState().setCounter(0);
        activateRoomCardEffect(openGrave,0);
        assertThat("The state didn't change", game.getState().getPhase(), is(GamePhase.EFFECT));
        assertThat("The substate didn't change", game.getState().getSubPhase(), is(GameSubPhase.CHOOSE_A_ROOM_CARD_FROM_DISCARD_PILE));
        assertThat("The action counter wasn't correctly updated", game.getState().getCounter(), is(0));
        assertThat("The limit wasn't correctly updated", game.getState().getActionLimit(), is(1));
        List<Card> expectedHand = new ArrayList<>(testPlayer.getHand());
        RoomCard chosenRoomCard = (RoomCard) game.getDiscardPile().stream().filter(x -> x.getClass() == RoomCard.class).findFirst().orElse(null);
        game.makeChoice(game.getDiscardPile().indexOf(chosenRoomCard));
        expectedHand.add(chosenRoomCard);
        assertEquals(expectedHand, testPlayer.getHand());
        assertThat("The state didn't change", game.getState().getPhase(), is(GamePhase.START_GAME));
        assertThat("The substate didn't change", game.getState().getSubPhase(), is(GameSubPhase.PLACE_FIRST_ROOM));
        assertThat("The limit wasn't correctly updated", game.getState().getActionLimit(), is(1));
        assertThat("The action counter wasn't correctly updated", game.getState().getCounter(), is(0));
    }

    @Test
    void shouldTriggerRecyclingCenterRoomCardEffect() {
        RoomCard recyclingCenter = setUpDummyRoomCard(RoomPassiveTrigger.DESTROY_ANOTHER_ROOM, EffectEnum.DRAW_2_ROOM_CARDS);
        Integer priorAmountOfRoomCards = getAmountOfRoomCards();
        activateRoomCardEffect(recyclingCenter, 0);
        Integer postAmountOfRoomCards = getAmountOfRoomCards();
        assertEquals(priorAmountOfRoomCards + 2, postAmountOfRoomCards);
    }

    @Test
    void shouldTriggerLigersDenRoomCardEffect() {
        RoomCard ligersDen = setUpDummyRoomCard(RoomPassiveTrigger.USE_SPELL_CARD, EffectEnum.DRAW_A_SPELL_CARD);
        Integer priorAmountOfSpellCards = getAmountOfSpellCards();
        activateRoomCardEffect(ligersDen, 0);
        Integer postAmountOfSpellCards = getAmountOfSpellCards();
        assertEquals(priorAmountOfSpellCards + 1, postAmountOfSpellCards);
    }

    @Test
    void shouldTriggerGoblinArmoryRoomCardEffect() {
        RoomCard goblinArmory = setUpDummyRoomCard(RoomPassiveTrigger.ADD_EXTRA_ROOM_DAMAGE, EffectEnum.ADD_1_DAMAGE_TO_ADYACENT_MONSTER_ROOMS);
        setUpDummyRoomCardInDungeon(RoomType.MONSTER, 1, 1);
        setUpDummyRoomCardInDungeon(RoomType.TRAP, 2, 2);
        activateRoomCardEffect(goblinArmory, 0);
        assertEquals(2, testPlayer.getDungeon().getRoomSlots()[1].getRoomTrueDamage());
        assertEquals(2, testPlayer.getDungeon().getRoomSlots()[2].getRoomTrueDamage());
        setUpDummyRoomCardInDungeon(RoomType.MONSTER, 1, 2);
        setUpDummyRoomCardInDungeon(RoomType.TRAP, 2, 1);
        activateRoomCardEffect(goblinArmory, 0);
        assertEquals(2, testPlayer.getDungeon().getRoomSlots()[1].getRoomTrueDamage());
        assertEquals(1, testPlayer.getDungeon().getRoomSlots()[2].getRoomTrueDamage());
    }

    @Test
    void shouldTriggerGolemFactoryRoomCardEffect() {
        RoomCard golemFactory = setUpDummyRoomCard(RoomPassiveTrigger.HERO_DIES_IN_THIS_ROOM, EffectEnum.DRAW_A_ROOM_CARD);
        Integer priorAmountOfRoomCards = getAmountOfRoomCards();
        activateRoomCardEffect(golemFactory, 0);
        Integer postAmountOfRoomCards = getAmountOfRoomCards();
        assertEquals(priorAmountOfRoomCards + 1, postAmountOfRoomCards);
    }

    @Test
    void shouldTriggerJackpotStashRoomCardEffect() {
        RoomCard jackpotStash = setUpDummyRoomCard(RoomPassiveTrigger.DESTROY_THIS_ROOM, EffectEnum.DOUBLE_DUNGEON_TREASURE_VALUE);
        activateRoomCardEffect(jackpotStash, 0);
        // The modified amount of treasure is already tested in shouldGetTreasureAmount() in DungeonTest
        assertTrue(testPlayer.getDungeon().getJackpotStashEffectActivated());
    }

    @Test
    void shouldTriggerDarkLaboratoryRoomCardEffect() {
        game.getState().setPhase(GamePhase.START_GAME);
        game.getState().setSubPhase(GameSubPhase.PLACE_FIRST_ROOM);
        game.getState().setActionLimit(1);
        game.getState().setCounter(0);
        RoomCard darkLaboratory = setUpDummyRoomCard(RoomPassiveTrigger.BUILD_THIS_ROOM, EffectEnum.DRAW_2_SPELL_CARDS_AND_DISCARD_1_SPELL_CARD);
        Integer priorAmountOfSpellCards = getAmountOfSpellCards();
        activateRoomCardEffect(darkLaboratory, 0);
        Integer postAmountOfSpellCardsBeforeDiscard = getAmountOfSpellCards();
        assertEquals(priorAmountOfSpellCards + 2, postAmountOfSpellCardsBeforeDiscard);
        assertThat("The state didn't change", game.getState().getPhase(), is(GamePhase.EFFECT));
        assertThat("The substate didn't change", game.getState().getSubPhase(), is(GameSubPhase.DISCARD_A_SPELL_CARD));
        assertThat("The action counter wasn't correctly updated", game.getState().getCounter(), is(0));
        assertThat("The limit wasn't correctly updated", game.getState().getActionLimit(), is(1));
        Integer randomSpellCard = testPlayer.getHand().size() - 1;
        game.makeChoice(randomSpellCard);
        Integer postAmountOfSpellCardsAfterDiscard = getAmountOfSpellCards();
        assertEquals(postAmountOfSpellCardsBeforeDiscard - 1, postAmountOfSpellCardsAfterDiscard);
        assertThat("The state didn't change", game.getState().getPhase(), is(GamePhase.START_GAME));
        assertThat("The substate didn't change", game.getState().getSubPhase(), is(GameSubPhase.PLACE_FIRST_ROOM));
        assertThat("The limit wasn't correctly updated", game.getState().getActionLimit(), is(1));
        assertThat("The action counter wasn't correctly updated", game.getState().getCounter(), is(0));
    }

    @Test
    void shouldTriggerMonstrousMonumentRoomCardEffect() {
        for (Player p: game.getPlayers()) game.discardAllCards(p);
        game.getState().setPhase(GamePhase.START_GAME);
        game.getState().setSubPhase(GameSubPhase.PLACE_FIRST_ROOM);
        game.getState().setActionLimit(1);
        game.getState().setCounter(0);
        RoomCard monstrousMonument = setUpDummyRoomCard(RoomPassiveTrigger.BUILD_THIS_ROOM, EffectEnum.CHOOSE_MONSTER_ROOM_CARD_FROM_DISCARD_PILE);
        activateRoomCardEffect(monstrousMonument,0);
        assertThat("The state didn't change", game.getState().getPhase(), is(GamePhase.EFFECT));
        assertThat("The substate didn't change", game.getState().getSubPhase(), is(GameSubPhase.CHOOSE_A_MONSTER_ROOM_CARD_FROM_DISCARD_PILE));
        assertThat("The action counter wasn't correctly updated", game.getState().getCounter(), is(0));
        assertThat("The limit wasn't correctly updated", game.getState().getActionLimit(), is(1));
        List<Card> expectedHand = new ArrayList<>(testPlayer.getHand());
        RoomCard chosenMonsterCard = game.getDiscardPile().stream().filter(x -> x.getClass() == RoomCard.class).map(x -> (RoomCard) x).filter(x -> x.isMonsterType()).findFirst().orElse(null);
        expectedHand.add(chosenMonsterCard);
        game.makeChoice(game.getDiscardPile().indexOf(chosenMonsterCard));
        assertEquals(expectedHand, testPlayer.getHand());
        assertThat("The state didn't change", game.getState().getPhase(), is(GamePhase.START_GAME));
        assertThat("The substate didn't change", game.getState().getSubPhase(), is(GameSubPhase.PLACE_FIRST_ROOM));
        assertThat("The limit wasn't correctly updated", game.getState().getActionLimit(), is(1));
        assertThat("The action counter wasn't correctly updated", game.getState().getCounter(), is(0));
    }

    @Test
    void shouldTriggerBeastMenagerieRoomCardEffect() {
        RoomCard beastMenagerie = setUpDummyRoomCard(RoomPassiveTrigger.BUILD_MONSTER_ROOM, EffectEnum.DRAW_A_ROOM_CARD);
        Integer priorAmountOfRoomCards = getAmountOfRoomCards();
        activateRoomCardEffect(beastMenagerie, 0);
        Integer postAmountOfRoomCards = getAmountOfRoomCards();
        assertEquals(priorAmountOfRoomCards + 1, postAmountOfRoomCards);
    }

    @Test
    void shouldTriggerBrainsuckerHiveRoomCardEffect() {
        RoomCard brainsuckerHive = setUpDummyRoomCard(RoomPassiveTrigger.HERO_DIES_IN_THIS_ROOM, EffectEnum.DRAW_A_SPELL_CARD);
        Integer priorAmountOfSpellCards = getAmountOfSpellCards();
        activateRoomCardEffect(brainsuckerHive, 0);
        Integer postAmountOfRoomCards = getAmountOfSpellCards();
        assertEquals(priorAmountOfSpellCards + 1, postAmountOfRoomCards);
    }

    @Test
    void shouldTriggerDizzygasHallwayRoomCardEffect() {
        RoomCard dizzygasHallway = setUpDummyRoomCard(RoomPassiveTrigger.ADD_EXTRA_ROOM_DAMAGE,EffectEnum.ADD_2_DAMAGE_TO_NEXT_ROOM_IF_IT_IS_A_TRAP_ROOM);

        DungeonRoomSlot roomSlot = game.getCurrentPlayer().getDungeon().getRoomSlots()[0];

        setUpDummyRoomCardInDungeon(RoomType.TRAP,1,0);
        Integer previousDamage = roomSlot.getRoomTrueDamage();

        activateRoomCardEffect(dizzygasHallway, 1);
        assertThat("Did not add damage to trap card",
            roomSlot.getRoomTrueDamage(),is(previousDamage+2));

        setUpDummyRoomCardInDungeon(RoomType.MONSTER,1,0);
        previousDamage = roomSlot.getRoomTrueDamage();
        activateRoomCardEffect(dizzygasHallway, 0);
        assertThat("Should not add damage to monster card",
            roomSlot.getRoomTrueDamage(),is(previousDamage));
    }

    @Ignore
    @Test
    void shouldTriggerMinotaursMazeRoomCardEffect() {

    }

    @Ignore
    @Test
    void shouldTriggerGiantSizeSpellCardEffect() {
        SpellCard giantSize = setUpDummySpellCard(EffectEnum.ADD_3_DAMAGE_TO_A_CHOSEN_MONSTER_ROOM);
        game.triggerSpellCardEffect(giantSize);
        assertThat("Phase did not change",game.getState().getPhase(),is(GamePhase.EFFECT));
        assertThat("Not offering dungeon as choice",game.getState().getSubPhase().getChoice(game),
            is(game.getCurrentPlayer().getDungeon().getRooms()));
        setUpDummyRoomCardInDungeon(RoomType.MONSTER,0,0);

        Dungeon dungeon = game.getCurrentPlayer().getDungeon();
        Integer previousDamageSlot1 = dungeon.getRoomSlots()[0].getRoomTrueDamage();

        game.makeChoice(0);
        assertThat("Did not add damage to specified room",
            dungeon.getRoomSlots()[0].getRoomTrueDamage(),is(previousDamageSlot1+3));
    }

    @Test
    void shouldTriggerSoulHarvestSpellCardEffect() {
        SpellCard soulHarvest = setUpDummySpellCard(EffectEnum.TRADE_A_SOUL_FOR_2_SPELL_CARDS);
        Integer priorAmountOfSpellCards = getAmountOfSpellCards();
        game.triggerSpellCardEffect(soulHarvest);
        Integer postAmountOfSpellCards = getAmountOfSpellCards();
        assertThat("The player amount of souls was incorrectly tampered", testPlayer.getSouls(), is(0));
        assertThat("A player without souls received spellcards", postAmountOfSpellCards, is(priorAmountOfSpellCards));
        testPlayer.setSouls(4);
        game.triggerSpellCardEffect(soulHarvest);
        postAmountOfSpellCards = getAmountOfSpellCards();
        assertThat("The player didn't sacrifice a soul", testPlayer.getSouls(), is(3));
        assertThat("The player didn't receive new spellcards", postAmountOfSpellCards, is(priorAmountOfSpellCards + 2));
    }

    @Test
    void shouldTriggerPrincessInPerilSpellCardEffect() {
        SpellCard princessInPeril = setUpDummySpellCard(EffectEnum.LURE_A_CHOSEN_HERO_FROM_CITY_TO_DUNGEON);
        game.triggerSpellCardEffect(princessInPeril);

        HeroCard hero = new HeroCard();
        hero.setName("Bobby Tables");
        game.getCity().add(hero);

        assertThat("Phase did not change",game.getState().getPhase(),is(GamePhase.EFFECT));
        assertThat("Not offering city pile",game.getChoice(),is(game.getCity()));

        setUpDummyRoomCardInDungeon(RoomType.MONSTER,0,0);
        DungeonRoomSlot slot = game.getCurrentPlayer().getDungeon().getRoomSlots()[0];
        Integer previousHeroesInDungeon = slot.getHeroesInRoom().size();
        Integer previousHeroesInCity = game.getCity().size();

        game.makeChoice(game.getCity().size()-1);
        assertThat("Did not remove hero from city", game.getCity().size(),is(previousHeroesInCity-1));
        assertThat("Did not add hero to dungeon",slot.getHeroesInRoom().size(),is(previousHeroesInDungeon+1));
    }

    @Ignore
    @Test
    void shouldTriggerMotivationSpellCardEffect() {
        SpellCard motivation = setUpDummySpellCard(EffectEnum.BUILD_ANOTHER_ROOM_IF_ANOTHER_PLAYER_HAS_MORE_ROOMS);
        game.triggerSpellCardEffect(motivation);
        //TODO
    }

    @Ignore
    @Test
    void shouldTriggerExhaustionSpellCardEffect() {
        Dungeon dungeon = testPlayer.getDungeon();
        setUpDummyRoomCardInDungeon(RoomType.MONSTER,0,0);
        setUpDummyRoomCardInDungeon(RoomType.MONSTER,0,1);
        HeroCardStateInDungeon hero = setUpDummyHero(TreasureType.BAG,4,false);
        dungeon.getRoomSlots()[0].addHero(hero);

        game.getState().triggerSpecialCardEffectState(GameSubPhase.DEAL_X_DAMAGE_TO_HERO_IN_DUNGEON);
        assertThat("Did not change phase",game.getState().getPhase(),is(GamePhase.EFFECT));
        assertThat("Not offering dungeon rooms as choice", game.getChoice(), is(dungeon.getRooms()));
        game.makeChoice(0);
        assertThat("Not offering heroes as choice",game.getChoice(),contains(hero.getHeroCard()));
        game.makeChoice(0);
        assertThat("Did not deal expected damage to hero",hero.getHealthInDungeon(),is(2));
    }

    @Test
    void shouldTriggerAnnihilatorSpellCardEffect() {
        SpellCard annihilator = setUpDummySpellCard(EffectEnum.ADD_3_DAMAGE_TO_A_CHOSEN_TRAP_ROOM);

        setUpDummyRoomCardInDungeon(RoomType.TRAP,0,0);
        setUpDummyRoomCardInDungeon(RoomType.MONSTER,0,1);

        game.triggerSpellCardEffect(annihilator);
        assertThat("Did not change phase",game.getState().getPhase(),is(GamePhase.EFFECT));
        assertThat("Not offering dungeon rooms as choice",game.getChoice(),is(testPlayer.getDungeon().getRooms()));
        assertThat("Should not be able to choose a monster room",
            game.getState().getSubPhase().isValidChoice(1,game),is(false));
        assertThat("Should be able to choose a trap room",
            game.getState().getSubPhase().isValidChoice(0,game),is(true));
        game.makeChoice(0);
        assertThat("Did not boost damage of chosen room",
            testPlayer.getDungeon().getRoomSlots()[0].getRoomTrueDamage(),is(3));


    }

    void shouldTrigggerCaveInSpellCardEffect() {
        //TODO
    }

    @Test
    void shouldTriggerKoboldStrikeSpellCardEffect() {
        SpellCard koboldStrike = setUpDummySpellCard(EffectEnum.SKIP_BUILD_PHASE);
        game.getState().setPhase(GamePhase.BUILD);
        setUpDummyRoomCardInDungeon(RoomType.MONSTER,0,0);
        setUpDummyRoomCardInDungeon(RoomType.MONSTER,0,1);
        testPlayer.getDungeon().getRoomSlots()[1].setIsVisible(false);

        game.triggerSpellCardEffect(koboldStrike);
        assertThat("Did not remove newly-built room",testPlayer.getDungeon().getRoom(1),nullValue());
        assertThat("Did not skip phase",game.getState().getPhase(),not(is(GamePhase.BUILD)));

    }

    @Test
    void shouldTriggerTeleportationSpellCardEffect() {

        SpellCard teleportation = setUpDummySpellCard(EffectEnum.SEND_HERO_TO_FIRST_ROOM);
        Dungeon dungeon = testPlayer.getDungeon();
        setUpDummyRoomCardInDungeon(RoomType.MONSTER,0,0);
        setUpDummyRoomCardInDungeon(RoomType.MONSTER,0,1);
        HeroCardStateInDungeon hero = setUpDummyHero(TreasureType.BAG,4,false);
        dungeon.getRoomSlots()[0].addHero(hero);

        game.triggerSpellCardEffect(teleportation);
        assertThat("Did not change phase",game.getState().getPhase(),is(GamePhase.EFFECT));
        assertThat("Not offering dungeon rooms as choice", game.getChoice(), is(dungeon.getRooms()));
        game.makeChoice(0);
        assertThat("Not offering heroes as choice",game.getChoice(),contains(hero.getHeroCard()));
        game.makeChoice(0);
        assertThat("Did not remove hero from room",dungeon.getRoomSlots()[0].getHeroesInRoom(),not(contains(hero)));
        assertThat("Hero was not moved to the first room",dungeon.getRoomSlots()[1].getHeroesInRoom(),contains(hero));
    }

    @Test
    void shouldTriggerJeopardySpellCardEffect() {
        SpellCard jeopardy = setUpDummySpellCard(EffectEnum.EVERY_PLAYER_RESETS_THEIR_HAND);
        for (int i = 0; i < 2; i ++) {
            game.discardCard(game.getPlayers().get(0), 0);
            game.getNewRoomCard(game.getPlayers().get(1));
            game.getNewSpellCard(game.getPlayers().get(2));
        }
        game.triggerSpellCardEffect(jeopardy);
        for (Player p: game.getPlayers()) {
            testPlayer = p;
            assertThat("A player has an incorrect amount of room cards", getAmountOfRoomCards(), is(2));
            assertThat("A player has an incorrect amount of spell cards", getAmountOfSpellCards(), is(1));
        }
    }

    @Test
    void shouldTriggerBelladonaLevelUpBossCardEffect() {
        FinalBossCard belladona = setUpFinalBossCard(EffectEnum.CONVERT_A_WOUND_INTO_A_SOUL);
        levelUpDungeonFinalBoss(belladona);
        assertThat("Player converted unexisting damage", testPlayer.getSouls(), is(0));
        assertThat("Player healed unexisting wound", testPlayer.getHealth(), is(5));
        testPlayer.setHealth(2);
        testPlayer.setSouls(2);
        levelUpDungeonFinalBoss(belladona);
        assertThat("Player didn't get a soul", testPlayer.getSouls(), is(3));
        assertThat("Player didn't heal a wound", testPlayer.getHealth(), is(3));
    }

    @Test
    void shouldTriggerTheBrothersWiseLevelUpBossCardEffect() {
        FinalBossCard brothersWise = setUpFinalBossCard(EffectEnum.CHOOSE_SPELL_CARD_FROM_SPELL_PILE);

        levelUpDungeonFinalBoss(brothersWise);
        assertThat("Phase did not change",game.getState().getPhase(),is(GamePhase.EFFECT));

        List<SpellCard> spellPile = game.getSpellPile();
        assertThat("Not offering spell pile as choice",game.getChoice(),is(spellPile));

        Card spell = spellPile.get(0);
        int cardsInHand = game.getCurrentPlayerHand().size();
        game.makeChoice(0);
        assertThat("Did not give a card to the player",game.getCurrentPlayerHand().size(),is(cardsInHand+1));
        assertThat("Card given is not the specified one",game.getCurrentPlayerHand().get(cardsInHand),is(spell));
    }

    @Test
    void shouldTriggerXyzaxLevelUpBossCardEffect() {
        FinalBossCard xyzax = setUpFinalBossCard(EffectEnum.CHOOSE_2_CARDS_FROM_DISCARD_PILE);

        levelUpDungeonFinalBoss(xyzax);
        assertThat("Phase did not change",game.getState().getPhase(),is(GamePhase.EFFECT));

        Card card = new RoomCard();
        game.getDiscardPile().add(0,card);
        List<Card> discardPile = game.getDiscardPile();
        assertThat("Not offering discard pile as choice",game.getChoice(),is(discardPile));

        int cardsInHand = game.getCurrentPlayerHand().size();
        game.makeChoice(0);
        assertThat("Did not give a card to the player",game.getCurrentPlayerHand().size(),is(cardsInHand+1));
        assertThat("Card given is not the specified one",game.getCurrentPlayerHand().get(cardsInHand),sameInstance(card));
    }

    @Test
    void shouldTriggerCerebellusLevelUpBossCardEffect() {
        FinalBossCard cerebellus = setUpFinalBossCard(EffectEnum.DRAW_3_SPELL_CARDS_AND_DISCARD_1_SPELL_CARD);

        levelUpDungeonFinalBoss(cerebellus);
        assertThat("Phase did not change",game.getState().getPhase(),is(GamePhase.EFFECT));

        List<Card> discardPile = game.getCurrentPlayerHand();
        assertThat("Not offering player hand as choice",game.getChoice(),is(discardPile));

        int cardsInHand = game.getCurrentPlayerHand().size();
        int cardsInDiscardPile = game.getDiscardPile().size();
        Card card = game.getCurrentPlayerHand().get(cardsInHand-1);
        game.makeChoice(cardsInHand-1);
        assertThat("Did not remove a card from the player",game.getCurrentPlayerHand().size(),is(cardsInHand-1));
        assertThat("Did not add card to discard pile", game.getDiscardPile().size(),is(cardsInDiscardPile+1));
        assertThat("Card discarded is not the specified one",game.getDiscardPile().get(cardsInDiscardPile),sameInstance(card));
    }

    @Test
    void shouldTriggerKingCroakLevelUpBossCardEffect() {
        FinalBossCard kingCroak = setUpFinalBossCard(EffectEnum.BUILD_AN_ADVANCED_MONSTER_ROOM_CHOSEN_FROM_THE_ROOM_PILE_OR_DISCARD_PILE);

        levelUpDungeonFinalBoss(kingCroak);
        assertThat("Phase did not change",game.getState().getPhase(),is(GamePhase.EFFECT));
        List<Card> expectedChoice = List.of(Card.DISCARD_PILE_CARD,Card.ROOM_PILE_CARD);
        assertThat("Not offering choice between piles", game.getChoice(), is(expectedChoice));

        RoomCard advancedMonster = new RoomCard();
        advancedMonster.setRoomType(RoomType.ADVANCED_MONSTER);
        advancedMonster.setTreasure("1000");
        advancedMonster.setName("An advanced monster");
        RoomCard notAdvancedMonster = new RoomCard();
        notAdvancedMonster.setRoomType(RoomType.MONSTER);
        notAdvancedMonster.setTreasure("0100");
        notAdvancedMonster.setName("A not advanced monster");
        RoomCard matchingTreasureCard = new RoomCard();
        matchingTreasureCard.setName("A room with matching treasure");
        matchingTreasureCard.setTreasure("1000");
        game.getDiscardPile().add(advancedMonster);
        game.getDiscardPile().add(notAdvancedMonster);
        game.getRoomPile().add(advancedMonster);
        game.getRoomPile().add(notAdvancedMonster);
        game.getCurrentPlayer().getDungeon().replaceDungeonRoom(matchingTreasureCard,0);
        game.getCurrentPlayer().getDungeon().replaceDungeonRoom(notAdvancedMonster,1);
        game.makeChoice(0);
        assertThat("Did not store choice",game.getPreviousChoices(),not(empty()));
        assertThat("Not offering discard pile", game.getChoice(),is(game.getDiscardPile()));
        assertThat("Shouldn't be able to choose a not advanced card",
            game.getState().getSubPhase().isValidChoice(game.getDiscardPile().size()-1,game),is(false));
        assertThat("Should be able to choose an advanced card",
            game.getState().getSubPhase().isValidChoice(game.getDiscardPile().size()-2,game),is(true));
        game.makeChoice(-1);
        assertThat("Not rollbacking choice correctly",game.getChoice(),is(expectedChoice));
        game.makeChoice(1);
        assertThat("Not offering room pile", game.getChoice(),is(game.getRoomPile()));
        game.makeChoice(game.getRoomPile().size()-2);
        expectedChoice = Arrays.stream(game.getCurrentPlayer().getDungeon().getRoomSlots()).map(slot->slot.getRoom())
                .collect(Collectors.toList());
        assertThat("Not showing dungeon",game.getChoice(),is(expectedChoice));
        assertThat("Should not be able to build on a room with mismatching treasure",
            game.getState().getSubPhase().isValidChoice(1,game),is(false));
        assertThat("Should be able to build on a room with matching treasure",
            game.getState().getSubPhase().isValidChoice(0,game),is(true));

        game.makeChoice(0);
        assertThat("Should have built specified room",
            game.getCurrentPlayer().getDungeon().getRoomSlots()[0].getRoom(),is(advancedMonster));
    }

    @Test
    void shouldTriggerSeduciaLevelUpBossCardEffect() {
        FinalBossCard seducia = setUpFinalBossCard(EffectEnum.LURE_A_CHOSEN_HERO_FROM_CITY_OR_HERO_PILE_TO_DUNGEON);

        levelUpDungeonFinalBoss(seducia);
        assertThat("Phase did not change",game.getState().getPhase(),is(GamePhase.EFFECT));
        List<Card> expectedChoice = List.of(Card.CITY_PILE_CARD,Card.HERO_PILE_CARD);
        assertThat("Not offering choice between piles", game.getChoice(), is(expectedChoice));

        game.makeChoice(0);
        assertThat("Not offering city pile",game.getChoice(),is(game.getCity()));
        game.makeChoice(-1);
        assertThat("Not rollbacking choice correctly",game.getChoice(),is(expectedChoice));
        game.makeChoice(1);
        assertThat("Not offering hero pile",game.getChoice(),is(game.getHeroPile()));

        HeroCard dummy = new HeroCard();
        dummy.setName("Dummy");
        game.getHeroPile().add(dummy);
        Integer cardsInPile = game.getHeroPile().size();
        RoomCard card = new RoomCard();
        Dungeon dungeon = game.getCurrentPlayer().getDungeon();
        dungeon.replaceDungeonRoom(card,0);
        Integer heroesBefore = dungeon.getRoomSlots()[0].getHeroesInRoom().size();

        game.makeChoice(cardsInPile-1);
        assertThat("Did not remove card from the pile",game.getHeroPile().size(),is(cardsInPile-1));
        assertThat("Did not move hero to dungeon",
            dungeon.getRoomSlots()[0].getHeroesInRoom().size(),is(heroesBefore+1));

    }

    @Test
    void shouldTriggerCleopatraLevelUpBossCardEffect() {
        FinalBossCard cleopatra = setUpFinalBossCard(EffectEnum.BUILD_AN_ADVANCED_TRAP_ROOM_CHOSEN_FROM_THE_ROOM_PILE_OR_DISCARD_PILE);

        levelUpDungeonFinalBoss(cleopatra);
        assertThat("Phase did not change",game.getState().getPhase(),is(GamePhase.EFFECT));
        List<Card> expectedChoice = List.of(Card.DISCARD_PILE_CARD,Card.ROOM_PILE_CARD);
        assertThat("Not offering choice between piles", game.getChoice(), is(expectedChoice));

        RoomCard advancedTrap = new RoomCard();
        advancedTrap.setRoomType(RoomType.ADVANCED_TRAP);
        advancedTrap.setTreasure("1000");
        advancedTrap.setName("An advanced trap");
        RoomCard notAdvancedTrap = new RoomCard();
        notAdvancedTrap.setRoomType(RoomType.TRAP);
        notAdvancedTrap.setTreasure("0100");
        notAdvancedTrap.setName("A not advanced monster");
        RoomCard matchingTreasureCard = new RoomCard();
        matchingTreasureCard.setName("A room with matching treasure");
        matchingTreasureCard.setTreasure("1000");
        game.getDiscardPile().add(advancedTrap);
        game.getDiscardPile().add(notAdvancedTrap);
        game.getRoomPile().add(advancedTrap);
        game.getRoomPile().add(notAdvancedTrap);
        game.getCurrentPlayer().getDungeon().replaceDungeonRoom(matchingTreasureCard,0);
        game.getCurrentPlayer().getDungeon().replaceDungeonRoom(notAdvancedTrap,1);
        game.makeChoice(0);
        assertThat("Did not store choice",game.getPreviousChoices(),not(empty()));
        assertThat("Not offering discard pile", game.getChoice(),is(game.getDiscardPile()));
        assertThat("Shouldn't be able to choose a not advanced card",
            game.getState().getSubPhase().isValidChoice(game.getDiscardPile().size()-1,game),is(false));
        assertThat("Should be able to choose an advanced card",
            game.getState().getSubPhase().isValidChoice(game.getDiscardPile().size()-2,game),is(true));
        game.makeChoice(-1);
        assertThat("Not rollbacking choice correctly",game.getChoice(),is(expectedChoice));
        game.makeChoice(1);
        assertThat("Not offering room pile", game.getChoice(),is(game.getRoomPile()));
        game.makeChoice(game.getRoomPile().size()-2);
        expectedChoice = Arrays.stream(game.getCurrentPlayer().getDungeon().getRoomSlots()).map(slot->slot.getRoom())
            .collect(Collectors.toList());
        assertThat("Not showing dungeon",game.getChoice(),is(expectedChoice));
        assertThat("Should not be able to build on a room with mismatching treasure",
            game.getState().getSubPhase().isValidChoice(1,game),is(false));
        assertThat("Should be able to build on a room with matching treasure",
            game.getState().getSubPhase().isValidChoice(0,game),is(true));

        game.makeChoice(0);
        assertThat("Should have built specified room",
            game.getCurrentPlayer().getDungeon().getRoomSlots()[0].getRoom(),is(advancedTrap));
    }

}
