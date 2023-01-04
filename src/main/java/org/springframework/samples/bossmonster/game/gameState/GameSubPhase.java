package org.springframework.samples.bossmonster.game.gameState;

import lombok.Getter;
import org.springframework.samples.bossmonster.game.Game;
import org.springframework.samples.bossmonster.game.card.Card;
import org.springframework.samples.bossmonster.game.card.room.RoomCard;
import org.springframework.samples.bossmonster.game.card.room.RoomType;
import org.springframework.samples.bossmonster.game.card.spell.SpellCard;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public enum GameSubPhase implements SubPhaseChoices{

    // COMMON
    ANNOUNCE_NEW_PHASE(g->g.getState().getPhase().getStartPhaseMessage()) {
    },
    ANNOUNCE_NEW_PLAYER(g->String.format("It is now %s's turn!",g.getCurrentPlayer())),
    USE_SPELLCARD(g->String.format("%s considers their spells...",g.getCurrentPlayer()),
        "Choose a spell to activate") {
        @Override
        public List<Card> getChoice(Game game) {
            return game.getCurrentPlayerHand();
        }

        @Override
        public Boolean isValidChoice(Integer choice, Game game) {
            Card card = getChoice(game).get(choice);
            return card instanceof SpellCard;
        }

        @Override
        public void makeChoice(Game game, Integer choice) {
            Card spell = game.getCurrentPlayerHand().get(choice);
            game.triggerSpellCardEffect((SpellCard) spell);
            game.discardCard(game.getCurrentPlayer(), choice);
        }
    },

    // START_GAME
    DISCARD_2_STARTING_CARDS(g->String.format("%s is discarding their cards...",g.getCurrentPlayer()),
        "Choose a card to discard") {
        @Override
        public List<Card> getChoice(Game game) {
            return game.getCurrentPlayerHand();
        }

        @Override
        public Boolean isValidChoice(Integer choice, Game game) {
            return true;
        }

        @Override
        public Boolean isOptional() {
            return false;
        }

        @Override
        public void makeChoice(Game game, Integer choice) {
            game.discardCard(game.getCurrentPlayer(),choice);
        }
    },
    PLACE_FIRST_ROOM(g->String.format("%s is building their first room...",g.getCurrentPlayer()),
        "Choose a room to build"){
        @Override
        public List<Card> getChoice(Game game) {
            return game.getCurrentPlayerHand();
        }

        @Override
        public Boolean isValidChoice(Integer choice, Game game) {
            Card card = getChoice(game).get(choice);
            return card instanceof RoomCard;
        }

        @Override
        public Boolean isOptional() {
            return false;
        }

        @Override
        public void makeChoice(Game game, Integer choice) {
            Card room = game.getCurrentPlayerHand().get(choice);
            game.placeFirstRoom(game.getCurrentPlayer(), (RoomCard) room);
        }
    },

    // START_ROUND
    REVEAL_HEROES(g->"New heroes arrived at the city!"),
    GET_ROOM_CARD(g->"The Bosses get a new room to build..."),

    // BUILD
    BUILD_NEW_ROOM(g->String.format("%s is considering building a room...",g.getCurrentPlayer()),
        "Choose a room to build and its place in your dungeon"){
        @Override
        public List<Card> getChoice(Game game) {
            if(!game.getState().isBuildingRoom()) return game.getCurrentPlayerHand();
            return Arrays.stream(game.getCurrentPlayer().getDungeon().getRoomSlots())
                .map(slot->slot.getRoom())
                .collect(Collectors.toList());
        }

        @Override
        public Boolean isValidChoice(Integer choice, Game game) {
            if(!game.getState().isBuildingRoom()) {
                Card card = getChoice(game).get(choice);
                return card instanceof RoomCard;
            }
            Card room = game.getCurrentPlayerHand().get(game.getRoomToBuildFromHand());

            return game.checkPlaceableRoomInDungeonPosition(game.getCurrentPlayer(), choice, (RoomCard) room);
        }

        @Override
        public void makeChoice(Game game, Integer choice) {
            if(!game.getState().isBuildingRoom()) {
                game.setRoomToBuildFromHand(choice);
            } else {
                RoomCard card = (RoomCard) game.getCurrentPlayerHand().get(game.getRoomToBuildFromHand());
                game.placeDungeonRoom(game.getCurrentPlayer(), choice,card);
                game.setRoomToBuildFromHand(null);
            }

        }
    },
    REVEAL_NEW_ROOMS(g->"The newly built rooms get revealed!"),

    // LURE
    HEROES_ENTER_DUNGEON(g->"The heroes enter the dungeons!"),

    // ADVENTURE
    HEROES_EXPLORE_DUNGEON(g->String.format("The heroes advance through %s's Dungeon!",g.getCurrentPlayer())),

    // EFFECT
    CHOOSE_A_CARD_FROM_DISCARD_PILE(g->String.format("%s is getting a card from the discard pile...",g.getCurrentPlayer()),
        "Choose a card to add to your hand"){
        @Override
        public List<Card> getChoice(Game game) {
            return game.getDiscardPile();
        }

        @Override
        public Boolean isValidChoice(Integer choice, Game game) {
            return true;
        }

        @Override
        public Boolean isOptional() {
            return false;
        }

        @Override
        public void makeChoice(Game game, Integer choice) {
            game.getCardFromDiscardPile(game.getCurrentPlayer(),choice);
        }
    },
    CHOOSE_A_ROOM_CARD_FROM_DISCARD_PILE(g->String.format("%s is getting a room from the discard pile...",g.getCurrentPlayer()),
        "Choose a room to add to your hand"){
        @Override
        public List<Card> getChoice(Game game) {
            return game.getDiscardPile();
        }

        @Override
        public Boolean isValidChoice(Integer choice, Game game) {
            Card card = getChoice(game).get(choice);
            return card instanceof RoomCard;
        }

        @Override
        public Boolean isOptional() {
            return false;
        }

        @Override
        public void makeChoice(Game game, Integer choice) {
            game.getCardFromDiscardPile(game.getCurrentPlayer(),choice);
        }
    },
    CHOOSE_A_MONSTER_ROOM_CARD_FROM_DISCARD_PILE(g->String.format("%s is getting a monster room from the discard pile...",g.getCurrentPlayer()),
        "Choose a monster room to add to your hand"){
        @Override
        public List<Card> getChoice(Game game) {
            return game.getDiscardPile();
        }

        @Override
        public Boolean isValidChoice(Integer choice, Game game) {
            Card card = getChoice(game).get(choice);
            return card instanceof RoomCard && ((RoomCard) card).isMonsterType();
        }

        @Override
        public Boolean isOptional() {
            return false;
        }

        @Override
        public void makeChoice(Game game, Integer choice) {
            game.getCardFromDiscardPile(game.getCurrentPlayer(),choice);
        }
    },
    DISCARD_A_SPELL_CARD(g->String.format("%s is choosing a spell to discard...",g.getCurrentPlayer()),
        "Choose a spell to discard"){
        @Override
        public List<Card> getChoice(Game game) {
            return game.getCurrentPlayerHand();
        }

        @Override
        public Boolean isValidChoice(Integer choice, Game game) {
            Card card = getChoice(game).get(choice);
            return card instanceof SpellCard;
        }

        @Override
        public Boolean isOptional() {
            return false;
        }

        @Override
        public void makeChoice(Game game, Integer choice) {
            game.discardCard(game.getCurrentPlayer(),choice);
        }
    };

    Function<Game, String> contextualMessage;
    String choiceMessage;

    GameSubPhase(Function<Game, String> contextualMessage, String choiceMessage) {
        this.contextualMessage = contextualMessage;
        this.choiceMessage = choiceMessage;
    }

    GameSubPhase(Function<Game, String> contextualMessage) {
        this.contextualMessage = contextualMessage;
    }
}
