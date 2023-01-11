package org.springframework.samples.bossmonster.game;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.bossmonster.game.card.CardService;
import org.springframework.samples.bossmonster.game.card.finalBoss.FinalBossCard;
import org.springframework.samples.bossmonster.game.card.hero.HeroCard;
import org.springframework.samples.bossmonster.game.card.room.RoomCard;
import org.springframework.samples.bossmonster.game.card.spell.SpellCard;
import org.springframework.samples.bossmonster.game.chat.ChatService;
import org.springframework.samples.bossmonster.game.gameState.GamePhase;
import org.springframework.samples.bossmonster.game.gameState.GameSubPhase;
import org.springframework.samples.bossmonster.gameLobby.GameLobby;
import org.springframework.samples.bossmonster.user.User;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(properties="spring.main.lazy-initialization=true")
public class GameBuilderTest {

    @Autowired
    GameBuilder gameBuilder;

    @MockBean
    ChatService chatService;

    @MockBean
    CardService cardService;

    @MockBean
    GameLobby gameLobby;

    Game newGame;

    @BeforeEach
    void setUp() {
        this.gameBuilder = new GameBuilder(cardService, chatService);
        given(this.cardService.createHeroCardDeck()).willReturn(setUpDummyHeroDeck());
        given(this.cardService.createSpellCardDeck()).willReturn(setUpDummySpellDeck());
        given(this.cardService.createRoomCardDeck()).willReturn(setUpDummyRoomDeck());
        given(this.cardService.createBossCardDeck()).willReturn(setUpDummyBossDeck());
        given(this.gameLobby.getJoinedUsers()).willReturn(List.of(new User(), new User()));
        newGame = new Game();
    }

    private List<HeroCard> setUpDummyHeroDeck() {
        List<HeroCard> deck = new ArrayList<>();
        for (int i = 0; i < 30; i ++) {
            HeroCard hero = new HeroCard();
            hero.setNecessaryPlayers(2 + (int) Math.floor(i/10));
            deck.add(hero);
        }
        return deck;
    }

    private List<SpellCard> setUpDummySpellDeck() {
        List<SpellCard> deck = new ArrayList<>();
        for (int i = 0; i < 10; i ++) {
            SpellCard spell = new SpellCard();
            deck.add(spell);
        }
        return deck;
    }

    private List<RoomCard> setUpDummyRoomDeck() {
        List<RoomCard> deck = new ArrayList<>();
        for (int i = 0; i < 15; i ++) {
            RoomCard room = new RoomCard();
            deck.add(room);
        }
        return deck;
    }

    private List<FinalBossCard> setUpDummyBossDeck() {
        List<FinalBossCard> deck = new ArrayList<>();
        for (int i = 0; i < 6; i ++) {
            FinalBossCard boss = new FinalBossCard();
            boss.setXp(100);
            deck.add(boss);
        }
        return deck;
    }

    @Test
    void shouldBuildHeroPile() {
        gameBuilder.buildHeroPile(newGame, gameLobby);
        Integer heroCardsReturned = newGame.getHeroPile().size();
        assertEquals(10, heroCardsReturned);
    }

    @Test
    void shouldBuildSpellPile() {
        gameBuilder.buildSpellPile(newGame);
        Integer spellCardsReturned = newGame.getSpellPile().size();
        assertEquals(10, spellCardsReturned);
    }

    @Test
    void shouldBuildRoomPile() {
        gameBuilder.buildRoomPile(newGame);
        Integer roomCardsReturned = newGame.getRoomPile().size();
        assertEquals(15, roomCardsReturned);
    }

    @Test
    void shouldBuildBossPile() {
        gameBuilder.buildFinalBossPile(newGame);
        Integer bossCardsReturned = newGame.getFinalBossPile().size();
        assertEquals(6, bossCardsReturned);
    }

    @Test
    void shouldBuildDiscardPile() {
        assertNull(newGame.getDiscardPile());
        gameBuilder.buildDiscardPile(newGame);
        assertEquals(new ArrayList<>(), newGame.getDiscardPile());
    }
    
    @Test
    void shouldBuildCity() {
        assertNull(newGame.getCity());
        gameBuilder.buildCity(newGame);
        assertEquals(new ArrayList<>(), newGame.getCity());
    }

    @Test
    void shouldBuildNewPlayers() {
        gameBuilder.buildHeroPile(newGame, gameLobby);
        gameBuilder.buildSpellPile(newGame);
        gameBuilder.buildRoomPile(newGame);
        gameBuilder.buildFinalBossPile(newGame);
        gameBuilder.buildDiscardPile(newGame);
        Integer expectedRoomCards = newGame.getRoomPile().size() - 6;
        Integer expectedSpellCards = newGame.getSpellPile().size() - 4;
        Integer expectedBossCards = newGame.getFinalBossPile().size() - 2;
        // - 6 room cards, - 4 spell cards, - 2 boss cards
        gameBuilder.buildPlayers(newGame, List.of(new User(), new User()));
        assertEquals(expectedRoomCards, newGame.getRoomPile().size());
        assertEquals(expectedSpellCards, newGame.getSpellPile().size());
        assertEquals(expectedBossCards, newGame.getFinalBossPile().size());
        assertEquals(2, newGame.getPlayers().size());
        
    }

    @Test
    void shouldBuildStats() {
        gameBuilder.buildStats(newGame, 2);
        assertEquals(0, newGame.getState().getCurrentPlayer());
        assertEquals(GamePhase.START_GAME, newGame.getState().getPhase());
        assertEquals(GameSubPhase.ANNOUNCE_NEW_PHASE, newGame.getState().getSubPhase());
        assertEquals(2, newGame.getState().getTotalPlayers());
        assertEquals(0, newGame.getState().getCounter());
        assertEquals(0, newGame.getState().getActionLimit());
        assertEquals(true, newGame.getState().getCheckClock());
        assertEquals(1, newGame.getState().getCurrentRound());
    }

}
