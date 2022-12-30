package org.springframework.samples.bossmonster.game.dungeon;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.samples.bossmonster.game.card.TreasureType;
import org.springframework.samples.bossmonster.game.card.finalBoss.FinalBossCard;
import org.springframework.samples.bossmonster.game.card.hero.HeroCard;
import org.springframework.samples.bossmonster.game.card.hero.HeroCardStateInDungeon;
import org.springframework.samples.bossmonster.game.card.room.RoomCard;
import org.springframework.samples.bossmonster.game.card.room.RoomPassiveTrigger;
import org.springframework.samples.bossmonster.game.card.room.RoomType;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.bossmonster.model.BaseEntity;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Dungeon extends BaseEntity {

    @OneToOne
    FinalBossCard bossCard;

    @OneToMany(cascade = CascadeType.ALL)
    @OrderColumn
    DungeonRoomSlot[] roomSlots;

    public Integer getTreasureAmount(TreasureType treasure) {

        Integer totalAmount = 0;
        for (DungeonRoomSlot roomSlot: roomSlots) {
            totalAmount += roomSlot.getRoom().parseTreasureAmount(treasure);
        }
        System.out.println(String.format("Amount of %s: %s", treasure, totalAmount));
        return totalAmount;

    }

    public void addNewHeroToDungeon(HeroCard hero) {
        roomSlots[getFirstRoomSlot()].addHero(createStateOfHeroInDungeon(hero));
    }

    public HeroCardStateInDungeon createStateOfHeroInDungeon(HeroCard hero) {
        HeroCardStateInDungeon heroInDungeon = new HeroCardStateInDungeon();
        heroInDungeon.setDungeon(this);
        heroInDungeon.setHeroCard(hero);
        heroInDungeon.setHealthInDungeon(hero.getHealth());
        heroInDungeon.setMinotaursMazeEffectTriggered(false);
        return heroInDungeon;
    }

    public Integer getFirstRoomSlot() {
        Boolean detected = false;
        Integer currentRoom = 4;
        while (!detected) {
            if (roomSlots[currentRoom].getRoom() == null && currentRoom != 0) currentRoom --;
            else detected = true;
        }
        return currentRoom;
    }

    public void setInitialRoomCardDamage() {
        for(DungeonRoomSlot slot: roomSlots) {
            RoomCard room = slot.getRoom();
            if (room != null) {
                if (room.getId() != 4) slot.setRoomTrueDamage(room.getDamage());
                else { // That one room card whose damage was the amount of monster rooms in the dungeon
                    long damage = Stream.of(roomSlots).filter(x -> x.getRoom().getRoomType() == RoomType.ADVANCED_MONSTER || x.getRoom().getRoomType() == RoomType.MONSTER).count();
                    //slot.setRoomTrueDamage(damage);
                }
            }
            else slot.setRoomTrueDamage(0);
        }
    }

    public Integer getBuiltRooms() {

        return (int) Arrays.stream(getRoomSlots()).filter(slot->slot.getRoom()!=null).count();
    }

    public void replaceDungeonRoom(RoomCard room, Integer position) {
        roomSlots[position].replaceRoom(room);
    }

    public RoomCard getRoom(Integer position) {
        return roomSlots[position].getRoom();
    }

    public void moveHeroToNextRoom(HeroCardStateInDungeon hero, Integer currentRoomSlot) {
        roomSlots[currentRoomSlot].removeHero(hero);
        roomSlots[currentRoomSlot - 1].addHero(hero);
    }

    public void revealRooms() {
        for(var i = 0; i < 5; i ++) {
            if (roomSlots[i].getRoom() != null) roomSlots[i].setIsVisible(true);
        }
    }

    public Boolean checkRoomCardEffectIsTriggered(RoomPassiveTrigger trigger, Integer position) {
        RoomCard card = roomSlots[position].getRoom();
        return (!(card == null) && card.getPassiveTrigger() == trigger);
    }

    // Used for testing
    public Dungeon cloneDungeon() {
        Dungeon clone = new Dungeon();
        clone.bossCard = bossCard;
        clone.roomSlots = roomSlots;
        return clone;
    }

}
