package org.springframework.samples.bossmonster.game.card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.bossmonster.game.card.hero.HeroCard;
import org.springframework.samples.bossmonster.game.card.hero.HeroCardRepository;
import org.springframework.samples.bossmonster.game.card.room.RoomCard;
import org.springframework.samples.bossmonster.game.card.room.RoomCardRepository;
import org.springframework.samples.bossmonster.game.card.spell.SpellCard;
import org.springframework.samples.bossmonster.game.card.spell.SpellCardRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CardService {

    SpellCardRepository spellCardRepo;
    RoomCardRepository roomCardRepo;
    HeroCardRepository heroCardRepo;

    @Autowired
    public CardService(SpellCardRepository spellCardRepo, RoomCardRepository roomCardRepo, HeroCardRepository heroCardRepo) {
        this.spellCardRepo = spellCardRepo;
        this.roomCardRepo = roomCardRepo;
        this.heroCardRepo = heroCardRepo;
    }

    public List<SpellCard> createSpellCardDeck() {
        List<SpellCard> deck = spellCardRepo.findAll();
        Collections.shuffle(deck);
        return deck;
    }

    public List<RoomCard> createRoomCardDeck() {
        List<RoomCard> deck = roomCardRepo.findAll();
        Collections.shuffle(deck);
        return deck;
    }

    public List<HeroCard> createHeroCardDeck() {
        List<HeroCard> deck = heroCardRepo.findAll();
        Collections.shuffle(deck);
        return deck;
    }

    public void giveCard(List<Card> source, List<Card> target) {
        Card toGive = source.remove(source.size()-1);
        target.add(toGive);
    }

    
}
