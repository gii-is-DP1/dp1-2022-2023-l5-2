package org.springframework.samples.bossmonster.game;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.bossmonster.game.card.CardService;
import org.springframework.samples.bossmonster.game.card.hero.HeroCard;
import org.springframework.samples.bossmonster.game.card.hero.HeroCardRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(properties="spring.main.lazy-initialization=true")
public class CardServiceTest {
    @Autowired
    CardService cardService;

    @Test
    public void shouldCreateHeroDeck() {
        List<HeroCard> deck = cardService.createHeroCardDeck();
        List<HeroCard> deck2 = cardService.createHeroCardDeck();
        assertFalse(deck.isEmpty());
        assertTrue(deck.get(0) instanceof HeroCard);
        assertNotEquals(deck,deck2);
    }
}
