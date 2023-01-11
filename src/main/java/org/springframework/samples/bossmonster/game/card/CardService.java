package org.springframework.samples.bossmonster.game.card;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.bossmonster.game.card.finalBoss.FinalBossCard;
import org.springframework.samples.bossmonster.game.card.finalBoss.FinalBossCardRepository;
import org.springframework.samples.bossmonster.game.card.hero.HeroCard;
import org.springframework.samples.bossmonster.game.card.hero.HeroCardRepository;
import org.springframework.samples.bossmonster.game.card.room.RoomCard;
import org.springframework.samples.bossmonster.game.card.room.RoomCardRepository;
import org.springframework.samples.bossmonster.game.card.spell.SpellCard;
import org.springframework.samples.bossmonster.game.card.spell.SpellCardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CardService {

    SpellCardRepository spellCardRepo;
    RoomCardRepository roomCardRepo;
    HeroCardRepository heroCardRepo;
    FinalBossCardRepository bossCardRepo;

    @Autowired
    public CardService(SpellCardRepository spellCardRepo, RoomCardRepository roomCardRepo, HeroCardRepository heroCardRepo,
                       FinalBossCardRepository bossCardRepo) {
        this.spellCardRepo = spellCardRepo;
        this.roomCardRepo = roomCardRepo;
        this.heroCardRepo = heroCardRepo;
        this.bossCardRepo = bossCardRepo;
    }

    @Transactional
    public List<SpellCard> createSpellCardDeck() {
        List<SpellCard> deck = spellCardRepo.findAll();
        Collections.shuffle(deck);
        return deck;
    }

    @Transactional
    public List<RoomCard> createRoomCardDeck() {
        List<RoomCard> deck = roomCardRepo.findAll();
        Collections.shuffle(deck);
        return deck;
    }

    @Transactional
    public List<HeroCard> createHeroCardDeck() {
        List<HeroCard> deck = heroCardRepo.findAll();
        Collections.shuffle(deck);
        return deck;
    }

    @Transactional
    public List<FinalBossCard> createBossCardDeck() {
        List<FinalBossCard> deck = bossCardRepo.findAll();
        Collections.shuffle(deck);
        return deck;
    }

    @Transactional
    public void giveCard(List<Card> source, List<Card> target, int cardPosition) {
        if (cardPosition >= source.size()) { cardPosition = source.size() - 1; }
        Card toGive = source.remove(cardPosition);
        target.add(toGive);
    }


}
