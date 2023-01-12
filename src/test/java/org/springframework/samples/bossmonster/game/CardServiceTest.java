package org.springframework.samples.bossmonster.game;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.bossmonster.game.card.Card;
import org.springframework.samples.bossmonster.game.card.CardService;
import org.springframework.samples.bossmonster.game.card.finalBoss.FinalBossCard;
import org.springframework.samples.bossmonster.game.card.finalBoss.FinalBossCardRepository;
import org.springframework.samples.bossmonster.game.card.hero.HeroCard;
import org.springframework.samples.bossmonster.game.card.hero.HeroCardRepository;
import org.springframework.samples.bossmonster.game.card.room.RoomCard;
import org.springframework.samples.bossmonster.game.card.room.RoomCardRepository;
import org.springframework.samples.bossmonster.game.card.spell.SpellCard;
import org.springframework.samples.bossmonster.game.card.spell.SpellCardRepository;
import org.springframework.samples.bossmonster.invitations.InvitationService;
import org.springframework.samples.bossmonster.social.FriendRequestService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@DataJpaTest(includeFilters = {@ComponentScan.Filter(Service.class)},
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = {GameService.class, FriendRequestService.class, InvitationService.class}))
public class CardServiceTest {

    @Mock
    RoomCardRepository roomMock;
    @Mock
    SpellCardRepository spellMock;
    @Mock
    HeroCardRepository heroMock;
    @Mock
    FinalBossCardRepository bossMock;

    @InjectMocks
    CardService cardService = new CardService(spellMock, roomMock, heroMock, bossMock);

    @Test
    @DisplayName("create Hero card Deck")
    public void shouldCreateHeroDeck() {

        HeroCard hero1 = new HeroCard();
        HeroCard hero2 = new HeroCard();
        HeroCard hero3 = new HeroCard();
        HeroCard hero4 = new HeroCard();

        when(heroMock.findAll()).thenReturn(Arrays.asList(hero1, hero2, hero3, hero4));
        List<HeroCard> deck = cardService.createHeroCardDeck();

        assertFalse(deck.isEmpty());
        assertTrue(deck.get(0) instanceof HeroCard);
    }

    @Test
    @DisplayName("create Spell card Deck")
    public void shouldCreateSpellDeck() {

        SpellCard card1 = new SpellCard();
        SpellCard card2 = new SpellCard();
        SpellCard card3 = new SpellCard();
        SpellCard card4 = new SpellCard();

        when(spellMock.findAll()).thenReturn(Arrays.asList(card1, card2, card3, card4));
        List<SpellCard> deck = cardService.createSpellCardDeck();

        assertFalse(deck.isEmpty());
        assertTrue(deck.get(0) instanceof SpellCard);
    }

    @Test
    @DisplayName("create Room card Deck")
    public void shouldCreateRoomDeck() {

        RoomCard card1 = new RoomCard();
        RoomCard card2 = new RoomCard();
        RoomCard card3 = new RoomCard();
        RoomCard card4 = new RoomCard();

        when(roomMock.findAll()).thenReturn(Arrays.asList(card1, card2, card3, card4));
        List<RoomCard> deck = cardService.createRoomCardDeck();

        assertFalse(deck.isEmpty());
        assertTrue(deck.get(0) instanceof RoomCard);
    }

    @Test
    @DisplayName("create Boss card Deck")
    public void shouldCreateBossDeck() {

        FinalBossCard card1 = new FinalBossCard();
        FinalBossCard card2 = new FinalBossCard();
        FinalBossCard card3 = new FinalBossCard();
        FinalBossCard card4 = new FinalBossCard();

        when(bossMock.findAll()).thenReturn(Arrays.asList(card1, card2, card3, card4));
        List<FinalBossCard> deck = cardService.createBossCardDeck();

        assertFalse(deck.isEmpty());
        assertTrue(deck.get(0) instanceof FinalBossCard);
    }

    @Test
    @DisplayName("give a card")
    public void shouldGetFirstCardFromDeck() {

        FinalBossCard card1 = new FinalBossCard();
        FinalBossCard card2 = new FinalBossCard();
        FinalBossCard card3 = new FinalBossCard();
        FinalBossCard card4 = new FinalBossCard();
        when(bossMock.findAll()).thenReturn(Arrays.asList(card1, card2, card3, card4));

        List<Card> deck = new ArrayList<>();
        deck.addAll(cardService.createBossCardDeck());
        List<Card> deck1 = new ArrayList<Card>(deck);
        List<Card> hand = new ArrayList<>();
        cardService.giveCard(deck, hand, 200);

        assertFalse(deck1.isEmpty());
        assertEquals(hand.get(0), deck1.get(deck1.size()-1));
        assertNotEquals(deck.get(deck.size()-1),deck1.get(deck1.size()-1));
    }
}
