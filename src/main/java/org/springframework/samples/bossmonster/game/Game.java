package org.springframework.samples.bossmonster.game;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.bossmonster.game.card.Card;
import org.springframework.samples.bossmonster.game.card.TreasureType;
import org.springframework.samples.bossmonster.game.card.finalBoss.FinalBossCard;
import org.springframework.samples.bossmonster.game.card.hero.HeroCard;
import org.springframework.samples.bossmonster.game.card.room.RoomCard;
import org.springframework.samples.bossmonster.game.card.room.RoomPassiveTrigger;
import org.springframework.samples.bossmonster.game.card.room.RoomType;
import org.springframework.samples.bossmonster.game.card.spell.SpellCard;
import org.springframework.samples.bossmonster.game.dungeon.Dungeon;
import org.springframework.samples.bossmonster.game.dungeon.DungeonRoomSlot;
import org.springframework.samples.bossmonster.game.gameState.GamePhase;
import org.springframework.samples.bossmonster.game.gameState.GameState;
import org.springframework.samples.bossmonster.game.gameState.GameSubPhase;
import org.springframework.samples.bossmonster.game.player.Player;
import org.springframework.samples.bossmonster.model.BaseEntity;
import org.springframework.samples.bossmonster.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Entity
@Getter
@Setter
@Table(name = "games")
public class Game extends BaseEntity {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "game")
    private List<Player> players = new java.util.ArrayList<>();
    private boolean active;

    @OneToMany
    private List<Card> discardPile;

    @OneToMany
    private List<HeroCard> heroPile;

    @OneToMany
    private List<SpellCard> spellPile;

    @OneToMany
    private List<RoomCard> roomPile;

    @OneToMany
    private List<FinalBossCard> finalBossPile;

    @OneToMany
    private List<HeroCard> city;

    @OneToOne(cascade = CascadeType.ALL)
    private GameState state;

    private LocalDateTime startedTime;

    //@OneToOne
    //private GameResult result;

    public Player getPlayerFromUser(User user) {
        return getPlayers().stream().filter(player->player.getUser().equals(user)).findAny().orElse(null);
    }

    public Player getCurrentPlayer() {
        return getPlayers().get(getState().getCurrentPlayer());
    }

    public List<HeroCard> getSpecifiedCity(TreasureType type) {
        return getCity().stream().filter(heroCard -> heroCard.getTreasure() == type).collect(Collectors.toList());
    }

    ////////////////////////////   AUXILIAR FUNCTIONS   ////////////////////////////

    ////////// PLAYER HAND RELATED //////////

    public void discardCard(Player player, int cardPosition) {
        List<Card> playerHand = player.getHand();
        Card discardedCard = playerHand.remove(cardPosition);
        discardPile.add(discardedCard);
        player.setHand(playerHand);
    }

    public void getNewRoomCard(Player player) {
        List<Card> playerHand = player.getHand();
        RoomCard newCard = roomPile.remove(0);
        playerHand.add(newCard);
        player.setHand(playerHand);
    }

    public void getNewSpellCard(Player player) {
        List<Card> playerHand = player.getHand();
        SpellCard newCard = spellPile.remove(0);
        playerHand.add(newCard);
        player.setHand(playerHand);
    }

    public void getCardFromDiscardPile(Player player, int position) {
        List<Card> playerHand = player.getHand();
        Card newCard = discardPile.remove(position);
        playerHand.add(newCard);
        player.setHand(playerHand);
    }

    ////////// REFILL PILE RELATED //////////

    public void refillRoomPile() {
        for(Card card: discardPile) {
            if (card.getClass() == RoomCard.class) {
                roomPile.add((RoomCard) card);
                discardPile.remove(card);
            }
        }
        Collections.shuffle(roomPile);
    }

    public void refillSpellPile() {
        for(Card card: discardPile) {
            if (card.getClass() == SpellCard.class) {
                spellPile.add((SpellCard) card);
                discardPile.remove(card);
            }
        }
        Collections.shuffle(spellPile);
    }

    ////////// HERO RELATED //////////

    public void lureHeroToBestDungeon() {

        for (int i = 0; i < city.size(); i ++) {

            List<Player> playersWithBestDungeon = new ArrayList<>();
            Integer bestValue;
            TreasureType targetTreasure = getCity().get(i).getTreasure();

            if (targetTreasure != TreasureType.FOOL) {
                bestValue = getPlayers().stream().max(Comparator.comparing(x -> x.getDungeon().getTreasureAmount(targetTreasure))).get().getDungeon().getTreasureAmount(targetTreasure);
                playersWithBestDungeon = getPlayers().stream().filter(x -> x.getDungeon().getTreasureAmount(targetTreasure) == bestValue).collect(Collectors.toList());
            }
            else {
                bestValue = getPlayers().stream().min(Comparator.comparing(x -> x.getSouls())).get().getSouls();
                playersWithBestDungeon = getPlayers().stream().filter(x -> x.getSouls() == bestValue).collect(Collectors.toList());
            }

            if (playersWithBestDungeon.size() == 1) {
                playersWithBestDungeon.get(0).getDungeon().addNewHeroToDungeon(city.get(i));
                city.remove(i);
             }

        }

    }

    public void placeHeroInCity() {
        List<HeroCard> nonEpicHeroes = heroPile.stream().filter(x -> !x.getIsEpic()).collect(Collectors.toList());
        for (var i = 0; i < players.size(); i ++) {
            if (nonEpicHeroes.size() > 0) {
                HeroCard newHero = nonEpicHeroes.get(0);
                city.add(newHero);
                nonEpicHeroes.remove(0);
                heroPile.remove(newHero);
            }
            else if (heroPile.size() > 0) {
                HeroCard newHero = heroPile.get(0);
                city.add(newHero);
                heroPile.remove(0);
            }
        }
    }

    ////////// DUNGEON RELATED //////////

    public void placeFirstRoom(Player player, RoomCard room) {
        player.getDungeon().replaceDungeonRoom(room, 0);
    }

    public Boolean checkPlaceableRoomInDungeonPosition(Player player, Integer position, RoomCard room) {
        Boolean result;
        RoomType oldRoomType = player.getDungeon().getRoom(position).getRoomType();
        RoomType newRoomType = room.getRoomType();
        switch (newRoomType) {
            case ADVANCED_MONSTER: { result = oldRoomType == RoomType.MONSTER || oldRoomType == RoomType.ADVANCED_MONSTER; break; }
            case ADVANCED_TRAP: { result = oldRoomType == RoomType.TRAP || oldRoomType == RoomType.ADVANCED_TRAP; break; }
            default: result = true;
        }
        return result;
    }

    public Boolean placeDungeonRoom(Player player, Integer position, RoomCard room) {
        Boolean placed;
        if (checkPlaceableRoomInDungeonPosition(player, position, room)) {
            player.getDungeon().replaceDungeonRoom(room, position);
            placed = true;
        }
        else placed = false;
        return placed;
    }

    public void destroyDungeonRoom(Player player, Integer position) {
        player.getDungeon().replaceDungeonRoom(null, position);
    }

    public void heroAdvanceRoomDungeon(Player player) {
        Dungeon playerDungeon = player.getDungeon();
        for(int i = 4; i >= 0; i --) {
            DungeonRoomSlot roomSlot = playerDungeon.getRoomSlots()[i];
            Integer dealtDamage = roomSlot.getRoomTrueDamage();
            for(HeroCard hero: roomSlot.getHeroesInRoom()) {
                // TODO Deal damage to hero

                Integer heroValue;
                if (hero.getIsEpic()) heroValue = 2;
                else heroValue = 1;

                if (false) player.setSouls(player.getSouls() + heroValue); // TODO Check if hero dies
                else {
                    if (i == 0) { player.setHealth(player.getHealth() - heroValue); }
                    else {
                        // TODO Hero goes to next room in dungeon
                    }
                }
                // TODO Hero is deleted from room
            }
        }
    }

    public void heroAutomaticallyMovesAfterDestroyingRoom() {
        // TODO
    }

    public void revealAllDungeonRooms() {
        for (Player p: players) {
            p.getDungeon().revealRooms();
        }
    }

    public void processRoomEffectTrigger(RoomPassiveTrigger trigger) {
        for(int i = 0; i < 5; i ++) {
            // Ups... a refactorizar toca
            
        }
    }

    ////////// MISC //////////

    public void sortPlayersByFinalBossEx() {
        setPlayers(players.stream().sorted(Comparator.comparing(x -> x.getDungeon().getBossCard().getXp(), Comparator.reverseOrder())).collect(Collectors.toList()));
    }

    public List<Card> getCurrentPlayerHand() {
        return players.get(getState().getCurrentPlayer()).getHand();
    }

    public void incrementCounter() {
        state.setCounter(state.getCounter() + 1);
        state.checkStateStatus();
    }

    public void decrementCounter() {
        state.setCounter(state.getCounter() - 1);
        state.checkStateStatus();
    }

    public void forceStateForTesting(GamePhase phase, GameSubPhase subPhase, Integer currentPlayer, Integer counter, Integer actionLimit, Integer seconds, Boolean checkClock) {
        getState().setPhase(phase);
        getState().setSubPhase(subPhase);
        getState().setCurrentPlayer(currentPlayer);
        getState().setCounter(counter);
        getState().setActionLimit(actionLimit);
        getState().setClock(LocalDateTime.now().plusSeconds(seconds));
        getState().setCheckClock(checkClock);
    }

    ////////// PROCESS STATE //////////

    public List<Card> getChoice() {
        List<Card> result;
        switch (getState().getSubPhase()) {
            case USE_SPELLCARD:
                result = getCurrentPlayer().getHand().stream()
                    .filter(card -> card instanceof SpellCard)
                    .collect(Collectors.toList());
                break;
            case DISCARD_2_STARTING_CARDS:
                result = getCurrentPlayer().getHand();
                break;
            case PLACE_FIRST_ROOM:
            case BUILD_NEW_ROOM:
                result = getCurrentPlayer().getHand().stream()
                    .filter(card -> card instanceof RoomCard)
                    .collect(Collectors.toList());
                break;
            default:
                result = List.of();
                break;
        }
        return result;
    }

    public void makeChoice(Integer index) {
        if(index != null) {
            switch (getState().getSubPhase()) {
                case USE_SPELLCARD:
                case DISCARD_2_STARTING_CARDS:
                    discardCard(getCurrentPlayer(), index);
                    break;
                case PLACE_FIRST_ROOM:
                    discardCard(getCurrentPlayer(), index);
                    break;
                case BUILD_NEW_ROOM:
                    discardCard(getCurrentPlayer(), index);
                    break;
            }
        }
        incrementCounter();
    }

    public boolean getIsChoiceOptional() {
        boolean result;
        switch (getState().getSubPhase()) {
            case USE_SPELLCARD:
            case BUILD_NEW_ROOM:
                result = true;
                break;
            default:
                result = false;
                break;
        }
        return result;
    }

    public List<Integer> getUnplayableCards() {
        List<Integer> result;
        List<Card> hand = getCurrentPlayer().getHand();
        switch (getState().getSubPhase()) {
            case USE_SPELLCARD:
                result = IntStream.range(0, hand.size()-1)
                    .filter(i->!(hand.get(i) instanceof SpellCard))
                    .boxed().collect(Collectors.toList());
            case PLACE_FIRST_ROOM:
            case BUILD_NEW_ROOM:
                result = IntStream.range(0, hand.size()-1)
                    .filter(i->!(hand.get(i) instanceof RoomCard))
                    .boxed().collect(Collectors.toList());
                break;
            default:
                result = List.of();
                break;
        }
        return result;
    }

    public Boolean getPlayerHasToChoose(Player player) {
        return player == getCurrentPlayer() && !getChoice().isEmpty();
    }
}
