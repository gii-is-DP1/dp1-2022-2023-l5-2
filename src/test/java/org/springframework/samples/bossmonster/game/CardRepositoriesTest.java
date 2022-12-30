package org.springframework.samples.bossmonster.game;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.bossmonster.game.card.hero.HeroCard;
import org.springframework.samples.bossmonster.game.card.hero.HeroCardRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Repository.class))
public class CardRepositoriesTest {
    @Autowired
    HeroCardRepository heroCardRepo;

    @Test
    public void shouldReturnHeroCards() {
        List<HeroCard> cards = heroCardRepo.findAll();
        assertFalse(cards.isEmpty());
        assertTrue(cards.get(0) instanceof HeroCard);
    }
}
