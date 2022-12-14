package org.springframework.samples.bossmonster.game;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.bossmonster.game.card.Card;
import org.springframework.samples.bossmonster.game.card.CardService;
import org.springframework.samples.bossmonster.game.card.finalBoss.FinalBossCard;
import org.springframework.samples.bossmonster.game.card.hero.HeroCard;
import org.springframework.samples.bossmonster.game.card.room.RoomCard;
import org.springframework.samples.bossmonster.game.card.spell.SpellCard;
import org.springframework.samples.bossmonster.game.chat.Chat;
import org.springframework.samples.bossmonster.game.chat.ChatService;
import org.springframework.samples.bossmonster.game.gameState.GamePhase;
import org.springframework.samples.bossmonster.game.gameState.GameState;
import org.springframework.samples.bossmonster.game.gameState.GameSubPhase;
import org.springframework.samples.bossmonster.game.player.Player;
import org.springframework.samples.bossmonster.game.player.PlayerBuilder;
import org.springframework.samples.bossmonster.gameLobby.GameLobby;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Component
public class GameBuilder {

    CardService cardService;
    ChatService chatService;

    @Autowired
    public GameBuilder(CardService cardService,ChatService chatService) {
        this.cardService = cardService;
        this.chatService = chatService;
    }

    //////////////////////////   TRUE GAME   //////////////////////////

    public Game buildNewGame(GameLobby lobby) {
        Game newGame = new Game();
        buildHeroPile(newGame, lobby);
        buildSpellPile(newGame);
        buildRoomPile(newGame, lobby);
        buildFinalBossPile(newGame);
        buildDiscardPile(newGame);
        buildCity(newGame);
        buildPlayers(newGame,lobby.getJoinedUsers());
        buildStats(newGame, lobby.getJoinedUsers().size());
        buildChat(newGame);
        return newGame;
    }

    public void buildHeroPile(Game newGame, GameLobby lobby) {
        Integer players = lobby.getJoinedUsers().size();
        List<HeroCard> allHeroCards = cardService.createHeroCardDeck();
        List<HeroCard> selectedHeroCards = new ArrayList<>();
        for(HeroCard i: allHeroCards) {if (players >= i.getNecessaryPlayers()) selectedHeroCards.add(i);}
        newGame.setHeroPile(selectedHeroCards);
    }

    public void buildSpellPile(Game newGame) {
        List<SpellCard> spellPile = cardService.createSpellCardDeck();
        newGame.setSpellPile(spellPile);
    }

    public void buildRoomPile(Game newGame, GameLobby lobby) {
        List<RoomCard> roomPile = cardService.createRoomCardDeck();
        Collections.sort(roomPile, Comparator.comparing(room->!room.isAdvanced()));
        Collections.shuffle(roomPile.subList(lobby.getMaxPlayers()*3-1, roomPile.size()));
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

    public void buildChat(Game newGame){
        Chat chat= new Chat();
        newGame.setChat(chat);
        chatService.createChat(chat);
    }

    public void buildPlayers(Game newGame, List<User> users) {
        List<Player> players = new ArrayList<>();
        PlayerBuilder playerBuilder = new PlayerBuilder();
        playerBuilder.setCurrentRoomPile(newGame.getRoomPile());
        playerBuilder.setCurrentSpellPile(newGame.getSpellPile());
        playerBuilder.setCurrentRoomPile(newGame.getRoomPile());
        playerBuilder.setCurrentBossPile(newGame.getFinalBossPile());

        for (User i: users) {
            Player newPlayer = playerBuilder.buildNewPlayer(i);
            newPlayer.getDungeon().setGame(newGame);
            players.add(newPlayer);
        }

        newGame.setPlayers(players);
        newGame.setRoomPile(playerBuilder.getCurrentRoomPile());
        newGame.setFinalBossPile(playerBuilder.getCurrentBossPile());
        newGame.setSpellPile(playerBuilder.getCurrentSpellPile());
        newGame.sortPlayersByFinalBossEx();
    }

    public void buildStats(Game newGame, Integer totalPlayers) {
        newGame.setStartedTime(LocalDateTime.now());
        GameState state = new GameState();
        state.setCurrentPlayer(0);
        state.setPhase(GamePhase.START_GAME);
        state.setSubPhase(GameSubPhase.ANNOUNCE_NEW_PHASE);
        state.setTotalPlayers(totalPlayers);
        state.setCounter(0);
        state.setActionLimit(0);
        state.setCheckClock(true);
        state.setClock(LocalDateTime.now().plusSeconds(5));
        newGame.setState(state);
        state.setCurrentRound(1);
        state.setGame(newGame);
        state.setEffectIsBeingTriggered(false);
        newGame.setActive(true);
        newGame.setPreviousChoices(new Stack<>());
    }

}
