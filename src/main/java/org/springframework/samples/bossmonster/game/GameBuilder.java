package org.springframework.samples.bossmonster.game;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.samples.bossmonster.game.card.Card;
import org.springframework.samples.bossmonster.game.card.hero.HeroCard;
import org.springframework.samples.bossmonster.game.card.room.RoomCard;
import org.springframework.samples.bossmonster.game.card.spell.SpellCard;
import org.springframework.samples.bossmonster.user.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
abstract class GameBuilder {
    
    protected Game newGame;
    private List<User> users;

    public void buildNewGame() {
        buildHeroPile();
        buildSpellPile();
        buildRoomPile();
        buildDiscardPile();
        buildCity();
        buildPlayers();
        newGame.setStartedTime(LocalDateTime.now());
        newGame.setPhase(GamePhase.START_GAME);
    }

    public void buildHeroPile() {
        Integer players = users.size();
        List<HeroCard> allHeroCards = new ArrayList<>();
        List<HeroCard> selectedHeroCards = new ArrayList<>();

        // TODO recoge las cartas del servicio

        for(HeroCard i: allHeroCards) {if (players <= i.getNecessaryPlayers()) selectedHeroCards.add(i);}
        newGame.setHeroPile(selectedHeroCards);
    }

    public void buildSpellPile() {
        List<SpellCard> spellPile = new ArrayList<>();

        // TODO recoge las cartas del servicio

        newGame.setSpellPile(spellPile);
    }

    public void buildRoomPile() {
        List<RoomCard> roomPile = new ArrayList<>();

        // TODO recoge las cartas del servicio

        newGame.setRoomPile(roomPile);
    }

    public void buildDiscardPile() {
        List<Card> discardPile = new ArrayList<>();
        newGame.setDiscardPile(discardPile);
    }

    public void buildCity() {
        List<HeroCard> city = new ArrayList<>();
        newGame.setCity(city);
    }

    public void buildPlayers() {
        for (User i: users) {
            // No me deja llamar a PlayerBuilder. Protected...?
        }
    }
}
