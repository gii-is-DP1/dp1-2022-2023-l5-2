package org.springframework.samples.bossmonster.game;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.bossmonster.game.card.Card;
import org.springframework.samples.bossmonster.game.card.CardService;
import org.springframework.samples.bossmonster.game.card.finalBoss.FinalBossCard;
import org.springframework.samples.bossmonster.game.card.hero.HeroCard;
import org.springframework.samples.bossmonster.game.card.room.RoomCard;
import org.springframework.samples.bossmonster.game.card.spell.SpellCard;
import org.springframework.samples.bossmonster.game.gamePhase.GamePhase;
import org.springframework.samples.bossmonster.game.player.Player;
import org.springframework.samples.bossmonster.game.player.PlayerBuilder;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class GameBuilder {

    public Game buildNewGame(List<User> users) {
        Game newGame = new Game();
        buildHeroPile(newGame, users);
        buildSpellPile(newGame);
        buildRoomPile(newGame);
        buildFinalBossPile(newGame);
        buildDiscardPile(newGame);
        buildCity(newGame);
        buildPlayers(newGame, users);
        buildStats(newGame);
        return newGame;
    }
    
    @Autowired
    CardService cardService;

    public void buildHeroPile(Game newGame, List<User> users) {
        Integer players = users.size();
        List<HeroCard> allHeroCards = cardService.createHeroCardDeck();
        List<HeroCard> selectedHeroCards = new ArrayList<>();
        for(HeroCard i: allHeroCards) { if (players <= i.getNecessaryPlayers()) selectedHeroCards.add(i);}
        newGame.setHeroPile(selectedHeroCards);
    }

    public void buildSpellPile(Game newGame) {
        List<SpellCard> spellPile = cardService.createSpellCardDeck();
        newGame.setSpellPile(spellPile);
    }

    public void buildRoomPile(Game newGame) {
        List<RoomCard> roomPile = cardService.createRoomCardDeck();
        newGame.setRoomPile(roomPile);
    }

    public void buildFinalBossPile(Game newGame) {
        List<FinalBossCard> roomPile = cardService.createBossCardDeck();
        newGame.setFinalBossPile(roomPile);
    }

    public void buildDiscardPile(Game newGame) {
        List<Card> discardPile = new ArrayList<>();
        newGame.setDiscardPile(discardPile);
    }

    public void buildCity(Game newGame) {
        List<HeroCard> city = new ArrayList<>();
        newGame.setCity(city);
    }

    public void buildPlayers(Game newGame, List<User> users) {
        List<Player> players = new ArrayList<>();
        PlayerBuilder playerBuilder = new PlayerBuilder();
        for (User i: users) {
            
            playerBuilder.setCurrentRoomPile(newGame.getRoomPile());
            playerBuilder.setCurrentSpellPile(newGame.getSpellPile());
            playerBuilder.setCurrentRoomPile(newGame.getRoomPile());

            Player newPlayer = playerBuilder.buildNewPlayer(i);
            players.add(newPlayer);

            newGame.setRoomPile(playerBuilder.getCurrentRoomPile());
            newGame.setSpellPile(playerBuilder.getCurrentSpellPile());
            newGame.setDiscardPile(playerBuilder.getCurrentDiscardPile());
        }
    }

    public void buildStats(Game newGame) {
        newGame.setStartedTime(LocalDateTime.now());
        newGame.setPhase(GamePhase.START_GAME);
        newGame.setCurrentPlayerTurn(0);
    }
}
