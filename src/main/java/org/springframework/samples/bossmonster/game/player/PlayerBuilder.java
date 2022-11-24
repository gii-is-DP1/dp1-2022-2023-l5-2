package org.springframework.samples.bossmonster.game.player;

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

    }

    public void buildDungeon() {
        Dungeon dungeon = new Dungeon();
        dungeon.setRooms(new RoomCard[5]);
        // Añadir lista de heroes
        //newPlayer.setDungeon(dungeon);
    }

}
