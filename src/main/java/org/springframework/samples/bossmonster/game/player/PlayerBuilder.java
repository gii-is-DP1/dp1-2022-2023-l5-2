package org.springframework.samples.bossmonster.game.player;

import java.util.ArrayList;
import java.util.List;

import org.springframework.samples.bossmonster.game.card.finalBoss.FinalBossCard;
import org.springframework.samples.bossmonster.game.card.room.RoomCard;
import org.springframework.samples.bossmonster.game.card.spell.SpellCard;
import org.springframework.samples.bossmonster.game.dungeon.Dungeon;
import org.springframework.samples.bossmonster.game.dungeon.DungeonRoomSlot;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class PlayerBuilder {

    private final Integer STARTING_ROOM_CARDS = 3;
    private final Integer STARTING_SPELL_CARDS = 2;
    private final Integer STARTING_HEALTH = 5;

    List<RoomCard> currentRoomPile;
    List<SpellCard> currentSpellPile;
    List<FinalBossCard> currentBossPile;

    public Player buildNewPlayer(User user) {
        Player newPlayer = new Player();
        buildPlayerStartingHand(newPlayer);
        buildPlayerDungeon(newPlayer);
        buildPlayerStats(newPlayer);
        buildPlayerUser(newPlayer, user);
        return newPlayer;
    }

    public void buildPlayerStartingHand(Player newPlayer) {
        newPlayer.setHand(new ArrayList<>());
        for (int i = 0; i < STARTING_ROOM_CARDS; i ++) {
            RoomCard roomCard = currentRoomPile.remove(currentRoomPile.size() - 1);
            newPlayer.getHand().add(roomCard);
        }
        for (int i = 0; i < STARTING_SPELL_CARDS; i ++) {
            SpellCard spellCard = currentSpellPile.remove(currentSpellPile.size() - 1);
            newPlayer.getHand().add(spellCard);
        }
    }

    public void buildPlayerDungeon(Player newPlayer) {
        Dungeon dungeon = new Dungeon();
        DungeonRoomSlot[] slots = new DungeonRoomSlot[5];
        for (int i = 0; i < 5; i ++) {
            DungeonRoomSlot newRoomSlot = new DungeonRoomSlot();
            slots[i] = newRoomSlot;
        }
        dungeon.setRoomSlots(slots);
        FinalBossCard boss = currentBossPile.remove(currentBossPile.size()-1);
        dungeon.setBossCard(boss);
        dungeon.setBossCardLeveledUp(false);
        dungeon.setPlayer(newPlayer);
        dungeon.setJackpotStashEffectActivated(false);
        newPlayer.setDungeon(dungeon);
    }

    public void buildPlayerStats(Player newPlayer) {
        newPlayer.setSouls(0);
        newPlayer.setHealth(STARTING_HEALTH);
        newPlayer.setEliminatedRound(-1);
    }

    public void buildPlayerUser(Player newPlayer, User user) {
        newPlayer.setUser(user);
    }

}
