package org.springframework.samples.bossmonster.game.card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.bossmonster.game.card.spell.SpellCard;
import org.springframework.samples.bossmonster.game.card.spell.SpellCardRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CardService {

    SpellCardRepository spellCardRepo;

    @Autowired
    public CardService(SpellCardRepository spellCardRepo) {
        this.spellCardRepo = spellCardRepo;
    }

    public List<SpellCard> createSpellCardDeck() {
        List<SpellCard> deck = spellCardRepo.findAll();
        Collections.shuffle(deck);
        return deck;
    }

    public void giveCard(List<Card> source, List<Card> target) {
        Card toGive = source.remove(source.size()-1);
        target.add(toGive);
    }
}
