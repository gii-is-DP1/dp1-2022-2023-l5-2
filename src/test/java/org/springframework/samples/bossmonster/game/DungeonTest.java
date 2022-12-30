package org.springframework.samples.bossmonster.game;

import org.junit.jupiter.api.Test;
import org.springframework.samples.bossmonster.game.card.room.RoomCard;
import org.springframework.samples.bossmonster.game.dungeon.Dungeon;
import org.springframework.samples.bossmonster.game.dungeon.DungeonRoomSlot;

public class DungeonTest {
    
    protected Dungeon dungeon;

    void setUp() {
        dungeon = new Dungeon();

    }

    DungeonRoomSlot setUpRoomSlot() {
        DungeonRoomSlot slot = new DungeonRoomSlot();
        RoomCard room = new RoomCard();
        return null;
    }

    @Test
    public void shouldGetTreasureAmount() {

    }

    public void shouldAddNewHeroToDungeon() {

    }

    public void shouldSetInitialRoomCardDamage() {

    }

    public void shouldGetBuiltRooms() {

    }

    public void shouldReplaceDungeonRoom() {

    }

    public void shouldGetRoom() {

    }

    public void shouldMoveHeroToNextRoom() {
        
    }

    public void shouldRevealRooms() {

    }

    public void shouldCheckRoomCardEffectIsTriggered() {

    }

    public void shouldDamageRandomHeroInDungeonPosition() {

    }

}
