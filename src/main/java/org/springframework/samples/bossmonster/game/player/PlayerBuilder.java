package org.springframework.samples.bossmonster.game.player;

import java.util.ArrayList;
import java.util.List;

import org.springframework.samples.bossmonster.game.card.Card;
import org.springframework.samples.bossmonster.game.card.room.RoomCard;
import org.springframework.samples.bossmonster.game.dungeon.Dungeon;
import org.springframework.samples.bossmonster.user.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
abstract class PlayerBuilder {
    
    protected Player newPlayer;

    public void buildPlayerStartingHand() {
        List<Card> startingHand = new ArrayList<>();
        // TODO Añadir 3 cartas de habitación aleatorias
        // TODO Añadir 2 cartas de hechizo aleatorias
        newPlayer.setHand(startingHand);
    }

    public void buildPlayerDungeon() {
        Dungeon dungeon = new Dungeon();
        dungeon.setRooms(new RoomCard[5]);
        // TODO Añadir lista de heroes
        //newPlayer.setDungeon(dungeon);
    }

    public void buildPlayerStats() {
        newPlayer.setSouls(0);
        newPlayer.setHealth(5);
    }

    public void buildPlayerUser(User user) {
        newPlayer.setUser(user);
    }

}
