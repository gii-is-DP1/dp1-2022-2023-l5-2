package org.springframework.samples.bossmonster.game.dungeon;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.samples.bossmonster.game.card.hero.HeroCard;
import org.springframework.samples.bossmonster.game.card.room.RoomCard;
import org.springframework.samples.bossmonster.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class DungeonRoomSlot extends BaseEntity {

    @OneToOne
    private RoomCard room;

    @OneToMany
    private List<HeroCard> heroesInRoom;

    private Boolean isVisible;

    private Integer roomTrueDamage;

    public DungeonRoomSlot() {
        this.room = null;
        this.heroesInRoom = new ArrayList<>();
        this.isVisible = true;
        this.roomTrueDamage = 0;
    }

    public void replaceRoom(RoomCard newRoom) {
        room = newRoom;
    }

    public void addHero(HeroCard hero) {
        heroesInRoom.add(hero);
    }

    public void removeHero(HeroCard hero) {
        heroesInRoom.remove(hero);
    }

    public Boolean isEmpty() {
        return room == null;
    }

}
