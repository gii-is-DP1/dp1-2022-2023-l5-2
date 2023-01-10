package org.springframework.samples.bossmonster.game.gameState;

import lombok.Getter;
import org.jpatterns.gof.StrategyPattern;
import org.springframework.samples.bossmonster.game.Game;
import org.springframework.samples.bossmonster.game.card.Card;
import org.springframework.samples.bossmonster.game.card.hero.HeroCard;
import org.springframework.samples.bossmonster.game.card.hero.HeroCardStateInDungeon;
import org.springframework.samples.bossmonster.game.card.room.RoomCard;
import org.springframework.samples.bossmonster.game.card.room.RoomType;
import org.springframework.samples.bossmonster.game.card.spell.SpellCard;
import org.springframework.samples.bossmonster.game.dungeon.Dungeon;
import org.springframework.samples.bossmonster.game.dungeon.DungeonRoomSlot;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@StrategyPattern.ConcreteStrategy
public enum GameSubPhase implements SubPhaseChoices{

    // COMMON
    ANNOUNCE_NEW_PHASE(g->g.getState().getPhase().getStartPhaseMessage()) {
        @Override
        public Integer getClockLimit() {
            return GameState.PHASE_COOLDOWN_SECONDS;
        }
    },
    ANNOUNCE_NEW_PLAYER(g->String.format("It is now %s's turn!",g.getCurrentPlayer())){
        @Override
        public Integer getClockLimit() {
            return GameState.PLAYER_COOLDOWN_SECONDS;
        }
    },
    USE_SPELLCARD(g->String.format("%s considers their spells...",g.getCurrentPlayer()),
        "Choose a spell to activate") {
        @Override
        public List<Card> getChoice(Game game) {
            return game.getCurrentPlayerHand();
        }

        @Override
        public Boolean isValidChoice(int choice, Game game) {
            Card card = getChoice(game).get(choice);
            GamePhase currentPhase = game.getState().getPhase();
            return card instanceof SpellCard &&
                ((SpellCard) card).getPhase().getTriggerPhases().contains(currentPhase) ;
        }

        @Override
        public void makeChoice(Game game, int choice) {
            Card spell = game.getCurrentPlayerHand().get(choice);
            game.triggerSpellCardEffect((SpellCard) spell);
            game.discardCard(game.getCurrentPlayer(), choice);
            game.decrementCounter();
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
        public Boolean isValidChoice(int choice, Game game) {
            return true;
        }

        @Override
        public Boolean isOptional() {
            return false;
        }

        @Override
        public void makeChoice(Game game, int choice) {
            game.discardCard(game.getCurrentPlayer(),choice);
        }

        @Override
        public Integer getActionLimit() {
            return GameState.START_GAME_DISCARDED_CARDS;
        }
    },
    PLACE_FIRST_ROOM(g->String.format("%s is building their first room...",g.getCurrentPlayer()),
        "Choose a room to build"){
        @Override
        public List<Card> getChoice(Game game) {
            return game.getCurrentPlayerHand();
        }

        @Override
        public Boolean isValidChoice(int choice, Game game) {
            Card card = getChoice(game).get(choice);
            return card instanceof RoomCard && !((RoomCard) card).isAdvanced();
        }

        @Override
        public Boolean isOptional() {
            return false;
        }

        @Override
        public void makeChoice(Game game, int choice) {
            Card room = game.getCurrentPlayerHand().get(choice);
            game.placeFirstRoom(game.getCurrentPlayer(), (RoomCard) room);
        }

        @Override
        public Integer getActionLimit() {
            return GameState.START_GAME_ROOMS_PLACED;
        }
    },

    // START_ROUND
    REVEAL_HEROES(g->"New heroes arrived at the city!") {
        @Override
        public Integer getClockLimit() {
            return GameState.SHOW_HEROES_COOLDOWN_SECONDS;
        }
    },
    GET_ROOM_CARD(g->"The Bosses get a new room to build...") {
        @Override
        public Integer getClockLimit() {
            return GameState.SHOW_NEW_ROOMCARD_COOLDOWN_SECONDS;
        }
    },

    // BUILD
    BUILD_NEW_ROOM(g->String.format("%s is considering building a room...",g.getCurrentPlayer()),
        "Choose a room to build and its place in your dungeon"){
        @Override
        public List<Card> getChoice(Game game) {
            if(game.getPreviousChoices().empty()) return game.getCurrentPlayerHand();
            return new ArrayList<>(game.getCurrentPlayer().getDungeon().getRooms());
        }

        @Override
        public Boolean isValidChoice(int choice, Game game) {
            if(game.getPreviousChoices().empty()) {
                Card card = getChoice(game).get(choice);
                return card instanceof RoomCard;
            }
            Card room = game.getCurrentPlayerHand().get(game.getPreviousChoices().peek());

            return game.checkPlaceableRoomInDungeonPosition(game.getCurrentPlayer(), choice, (RoomCard) room);
        }

        @Override
        public void makeChoice(Game game, int choice) {
            if(game.getPreviousChoices().empty()) {
                game.getPreviousChoices().add(choice);
            } else {
                RoomCard card = (RoomCard) game.getCurrentPlayerHand().get(game.getPreviousChoices().peek());
                game.placeDungeonRoom(game.getCurrentPlayer(), choice,card, false);
                game.getPreviousChoices().removeAllElements();
            }

        }

        @Override
        public Integer getActionLimit() {
            return GameState.BUILD_ROOM_ACTIONS;
        }

        @Override
        public boolean canDecrementCounter() {
            return true;
        }
    },
    REVEAL_NEW_ROOMS(g->"The newly built rooms get revealed!") {
        @Override
        public Integer getClockLimit() {
            return GameState.SHOW_ROOMS_COOLDOWN_SECONDS;
        }
    },

    // LURE
    HEROES_ENTER_DUNGEON(g->"The heroes enter the dungeons!") {
        @Override
        public Integer getClockLimit() {
            return GameState.SHOW_HEROES_COOLDOWN_SECONDS;
        }
    },

    // ADVENTURE
    HEROES_EXPLORE_DUNGEON(g->String.format("The heroes advance through %s's Dungeon!",g.getCurrentPlayer())) {
        @Override
        public Integer getClockLimit() {
            return GameState.SHOW_HEROES_COOLDOWN_SECONDS;
        }
    },

    // EFFECT
    CHOOSE_A_CARD_FROM_DISCARD_PILE(g->String.format("%s is getting a card from the discard pile...",g.getCurrentPlayer()),
        "Choose a card to add to your hand"){
        @Override
        public List<Card> getChoice(Game game) {
            return game.getDiscardPile();
        }

        @Override
        public Boolean isValidChoice(int choice, Game game) {
            return true;
        }

        @Override
        public Boolean isOptional() {
            return false;
        }

        @Override
        public void makeChoice(Game game, int choice) {
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
        public Boolean isValidChoice(int choice, Game game) {
            Card card = getChoice(game).get(choice);
            return card instanceof RoomCard;
        }

        @Override
        public Boolean isOptional() {
            return false;
        }

        @Override
        public void makeChoice(Game game, int choice) {
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
        public Boolean isValidChoice(int choice, Game game) {
            Card card = getChoice(game).get(choice);
            return card instanceof RoomCard && ((RoomCard) card).isMonsterType();
        }

        @Override
        public Boolean isOptional() {
            return false;
        }

        @Override
        public void makeChoice(Game game, int choice) {
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
        public Boolean isValidChoice(int choice, Game game) {
            Card card = getChoice(game).get(choice);
            return card instanceof SpellCard;
        }

        @Override
        public Boolean isOptional() {
            return false;
        }

        @Override
        public void makeChoice(Game game, int choice) {
            game.discardCard(game.getCurrentPlayer(),choice);
        }
    },

    CHOOSE_SPELL_FROM_SPELL_PILE(g->String.format("%s is choosing a spell from the spell pile...",g.getCurrentPlayer()),
        "Choose a spell to add to your hand") {
        @Override
        public List<Card> getChoice(Game game) {
            List<Card> pile = new ArrayList<>(game.getSpellPile());
            return pile;
        }

        @Override
        public void makeChoice(Game game, int choice) {
            Card cardToGive = game.getSpellPile().remove(choice);
            game.getCurrentPlayerHand().add(cardToGive);
        }

        @Override
        public Boolean isValidChoice(int choice, Game game) {
            return true;
        }
    },

    CHOOSE_2_CARDS_FROM_DISCARD_PILE(g->String.format("%s is getting cards from the discard pile...",g.getCurrentPlayer()),
        "Choose a card to add to your hand") {
        @Override
        public List<Card> getChoice(Game game) {
            return game.getDiscardPile();
        }

        @Override
        public void makeChoice(Game game, int choice) {
            Card cardToGive = game.getDiscardPile().remove(choice);
            game.getCurrentPlayerHand().add(cardToGive);
        }

        @Override
        public Boolean isValidChoice(int choice, Game game) {
            return true;
        }

        @Override
        public Integer getActionLimit() {
            return 2;
        }
    },
    BUILD_ADVANCED_MONSTER_FROM_DISCARD_OR_ROOM_PILE(g->String.format("%s is building an advanced room from a pile of their choosing...",g.getCurrentPlayer()),
        "Choose a pile and build an advanced monster from it") {
        @Override
        public List<Card> getChoice(Game game) {

            switch(game.getState().getCounter()) {
                case 0: return List.of(Card.DISCARD_PILE_CARD, Card.ROOM_PILE_CARD);
                case 1: {
                    if(game.getPreviousChoices().peek().equals(0))
                        return game.getDiscardPile();
                    return new ArrayList<>(game.getRoomPile());
                }
                case 2: return new ArrayList<>(game.getCurrentPlayer().getDungeon().getRooms());
                default: return List.of();
            }
        }

        @Override
        public void makeChoice(Game game, int choice) {
            switch (game.getState().getCounter()) {
                case 0:
                case 1: {
                    game.getPreviousChoices().add(choice);
                    break;
                }
                case 2: {
                    Integer chosenPile = game.getPreviousChoices().firstElement();
                    RoomCard cardToBuild = chosenPile.equals(0)?
                        (RoomCard) game.getDiscardPile().get(game.getPreviousChoices().peek()) :
                        game.getRoomPile().get(game.getPreviousChoices().peek());
                    game.placeDungeonRoom(game.getCurrentPlayer(),choice,cardToBuild, true);
                    game.getPreviousChoices().removeAllElements();
                    break;
                }
                default:
                    break;
            }
        }

        @Override
        public Boolean isValidChoice(int choice, Game game) {
            switch (game.getState().getCounter()) {
                case 0: return true;
                case 1: {
                    Integer chosenPile = game.getPreviousChoices().peek();
                    RoomCard chosenCard = chosenPile.equals(0)?
                        (RoomCard) game.getDiscardPile().get(choice) :
                        game.getRoomPile().get(choice);
                    return chosenCard.getRoomType().equals(RoomType.ADVANCED_MONSTER);
                }
                case 2: {
                    Integer chosenPile = game.getPreviousChoices().firstElement();
                    RoomCard cardToBuild = chosenPile.equals(0)?
                        (RoomCard) game.getDiscardPile().get(game.getPreviousChoices().peek()) :
                        game.getRoomPile().get(game.getPreviousChoices().peek());
                    RoomCard oldRoom = game.getCurrentPlayer().getDungeon().getRoom(choice);
                    return oldRoom != null && oldRoom.parseHasTreasureType().equals(cardToBuild.parseHasTreasureType());
                }
                default: return false;
            }
        }

        @Override
        public Integer getActionLimit() {
            return 3;
        }

        @Override
        public boolean canDecrementCounter() {
            return true;
        }

        @Override
        public Boolean isOptional() {
            return true;
        }
    }, LURE_HERO_FROM_HERO_OR_CITY_PILE(g->String.format("%s is choosing a hero to lure to their dungeon...",g.getCurrentPlayer()),
        "Choose a pile and a hero from it to lure to your dungeon") {
        @Override
        public List<Card> getChoice(Game game) {
            if(game.getPreviousChoices().empty())
                return List.of(Card.CITY_PILE_CARD,Card.HERO_PILE_CARD);
            Integer chosenPile = game.getPreviousChoices().peek();
            if(chosenPile.equals(0))
                return new ArrayList<>(game.getCity());
            return new ArrayList<>(game.getHeroPile());
        }

        @Override
        public void makeChoice(Game game, int choice) {
            if(game.getPreviousChoices().empty()) {
                game.getPreviousChoices().add(choice);
                return;
            }
            Integer chosenPile = game.getPreviousChoices().peek();
            HeroCard chosenHero = chosenPile.equals(0)?
                game.getCity().get(choice) :
                game.getHeroPile().remove(choice);
            game.getCurrentPlayer().getDungeon().addNewHeroToDungeon(chosenHero);
            game.getPreviousChoices().removeAllElements();
        }

        @Override
        public Boolean isValidChoice(int choice, Game game) {
            return true;
        }

        @Override
        public Boolean isOptional() {
            return true;
        }

        @Override
        public Integer getActionLimit() {
            return 2;
        }

        @Override
        public boolean canDecrementCounter() {
            return true;
        }
    }, BUILD_ADVANCED_TRAP_FROM_DISCARD_OR_ROOM_PILE(g->String.format("%s is building an advanced room from a pile of their choosing...",g.getCurrentPlayer()),
        "Choose a pile and build an advanced trap from it") {
        @Override
        public List<Card> getChoice(Game game) {

            switch(game.getState().getCounter()) {
                case 0: return List.of(Card.DISCARD_PILE_CARD, Card.ROOM_PILE_CARD);
                case 1: {
                    if(game.getPreviousChoices().peek().equals(0))
                        return game.getDiscardPile();
                    return new ArrayList<>(game.getRoomPile());
                }
                case 2: return new ArrayList<>(game.getCurrentPlayer().getDungeon().getRooms());
                default: return List.of();
            }
        }

        @Override
        public void makeChoice(Game game, int choice) {
            switch (game.getState().getCounter()) {
                case 0:
                case 1: {
                    game.getPreviousChoices().add(choice);
                    break;
                }
                case 2: {
                    Integer chosenPile = game.getPreviousChoices().firstElement();
                    RoomCard cardToBuild = chosenPile.equals(0)?
                        (RoomCard) game.getDiscardPile().get(game.getPreviousChoices().peek()) :
                        game.getRoomPile().get(game.getPreviousChoices().peek());
                    game.placeDungeonRoom(game.getCurrentPlayer(),choice,cardToBuild, true);
                    game.getPreviousChoices().removeAllElements();
                    break;
                }
                default:
                    break;
            }
        }

        @Override
        public Boolean isValidChoice(int choice, Game game) {
            switch (game.getState().getCounter()) {
                case 0: return true;
                case 1: {
                    Integer chosenPile = game.getPreviousChoices().peek();
                    RoomCard chosenCard = chosenPile.equals(0)?
                        (RoomCard) game.getDiscardPile().get(choice) :
                        game.getRoomPile().get(choice);
                    return chosenCard.getRoomType().equals(RoomType.ADVANCED_TRAP);
                }
                case 2: {
                    Integer chosenPile = game.getPreviousChoices().firstElement();
                    RoomCard cardToBuild = chosenPile.equals(0)?
                        (RoomCard) game.getDiscardPile().get(game.getPreviousChoices().peek()) :
                        game.getRoomPile().get(game.getPreviousChoices().peek());
                    RoomCard oldRoom = game.getCurrentPlayer().getDungeon().getRoom(choice);
                    return oldRoom != null && oldRoom.parseHasTreasureType().equals(cardToBuild.parseHasTreasureType());
                }
                default: return false;
            }
        }

        @Override
        public Integer getActionLimit() {
            return 3;
        }

        @Override
        public boolean canDecrementCounter() {
            return true;
        }

        @Override
        public Boolean isOptional() {
            return true;
        }

    }, ADD_3_DAMAGE_TO_CHOSEN_MONSTER_ROOM(g->String.format("%s is choosing a room to strengthen...",g.getCurrentPlayer()),
        "Choose a monster room to add 3 points of damage to") {
        @Override
        public List<Card> getChoice(Game game) {
            return new ArrayList<>(game.getCurrentPlayer().getDungeon().getRooms());
        }

        @Override
        public void makeChoice(Game game, int choice) {
            DungeonRoomSlot chosenSlot = game.getCurrentPlayer().getDungeon().getRoomSlots()[choice];
            chosenSlot  .setRoomTrueDamage(chosenSlot.getRoomTrueDamage()+3);
        }

        @Override
        public Boolean isValidChoice(int choice, Game game) {
            RoomCard chosenRoom = game.getCurrentPlayer().getDungeon().getRoom(choice);
            return chosenRoom != null && chosenRoom.isMonsterType();
        }
    }, LURE_HERO_FROM_CITY(g->String.format("%s is choosing a hero to lure to their dungeon...",g.getCurrentPlayer()),
        "Choose a hero to lure to your dungeon") {
        @Override
        public List<Card> getChoice(Game game) {
            return new ArrayList<>(game.getCity());
        }

        @Override
        public void makeChoice(Game game, int choice) {
            HeroCard selected = game.getCity().remove(choice);
            game.getCurrentPlayer().getDungeon().addNewHeroToDungeon(selected);
        }

        @Override
        public Boolean isValidChoice(int choice, Game game) {
            return true;
        }
    }, SEND_HERO_TO_FIRST_ROOM(g->String.format("%s is choosing a hero to send to the first room...",g.getCurrentPlayer()),
        "Choose a room and a hero to send back to the first room") {
        @Override
        public List<Card> getChoice(Game game) {
            if(game.getPreviousChoices().empty())
                return new ArrayList<>(game.getCurrentPlayer().getDungeon().getRooms());
            Integer chosenSlot = game.getPreviousChoices().peek();
            return game.getCurrentPlayer().getDungeon().getRoomSlots()[chosenSlot]
                .getHeroesInRoom().stream()
                .map(h->h.getHeroCard())
                .collect(Collectors.toList());
        }

        @Override
        public void makeChoice(Game game, int choice) {
            if(game.getPreviousChoices().empty()) {
                game.getPreviousChoices().add(choice);
                return;
            }
            Integer chosenSlot = game.getPreviousChoices().peek();
            Dungeon dungeon = game.getCurrentPlayer().getDungeon();
            DungeonRoomSlot slot = dungeon.getRoomSlots()[chosenSlot];
            DungeonRoomSlot slotToSendHeroTo = dungeon.getRoomSlots()[dungeon.getBuiltRooms()-1];
            HeroCardStateInDungeon hero = slot.getHeroesInRoom().remove(choice);
            slotToSendHeroTo.getHeroesInRoom().add(hero);
            game.getPreviousChoices().removeAllElements();
        }

        @Override
        public Boolean isValidChoice(int choice, Game game) {
            return game.getPreviousChoices().empty()?
                game.getCurrentPlayer().getDungeon().getRoom(choice) != null:
                true;
        }

        @Override
        public Boolean isOptional() {
            return true;
        }

        @Override
        public Integer getActionLimit() {
            return 2;
        }

        @Override
        public boolean canDecrementCounter() {
            return true;
        }
    }, DEAL_X_DAMAGE_TO_HERO_IN_DUNGEON(g->String.format("%s is choosing a hero to damage in their dungeon...",g.getCurrentPlayer()),
        "Choose a room and a hero to damage in it") {
        @Override
        public List<Card> getChoice(Game game) {
            if(game.getPreviousChoices().empty())
                return new ArrayList<>(game.getCurrentPlayer().getDungeon().getRooms());
            Integer chosenSlot = game.getPreviousChoices().peek();
            return game.getCurrentPlayer().getDungeon().getRoomSlots()[chosenSlot]
                .getHeroesInRoom().stream()
                .map(h->h.getHeroCard())
                .collect(Collectors.toList());
        }

        @Override
        public void makeChoice(Game game, int choice) {
            if(game.getPreviousChoices().empty()) {
                game.getPreviousChoices().add(choice);
                return;
            }
            Integer chosenSlot = game.getPreviousChoices().peek();
            Dungeon dungeon = game.getCurrentPlayer().getDungeon();
            DungeonRoomSlot slot = dungeon.getRoomSlots()[chosenSlot];
            HeroCardStateInDungeon hero = slot.getHeroesInRoom().get(choice);
            hero.dealDamage(dungeon.getBuiltRooms());
            game.getPreviousChoices().removeAllElements();
        }

        @Override
        public Boolean isValidChoice(int choice, Game game) {
            return game.getPreviousChoices().empty()?
                game.getCurrentPlayer().getDungeon().getRoom(choice) != null:
                true;
        }

        @Override
        public Boolean isOptional() {
            return true;
        }

        @Override
        public Integer getActionLimit() {
            return 2;
        }

        @Override
        public boolean canDecrementCounter() {
            return true;
        }
    }, ADD_3_DAMAGE_TO_CHOSEN_TRAP_ROOM(g->String.format("%s is choosing a room to strengthen...",g.getCurrentPlayer()),
        "Choose a trap room to add 3 points of damage to") {
        @Override
        public List<Card> getChoice(Game game) {
            return new ArrayList<>(game.getCurrentPlayer().getDungeon().getRooms());
        }

        @Override
        public void makeChoice(Game game, int choice) {
            DungeonRoomSlot chosenSlot = game.getCurrentPlayer().getDungeon().getRoomSlots()[choice];
            chosenSlot.setRoomTrueDamage(chosenSlot.getRoomTrueDamage()+3);
        }

        @Override
        public Boolean isValidChoice(int choice, Game game) {
            RoomCard chosenRoom = game.getCurrentPlayer().getDungeon().getRoom(choice);
            return chosenRoom != null && chosenRoom.isTrapType();
        }
    };


    Function<Game, String> contextualMessage;
    String choiceMessage;

    public Boolean hasToCheckClock() {
        return this.getClockLimit() != null;
    }

    GameSubPhase(Function<Game, String> contextualMessage, String choiceMessage) {
        this.contextualMessage = contextualMessage;
        this.choiceMessage = choiceMessage;
    }

    GameSubPhase(Function<Game, String> contextualMessage) {
        this.contextualMessage = contextualMessage;
    }
}
