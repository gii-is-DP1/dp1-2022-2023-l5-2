package org.springframework.samples.bossmonster.game.player;

import java.util.ArrayList;
import java.util.List;

import org.springframework.samples.bossmonster.game.card.Card;
import org.springframework.samples.bossmonster.game.card.room.RoomCard;
import org.springframework.samples.bossmonster.game.dungeon.Dungeon;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
abstract class PlayerBuilder {
    
    protected Player newPlayer;
    

    public void buildNewPlayer() {
        newPlayer.setSouls(0);
        newPlayer.setHealth(5);
    }

    public void buildStartingHand() {
        List<Card> startingHand = new ArrayList<>();
        // TODO Añadir 3 cartas de habitación aleatorias
        // TODO Añadir dos cartas de hechizo aleatorias
        newPlayer.setHand(startingHand);
    }

    public void buildDungeon() {
        Dungeon dungeon = new Dungeon();
        dungeon.setRooms(new RoomCard[5]);
        // TODO Añadir lista de heroes
        //newPlayer.setDungeon(dungeon);
    }

}
