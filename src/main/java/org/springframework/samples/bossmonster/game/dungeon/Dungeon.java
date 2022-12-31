package org.springframework.samples.bossmonster.game.dungeon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.samples.bossmonster.game.card.TreasureType;
import org.springframework.samples.bossmonster.game.card.finalBoss.FinalBossCard;
import org.springframework.samples.bossmonster.game.card.hero.HeroCard;
import org.springframework.samples.bossmonster.game.card.hero.HeroCardStateInDungeon;
import org.springframework.samples.bossmonster.game.card.room.RoomCard;
import org.springframework.samples.bossmonster.game.card.room.RoomPassiveTrigger;
import org.springframework.samples.bossmonster.game.card.room.RoomType;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.samples.bossmonster.model.BaseEntity;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Slf4j
public class Dungeon extends BaseEntity {

    @OneToOne
    FinalBossCard bossCard;

    @OneToMany(cascade = CascadeType.ALL)
    @OrderColumn
    DungeonRoomSlot[] roomSlots;

    @OneToMany(cascade = CascadeType.ALL)
    List<HeroCardStateInDungeon> heroes = new ArrayList<>();

    public Integer getTreasureAmount(TreasureType treasure) {

        Integer totalAmount = 0;
        for (int index = getBuiltRooms()-1; index >= 0; index--) {
            Integer treasureInRoom = getRoom(index).parseTreasureAmount(treasure);
            log.debug(String.format("%s in %s: %s, (%s)", treasure, getRoom(index).getName(),treasureInRoom,getRoom(index).getTreasure()));
            totalAmount += treasureInRoom;
        }
        log.debug(String.format("[getTreasureAmount] Amount of %s for card: %s", treasure, totalAmount));
        return totalAmount;

    }

    public void addNewHeroToDungeon(HeroCard hero) {
        roomSlots[getFirstRoomSlot()].addHero(new HeroCardStateInDungeon(hero, this));
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
