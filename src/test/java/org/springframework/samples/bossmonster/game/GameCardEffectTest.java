package org.springframework.samples.bossmonster.game;

import java.util.ArrayList;
import java.util.List;

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

    private void activateRoomCardEffect(RoomCard room) {
        Player testPlayer = game.getPlayers().get(0);
        testPlayer.getDungeon().getRoomSlots()[0].setRoom(room);
        game.triggerRoomCardEffect(testPlayer, 0);
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
        activateRoomCardEffect(botommlessPit);
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
        activateRoomCardEffect(theCrushinator);
        assertEquals(3, testPlayer.getDungeon().getRoomSlots()[1].getRoomTrueDamage());
        assertEquals(4, testPlayer.getDungeon().getRoomSlots()[2].getRoomTrueDamage());
        assertEquals(5, testPlayer.getDungeon().getRoomSlots()[3].getRoomTrueDamage());
        assertEquals(6, testPlayer.getDungeon().getRoomSlots()[4].getRoomTrueDamage());
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
        Integer priorAmountOfRoomCards = getAmountOfRoomCards();
        activateRoomCardEffect(recyclingCenter);
        Integer postAmountOfRoomCards = getAmountOfRoomCards();
        assertEquals(priorAmountOfRoomCards + 2, postAmountOfRoomCards);
    }

    @Test
    void shouldTriggerLigersDenRoomCardEffect() {
        RoomCard ligersDen = setUpDummyRoomCard(RoomPassiveTrigger.USE_SPELL_CARD, EffectEnum.DRAW_A_SPELL_CARD);
        Integer priorAmountOfSpellCards = getAmountOfSpellCards();
        activateRoomCardEffect(ligersDen);
        Integer postAmountOfSpellCards = getAmountOfSpellCards();
        assertEquals(priorAmountOfSpellCards + 1, postAmountOfSpellCards);
    }

    @Test
    void shouldTriggerGoblinArmoryRoomCardEffect() {
        RoomCard goblinArmory = setUpDummyRoomCard(RoomPassiveTrigger.ADD_EXTRA_ROOM_DAMAGE, EffectEnum.ADD_1_DAMAGE_TO_ADYACENT_MONSTER_ROOMS);
        setUpDummyRoomCardInDungeon(RoomType.MONSTER, 1, 1);
        setUpDummyRoomCardInDungeon(RoomType.TRAP, 2, 2);
        activateRoomCardEffect(goblinArmory);
        assertEquals(2, testPlayer.getDungeon().getRoomSlots()[1].getRoomTrueDamage());
        assertEquals(2, testPlayer.getDungeon().getRoomSlots()[2].getRoomTrueDamage());
        setUpDummyRoomCardInDungeon(RoomType.MONSTER, 1, 2);
        setUpDummyRoomCardInDungeon(RoomType.TRAP, 2, 1);
        activateRoomCardEffect(goblinArmory);
        assertEquals(2, testPlayer.getDungeon().getRoomSlots()[1].getRoomTrueDamage());
        assertEquals(1, testPlayer.getDungeon().getRoomSlots()[2].getRoomTrueDamage());
    }

    @Test
    void shouldTriggerGolemFactoryRoomCardEffect() {
        RoomCard golemFactory = setUpDummyRoomCard(RoomPassiveTrigger.HERO_DIES_IN_THIS_ROOM, EffectEnum.DRAW_A_ROOM_CARD);
        Integer priorAmountOfRoomCards = getAmountOfRoomCards();
        activateRoomCardEffect(golemFactory);
        Integer postAmountOfRoomCards = getAmountOfRoomCards();
        assertEquals(priorAmountOfRoomCards + 1, postAmountOfRoomCards);
    }

    @Test
    void shouldTriggerJackpotStashRoomCardEffect() {
        RoomCard jackpotStash = setUpDummyRoomCard(RoomPassiveTrigger.DESTROY_THIS_ROOM, EffectEnum.DOUBLE_DUNGEON_TREASURE_VALUE);
        activateRoomCardEffect(jackpotStash);
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
        activateRoomCardEffect(darkLaboratory);
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

    @Ignore
    @Test
    void shouldTriggerMonstrousMonumentRoomCardEffect() {

    }

    @Test
    void shouldTriggerBeastMenagerieRoomCardEffect() {
        RoomCard beastMenagerie = setUpDummyRoomCard(RoomPassiveTrigger.BUILD_MONSTER_ROOM, EffectEnum.DRAW_A_ROOM_CARD);
        Integer priorAmountOfRoomCards = getAmountOfRoomCards();
        activateRoomCardEffect(beastMenagerie);
        Integer postAmountOfRoomCards = getAmountOfRoomCards();
        assertEquals(priorAmountOfRoomCards + 1, postAmountOfRoomCards);
    }

    @Test
    void shouldTriggerBrainsuckerHiveRoomCardEffect() {
        RoomCard brainsuckerHive = setUpDummyRoomCard(RoomPassiveTrigger.HERO_DIES_IN_THIS_ROOM, EffectEnum.DRAW_A_SPELL_CARD);
        Integer priorAmountOfSpellCards = getAmountOfSpellCards();
        activateRoomCardEffect(brainsuckerHive);
        Integer postAmountOfRoomCards = getAmountOfSpellCards();
        assertEquals(priorAmountOfSpellCards + 1, postAmountOfRoomCards);
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
        SpellCard giantSize = setUpDummySpellCard(EffectEnum.ADD_3_DAMAGE_TO_A_CHOSEN_MONSTER_ROOM);
        game.triggerSpellCardEffect(giantSize);

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

    @Ignore
    @Test
    void shouldTriggerPrincessInPerilSpellCardEffect() {
        SpellCard princessInPeril = setUpDummySpellCard(EffectEnum.LURE_A_CHOSEN_HERO_FROM_CITY_TO_DUNGEON);
        game.triggerSpellCardEffect(princessInPeril);
    }

    @Ignore
    @Test
    void shouldTriggerMotivationSpellCardEffect() {
        SpellCard motivation = setUpDummySpellCard(EffectEnum.BUILD_ANOTHER_ROOM_IF_ANOTHER_PLAYER_HAS_MORE_ROOMS);
        game.triggerSpellCardEffect(motivation);
    }

    @Ignore
    @Test
    void shouldTriggerExhaustionSpellCardEffect() {
        SpellCard exhaustion = setUpDummySpellCard(EffectEnum.DEAL_ROOM_AMOUNT_DAMAGE_TO_HERO);
        game.triggerSpellCardEffect(exhaustion);
    }

    void shouldTriggerAnnihilatorSpellCardEffect() {

    }

    void shouldTrigggerCaveInSpellCardEffect() {

    }

    void shouldTriggerKoboldStrikeSpellCardEffect() {

    }

    void shouldTriggerTeleportationSpellCardEffect() {

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

    void shouldTriggerTheBrothersWiseLevelUpBossCardEffect() {

    }

    void shouldTriggerXyzaxLevelUpBossCardEffect() {

    }

    void shouldTriggerCerebellusLevelUpBossCardEffect() {

    }

    void shouldTriggerKingCroakLevelUpBossCardEffect() {

    }

    void shouldTriggerSeduciaLevelUpBossCardEffect() {

    }

    void shouldTriggerCleopatraLevelUpBossCardEffect() {

    }

}
