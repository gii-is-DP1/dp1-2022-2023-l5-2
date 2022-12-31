package org.springframework.samples.bossmonster.game;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.bossmonster.game.card.CardService;
import org.springframework.samples.bossmonster.game.card.finalBoss.FinalBossCard;
import org.springframework.samples.bossmonster.game.card.hero.HeroCard;
import org.springframework.samples.bossmonster.game.card.room.RoomCard;
import org.springframework.samples.bossmonster.game.card.spell.SpellCard;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(properties="spring.main.lazy-initialization=true")
public class CardServiceTest {
    @Autowired
    CardService cardService;

    @Test
    @DisplayName("create Hero card Deck")
    public void shouldCreateHeroDeck() {
        List<HeroCard> deck = cardService.createHeroCardDeck();
        List<HeroCard> deck2 = cardService.createHeroCardDeck();
        assertFalse(deck.isEmpty());
        assertTrue(deck.get(0) instanceof HeroCard);
        assertNotEquals(deck,deck2);
    }

    @Test
    @DisplayName("create Spell card Deck")
    public void shouldCreateSpellDeck() {
        List<SpellCard> deck = cardService.createSpellCardDeck();
        List<SpellCard> deck2 = cardService.createSpellCardDeck();
        assertFalse(deck.isEmpty());
        assertTrue(deck.get(0) instanceof SpellCard);
        assertNotEquals(deck,deck2);
    }

    @Test
    @DisplayName("create Room card Deck")
    public void shouldCreateRoomDeck() {
        List<RoomCard> deck = cardService.createRoomCardDeck();
        List<RoomCard> deck2 = cardService.createRoomCardDeck();
        assertFalse(deck.isEmpty());
        assertTrue(deck.get(0) instanceof RoomCard);
        assertNotEquals(deck,deck2);
    }

    @Test
    @DisplayName("create Boss card Deck")
    public void shouldCreateBossDeck() {
        List<FinalBossCard> deck = cardService.createBossCardDeck();
        List<FinalBossCard> deck2 = cardService.createBossCardDeck();
        assertFalse(deck.isEmpty());
        assertTrue(deck.get(0) instanceof FinalBossCard);
        assertNotEquals(deck,deck2);
    }

}
