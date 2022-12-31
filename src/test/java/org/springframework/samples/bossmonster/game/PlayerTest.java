package org.springframework.samples.bossmonster.game;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.samples.bossmonster.game.player.Player;

public class PlayerTest {
    
    protected Player player;

    @BeforeEach
    void setUp() {
        player = new Player();
        player.setHealth(5);
        player.setSouls(0);
        player.setHand(new ArrayList<>());
    }

    public void shouldRemoveHandCard() {

    }

    public void shouldAddHandCard() {
        
    }

    public void shouldDamageRandomHeroInDungeonPosition() {

    }

    public void shouldAddSoulsFromKilledHero() {

    }

    public void shouldRemoveHealthFromUndefeatedHero() {

    }

    public void shouldHeroAdvanceRoomDungeon() {

    }

    public void shouldHeroAutomaticallyMovesAfterDestroyingRoom() {

    }

    public void shouldDetectIsDungeonLastRoom() {

    }

    public void shoulDetectPlayerIsDead() {

    }

}
