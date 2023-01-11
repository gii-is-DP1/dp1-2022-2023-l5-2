package org.springframework.samples.bossmonster.game;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.bossmonster.game.card.finalBoss.FinalBossCard;
import org.springframework.samples.bossmonster.game.card.finalBoss.FinalBossCardRepository;
import org.springframework.samples.bossmonster.game.card.hero.HeroCard;
import org.springframework.samples.bossmonster.game.card.hero.HeroCardRepository;
import org.springframework.samples.bossmonster.game.card.room.RoomCard;
import org.springframework.samples.bossmonster.game.card.room.RoomCardRepository;
import org.springframework.samples.bossmonster.game.card.spell.SpellCard;
import org.springframework.samples.bossmonster.game.card.spell.SpellCardRepository;
import org.springframework.stereotype.Repository;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Repository.class))
public class CardRepositoriesTest {

    @Autowired
    RoomCardRepository roomCardRepo;
    @Autowired
    SpellCardRepository spellCardRepo;
    @Autowired
    HeroCardRepository heroCardRepo;
    @Autowired
    FinalBossCardRepository bossCardRepo;

    @Test
    @DisplayName("get FinalBosses from DB")
    public void shouldReturnBossCards() {
        List<FinalBossCard> cards = bossCardRepo.findAll();
        assertFalse(cards.isEmpty());
        assertTrue(cards.get(0) instanceof FinalBossCard);
    }

    @Test
    @DisplayName("get spells from DB")
    public void shouldReturnSpellCards() {
        List<SpellCard> cards = spellCardRepo.findAll();
        assertFalse(cards.isEmpty());
        assertTrue(cards.get(0) instanceof SpellCard);
    }

    @Test
    @DisplayName("get heroes from DB")
    public void shouldReturnHeroCards() {
        List<HeroCard> cards = heroCardRepo.findAll();
        assertFalse(cards.isEmpty());
        assertTrue(cards.get(0) instanceof HeroCard);
    }

    @Test
    @DisplayName("get rooms from DB")
    public void shouldReturnRoomCards() {
        List<RoomCard> cards = roomCardRepo.findAll();
        assertFalse(cards.isEmpty());
        assertTrue(cards.get(0) instanceof RoomCard);
    }

}
