package org.springframework.samples.bossmonster.game.dungeon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.samples.bossmonster.game.Game;
import org.springframework.samples.bossmonster.game.card.TreasureType;
import org.springframework.samples.bossmonster.game.card.finalBoss.FinalBossCard;
import org.springframework.samples.bossmonster.game.card.hero.HeroCard;
import org.springframework.samples.bossmonster.game.card.hero.HeroCardStateInDungeon;
import org.springframework.samples.bossmonster.game.card.room.RoomCard;
import org.springframework.samples.bossmonster.game.card.room.RoomPassiveTrigger;
import org.springframework.samples.bossmonster.game.card.room.RoomType;
import org.springframework.samples.bossmonster.game.player.Player;

import lombok.Getter;
import lombok.Setter;

import org.springframework.samples.bossmonster.model.BaseEntity;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Slf4j
public class Dungeon extends BaseEntity {

    @OneToOne(cascade = CascadeType.ALL)
    private Player player;

    @OneToOne
    private FinalBossCard bossCard;

    @OneToMany(cascade = CascadeType.ALL)
    @OrderColumn
    private DungeonRoomSlot[] roomSlots;

    @OneToMany(cascade = CascadeType.ALL)
    private List<HeroCardStateInDungeon> heroes = new ArrayList<>();

    private Boolean bossCardLeveledUp;

    private Boolean jackpotStashEffectActivated;

    @ManyToOne
    Game game;

    public Integer getTreasureAmount(TreasureType treasure) {

        Integer totalAmount = 0;
        for (int index = getBuiltRooms()-1; index >= 0; index--) {
            Integer treasureInRoom = getRoom(index).parseTreasureAmount(treasure);
            log.debug(String.format("%s in %s: %s, (%s)", treasure, getRoom(index).getName(),treasureInRoom,getRoom(index).getTreasure()));
            totalAmount += treasureInRoom;
        }
        if (bossCard.getTreasure() == treasure) totalAmount ++;
        if (player.isDead()) totalAmount = 0;
        if (jackpotStashEffectActivated) totalAmount = totalAmount * 2;
        log.debug(String.format("[getTreasureAmount] Amount of %s for card: %s", treasure, totalAmount));
        return totalAmount;
    }

    public void addNewHeroToDungeon(HeroCard hero) {
        roomSlots[getFirstRoomSlot()].addHero(new HeroCardStateInDungeon(hero));
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
                if (!room.isMonsterBallroom()) slot.setRoomTrueDamage(room.getDamage());
                else { // That one room card whose damage was the amount of monster rooms in the dungeon
                    long damage = Stream.of(roomSlots).filter(x -> x.getRoom() != null).filter(x -> x.getRoom().getRoomType() == RoomType.ADVANCED_MONSTER || x.getRoom().getRoomType() == RoomType.MONSTER).count();
                    slot.setRoomTrueDamage((int) damage);
                }
            }
            else slot.setRoomTrueDamage(0);
        }
    }

    public Integer getBuiltRooms() {
        return (int) Arrays.stream(getRoomSlots()).filter(slot->slot.getRoom()!=null).count();
    }

    public List<RoomCard> getRooms() {
        return Arrays.stream(getRoomSlots()).map(slot->slot.getRoom()).collect(Collectors.toList());
    }

    public void replaceDungeonRoom(RoomCard room, Integer position) {
        roomSlots[position].replaceRoom(room);
    }

    public RoomCard getRoom(Integer position) {
        return roomSlots[position].getRoom();
    }

    public void moveHeroToNextRoom(HeroCardStateInDungeon hero, Integer currentRoomSlot) {
        // This is an auxiliary function, which means the hero is always going to be in the dungeon room
        // and the room is never going to be the last one, but just in case I put a failsafe in it
        if (roomSlots[currentRoomSlot].getHeroesInRoom().contains(hero) && currentRoomSlot != 0) {
            roomSlots[currentRoomSlot].removeHero(hero);
            roomSlots[currentRoomSlot - 1].addHero(hero);
        }

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

    public Boolean checkBossLeveledUp() {
        return (getBuiltRooms() == 5 && !bossCardLeveledUp);
    }

    public void heroAdvanceRoomDungeon() {
        var slots = Arrays.stream(roomSlots).map(s->s.getHeroesInRoom()).collect(Collectors.toList());
        log.debug("Slots before: " + slots);
        for(int i = 0; i < 5; i ++) {
            DungeonRoomSlot roomSlot = roomSlots[i];
            Integer dealtDamage = roomSlot.getRoomTrueDamage();
            Iterator<HeroCardStateInDungeon> iterator = roomSlot.getHeroesInRoom().iterator();
            while(iterator.hasNext()) {
                HeroCardStateInDungeon hero = iterator.next();
                hero.dealDamage(dealtDamage);
                iterator.remove();
                if (hero.isDead()) {
                    player.addSoulsFromKilledHero(hero);
                    game.tryTriggerRoomCardEffect(RoomPassiveTrigger.HERO_DIES_IN_THIS_ROOM,getPlayer(),i);
                }
                else {
                    if (!isDungeonLastRoom(i)) roomSlots[i-1].addHero(hero);
                    else player.removeHealthFromUndefeatedHero(hero);
                }
            }
        }
        log.debug("slots after: " + slots);
    }

    public void damageRandomHeroInDungeonPosition(Integer position, Integer damage) {
        List<HeroCardStateInDungeon> heroesInSlot = roomSlots[position].getHeroesInRoom();
        if (!heroesInSlot.isEmpty()) {
            Random random = new Random();
            int index = random.nextInt(heroesInSlot.size());
            HeroCardStateInDungeon chosenHero = heroesInSlot.get(index);
            chosenHero.dealDamage(damage);
            if (chosenHero.isDead()) {
                getRoomSlots()[position].removeHero(chosenHero);
                player.addSoulsFromKilledHero(chosenHero);
            }
        }
    }

    public Boolean isDungeonLastRoom(Integer position) {
        return position == 0;
    }

    public void heroAutomaticallyMovesAfterDestroyingRoom(Integer position) {
        List<HeroCardStateInDungeon> affectedHeroes = roomSlots[position].getHeroesInRoom();
        for (HeroCardStateInDungeon hcsd: affectedHeroes) {
            roomSlots[position].removeHero(hcsd);
            if (isDungeonLastRoom(position)) player.removeHealthFromUndefeatedHero(hcsd);
            else roomSlots[position-1].addHero(hcsd);
        }
    }

    // Used for testing
    public Dungeon cloneDungeon() {
        Dungeon clone = new Dungeon();
        clone.bossCard = bossCard;
        clone.roomSlots = roomSlots;
        return clone;
    }

}
