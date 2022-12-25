package org.springframework.samples.bossmonster.game.dungeon;

import java.util.List;
import java.util.stream.Stream;

import org.hibernate.annotations.Cascade;
import org.springframework.samples.bossmonster.game.card.TreasureType;
import org.springframework.samples.bossmonster.game.card.finalBoss.FinalBossCard;
import org.springframework.samples.bossmonster.game.card.hero.HeroCard;
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

    @OneToMany
    List<HeroCard> entrance;

    //@OneToMany
    //List<HeroCard>[] heroesInRoom;

    public Integer getTreasureAmount(TreasureType treasure) {

        Integer totalAmount = 0;
        for (DungeonRoomSlot roomSlot: roomSlots) {
            totalAmount += roomSlot.getRoom().parseTreasureAmount(treasure);
        }
        return totalAmount;

    }

    public void addNewHeroToDungeon(HeroCard hero) {
        entrance.add(hero);
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

    public void replaceDungeonRoom(RoomCard room, Integer position) {
        roomSlots[position].replaceRoom(room);
    }

    public RoomCard getRoom(Integer position) {
        return roomSlots[position].getRoom();
    }

    public void moveHeroToNextRoom(HeroCard hero, Integer currentRoomSlot) {
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
        return (!card.equals(null) && card.getPassiveTrigger() == trigger);
    }

}
