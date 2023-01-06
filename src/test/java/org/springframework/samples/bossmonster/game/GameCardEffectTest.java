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

    }

    void shouldTriggerSeduciaLevelUpBossCardEffect() {

    }

    void shouldTriggerCleopatraLevelUpBossCardEffect() {

    }

}
