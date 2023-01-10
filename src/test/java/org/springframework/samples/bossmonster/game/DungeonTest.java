package org.springframework.samples.bossmonster.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.samples.bossmonster.game.card.TreasureType;
import org.springframework.samples.bossmonster.game.card.finalBoss.FinalBossCard;
import org.springframework.samples.bossmonster.game.card.hero.HeroCard;
import org.springframework.samples.bossmonster.game.card.hero.HeroCardStateInDungeon;
import org.springframework.samples.bossmonster.game.card.room.RoomCard;
import org.springframework.samples.bossmonster.game.card.room.RoomPassiveTrigger;
import org.springframework.samples.bossmonster.game.card.room.RoomType;
import org.springframework.samples.bossmonster.game.dungeon.Dungeon;
import org.springframework.samples.bossmonster.game.dungeon.DungeonRoomSlot;
import org.springframework.samples.bossmonster.game.player.Player;

import static org.hamcrest.Matchers.*;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DungeonTest {

    protected Dungeon dungeon;

    @BeforeEach
    void setUp() {
        dungeon = new Dungeon();
        DungeonRoomSlot[] slots = new DungeonRoomSlot[5];
        slots[0] = setUpFilledRoomSlot("1010", 3, RoomType.ADVANCED_MONSTER, RoomPassiveTrigger.DESTROY_ANOTHER_ROOM, 999);
        slots[1] = setUpFilledRoomSlot("2000", 2, RoomType.ADVANCED_TRAP, RoomPassiveTrigger.USE_SPELL_CARD, 998);
        slots[2] = setUpFilledRoomSlot("0110", 1, RoomType.TRAP, RoomPassiveTrigger.HERO_DIES_IN_THIS_ROOM, 997);
        slots[3] = setUpFilledRoomSlot("1000", 0, RoomType.MONSTER, RoomPassiveTrigger.USE_SPELL_CARD, 4);
        slots[4] = setUpEmptyRoomSlot();
        dungeon.setRoomSlots(slots);
        FinalBossCard boss = new FinalBossCard();
        boss.setTreasure(TreasureType.BOOK);
        dungeon.setBossCard(boss);
        Player player = new Player();
        player.setHealth(5);
        dungeon.setPlayer(player);
        dungeon.setJackpotStashEffectActivated(false);
        dungeon.setGame(new Game());
    }

    DungeonRoomSlot setUpFilledRoomSlot(String treasure, Integer damage, RoomType type, RoomPassiveTrigger trigger, Integer id) {
        DungeonRoomSlot slot = new DungeonRoomSlot();
        RoomCard room = new RoomCard();
        room.setDamage(damage);
        room.setTreasure(treasure);
        room.setRoomType(type);
        room.setPassiveTrigger(trigger);
        room.setId(id);
        slot.setRoom(room);
        slot.setHeroesInRoom(new ArrayList<>());
        return slot;
    }

    DungeonRoomSlot setUpEmptyRoomSlot() {
        DungeonRoomSlot slot = new DungeonRoomSlot();
        slot.setHeroesInRoom(new ArrayList<>());
        return slot;
    }

    HeroCardStateInDungeon setUpComplexDummyHero(Integer currentHealth, Boolean isEpic) {
        HeroCard hero = new HeroCard();
        hero.setIsEpic(isEpic);
        HeroCardStateInDungeon heroInDungeon = new HeroCardStateInDungeon();
        heroInDungeon.setHeroCard(hero);
        heroInDungeon.setHealthInDungeon(currentHealth);
        return heroInDungeon;
    }

    @Test
    public void shouldGetTreasureAmount() {
        assertEquals(dungeon.getTreasureAmount(TreasureType.BOOK), 5);
        assertEquals(dungeon.getTreasureAmount(TreasureType.SWORD), 1);
        assertEquals(dungeon.getTreasureAmount(TreasureType.CROSS), 2);
        assertEquals(dungeon.getTreasureAmount(TreasureType.BAG), 0);
        assertNotEquals(dungeon.getTreasureAmount(TreasureType.BOOK), 2);
        assertNotEquals(dungeon.getTreasureAmount(TreasureType.SWORD), 0);
        assertNotEquals(dungeon.getTreasureAmount(TreasureType.CROSS), 4);
        assertNotEquals(dungeon.getTreasureAmount(TreasureType.BAG), 1);
        dungeon.setJackpotStashEffectActivated(true);
        assertEquals(dungeon.getTreasureAmount(TreasureType.BOOK), 10);
        assertEquals(dungeon.getTreasureAmount(TreasureType.SWORD), 2);
        assertEquals(dungeon.getTreasureAmount(TreasureType.CROSS), 4);
        assertEquals(dungeon.getTreasureAmount(TreasureType.BAG), 0);
    }

    @Test
    public void shouldAddNewHeroToDungeon() {
        HeroCard dummyHero1 = new HeroCard();
        HeroCard dummyHero2 = new HeroCard();
        HeroCard dummyHero3 = new HeroCard();
        dungeon.addNewHeroToDungeon(dummyHero1);
        dungeon.addNewHeroToDungeon(dummyHero2);
        dungeon.addNewHeroToDungeon(dummyHero3);
        List<HeroCard> expectedFirstRoomHeroes = List.of(dummyHero1, dummyHero2, dummyHero3);
        List<HeroCard> trueFirstRoomHeroes = dungeon.getRoomSlots()[3].getHeroesInRoom().stream().map(x -> x.getHeroCard()).collect(Collectors.toList());
        assertEquals(expectedFirstRoomHeroes, trueFirstRoomHeroes);
    }

    @Test
    public void shouldSetInitialRoomCardDamage() {
        dungeon.setInitialRoomCardDamage();
        assertEquals(dungeon.getRoomSlots()[0].getRoomTrueDamage(), 3);
        assertEquals(dungeon.getRoomSlots()[1].getRoomTrueDamage(), 2);
        assertEquals(dungeon.getRoomSlots()[2].getRoomTrueDamage(), 1);
        assertEquals(dungeon.getRoomSlots()[3].getRoomTrueDamage(), 2); // Monster's Ballroom passive effect
        assertEquals(dungeon.getRoomSlots()[4].getRoomTrueDamage(), 0);
    }

    @Test
    public void shouldGetBuiltRooms() {
        assertEquals(4, dungeon.getBuiltRooms());
        dungeon.replaceDungeonRoom(null, 3);
        assertEquals(3, dungeon.getBuiltRooms());
    }

    @Test
    public void shouldReplaceDungeonRoom() {
        RoomCard dummyRoomCard1 = new RoomCard();
        dungeon.replaceDungeonRoom(dummyRoomCard1, 2);
        dungeon.replaceDungeonRoom(null, 3);
        assertEquals(dummyRoomCard1, dungeon.getRoomSlots()[2].getRoom());
        assertEquals(null, dungeon.getRoomSlots()[3].getRoom());
    }

    @Test
    public void shouldGetRoom() {
        RoomCard position0Room = dungeon.getRoomSlots()[0].getRoom();
        RoomCard position1Room = dungeon.getRoomSlots()[1].getRoom();
        assertEquals(position0Room, dungeon.getRoom(0));
        assertEquals(position1Room, dungeon.getRoom(1));
        assertNotEquals(position0Room, dungeon.getRoom(1));
        assertNotEquals(position1Room, dungeon.getRoom(0));
        assertEquals(null, dungeon.getRoom(4));
    }

    @Test
    public void shouldMoveHeroToNextRoom() {
        HeroCardStateInDungeon dummyHero1 = new HeroCardStateInDungeon(new HeroCard());
        HeroCardStateInDungeon dummyHero2 = new HeroCardStateInDungeon(new HeroCard());
        HeroCardStateInDungeon dummyHero3 = new HeroCardStateInDungeon(new HeroCard());
        dungeon.getRoomSlots()[3].getHeroesInRoom().add(dummyHero1);
        dungeon.getRoomSlots()[3].getHeroesInRoom().add(dummyHero2);
        dungeon.getRoomSlots()[1].getHeroesInRoom().add(dummyHero3);
        dungeon.moveHeroToNextRoom(dummyHero1, 3);
        dungeon.moveHeroToNextRoom(dummyHero2, 3);
        dungeon.moveHeroToNextRoom(dummyHero3, 2);
        dungeon.moveHeroToNextRoom(dummyHero3, 1);
        dungeon.moveHeroToNextRoom(dummyHero3, 0);
        dungeon.moveHeroToNextRoom(dummyHero2, 4);
        assertEquals(List.of(dummyHero1, dummyHero2), dungeon.getRoomSlots()[2].getHeroesInRoom());
        assertEquals(List.of(dummyHero3), dungeon.getRoomSlots()[0].getHeroesInRoom());
    }

    @Test
    public void shouldRevealRooms() {
        dungeon.getRoomSlots()[0].setIsVisible(false);
        dungeon.getRoomSlots()[2].setIsVisible(false);
        dungeon.revealRooms();
        Integer hiddenRooms = (int) Arrays.stream(dungeon.getRoomSlots()).filter(x -> !x.getIsVisible()).count();
        assertEquals(0, hiddenRooms);
    }

    @Test
    public void shouldCheckRoomCardEffectIsTriggered() {
        assertTrue(dungeon.checkRoomCardEffectIsTriggered(RoomPassiveTrigger.DESTROY_ANOTHER_ROOM, 0));
        assertTrue(dungeon.checkRoomCardEffectIsTriggered(RoomPassiveTrigger.USE_SPELL_CARD, 1));
        assertTrue(dungeon.checkRoomCardEffectIsTriggered(RoomPassiveTrigger.USE_SPELL_CARD, 3));
        assertFalse(dungeon.checkRoomCardEffectIsTriggered(RoomPassiveTrigger.USE_SPELL_CARD, 2));
        assertFalse(dungeon.checkRoomCardEffectIsTriggered(RoomPassiveTrigger.USE_SPELL_CARD, 4));
    }

    @Test
    public void shouldCheckBossLeveledUp() {
        dungeon.setBossCardLeveledUp(false);
        assertFalse(dungeon.checkBossLeveledUp());
        dungeon.replaceDungeonRoom(new RoomCard(), 4);
        assertTrue(dungeon.checkBossLeveledUp());
        dungeon.setBossCardLeveledUp(true);
        assertFalse(dungeon.checkBossLeveledUp());
        dungeon.replaceDungeonRoom(null, 4);
        assertFalse(dungeon.checkBossLeveledUp());
    }

    @Test
    public void shouldHeroAdvanceRoomDungeon() {
        Player dummyPlayer = new Player();
        dummyPlayer.setSouls(0);
        dummyPlayer.setHealth(5);
        dummyPlayer.setDungeon(dungeon);
        dungeon.setPlayer(dummyPlayer);
        HeroCardStateInDungeon dummyHero1 = setUpComplexDummyHero(2, false);
        HeroCardStateInDungeon dummyHero2 = setUpComplexDummyHero(5, true);
        HeroCardStateInDungeon dummyHero3 = setUpComplexDummyHero(1, true);
        HeroCardStateInDungeon dummyHero4 = setUpComplexDummyHero(5, false);
        HeroCardStateInDungeon dummyHero5 = setUpComplexDummyHero(3, false);
        HeroCardStateInDungeon dummyHero6 = setUpComplexDummyHero(5, true);
        dungeon.getRoomSlots()[0].addHero(dummyHero1); // This hero will die in the last room and give 1 soul to the player
        dungeon.getRoomSlots()[0].addHero(dummyHero2); // This hero will reach the final boss and deal 2 damage to the player
        dungeon.getRoomSlots()[2].addHero(dummyHero3); // This player will die in a non last room give 2 soul to the player
        dungeon.getRoomSlots()[0].addHero(dummyHero4); // This hero will reach the final boss and deal 1 damage to the player
        dungeon.getRoomSlots()[3].addHero(dummyHero5); // This hero will travel to the next room receiving 0 damage
        dungeon.getRoomSlots()[2].addHero(dummyHero6); // This hero will travel to the next room receiving 1 damage
        dungeon.setInitialRoomCardDamage();
        dungeon.heroAdvanceRoomDungeon();
        assertThat("Heroes in last room didn't left the room", dungeon.getRoomSlots()[0].getHeroesInRoom(), is(new ArrayList<>()));
        assertThat("Incorrect damage dealt to player", dungeon.getPlayer().getHealth(), is(2));
        assertThat("Incorrect souls given to player", dungeon.getPlayer().getSouls(), is(3));
        assertThat("Hero6 didn't move to next room", dungeon.getRoomSlots()[1].getHeroesInRoom(), is(List.of(dummyHero6)));
        assertThat("Hero5 didn't move to next room", dungeon.getRoomSlots()[2].getHeroesInRoom(), is(List.of(dummyHero5)));
        assertThat("Hero6 didn't receive damage", dummyHero6.getHealthInDungeon(), is(4));
        assertThat("Hero5 didn't receive damage", dummyHero5.getHealthInDungeon(), is(1));
    }

}
