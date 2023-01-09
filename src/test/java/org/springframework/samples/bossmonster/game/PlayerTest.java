package org.springframework.samples.bossmonster.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.samples.bossmonster.game.player.Player;
import org.springframework.samples.bossmonster.game.card.Card;
import org.springframework.samples.bossmonster.game.card.hero.HeroCard;
import org.springframework.samples.bossmonster.game.card.hero.HeroCardStateInDungeon;

public class PlayerTest {
    
    protected Player player;

    @BeforeEach
    void setUp() {
        player = new Player();
        player.setHealth(5);
        player.setSouls(0);
        player.setHand(new ArrayList<>());
    }

    @Test
    public void shouldAddHandCard() {
        Card card1 = new Card();
        Card card2 = new Card();
        List<Card> expectedHand = List.of(card1, card2);
        player.addHandCard(card1);
        player.addHandCard(card2);
        assertEquals(expectedHand, player.getHand());
    }

    @Test
    public void shouldRemoveHandCard() {
        Card card1 = new Card();
        Card card2 = new Card();
        List<Card> expectedHand = List.of(card2);
        player.addHandCard(card1);
        player.addHandCard(card2);
        player.removeHandCard(0);
        assertEquals(expectedHand, player.getHand());
    }

    @Test
    public void shouldAddSoulsFromKilledHero() {
        HeroCard dummyHero1 = new HeroCard();
        HeroCard dummyHero2 = new HeroCard();
        dummyHero1.setIsEpic(false);
        dummyHero2.setIsEpic(true);
        HeroCardStateInDungeon h1 = new HeroCardStateInDungeon();
        HeroCardStateInDungeon h2 = new HeroCardStateInDungeon();
        h1.setHeroCard(dummyHero1);
        h2.setHeroCard(dummyHero2);
        player.addSoulsFromKilledHero(h1);
        assertEquals(1, player.getSouls());
        player.addSoulsFromKilledHero(h2);
        assertEquals(3, player.getSouls());
    }

    @Test
    public void shouldRemoveHealthFromUndefeatedHero() {
        HeroCard dummyHero1 = new HeroCard();
        HeroCard dummyHero2 = new HeroCard();
        dummyHero1.setIsEpic(false);
        dummyHero2.setIsEpic(true);
        HeroCardStateInDungeon h1 = new HeroCardStateInDungeon();
        HeroCardStateInDungeon h2 = new HeroCardStateInDungeon();
        h1.setHeroCard(dummyHero1);
        h2.setHeroCard(dummyHero2);
        player.removeHealthFromUndefeatedHero(h1);
        assertEquals(4, player.getHealth());
        player.removeHealthFromUndefeatedHero(h2);
        assertEquals(2, player.getHealth());
    }

    @Test
    public void shoulDetectPlayerIsDead() {
        player.setHealth(3);
        assertFalse(player.isDead());
        player.setHealth(0);
        assertTrue(player.isDead());
        player.setHealth(-2);
        assertTrue(player.isDead());
    }

}
