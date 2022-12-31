package org.springframework.samples.bossmonster.game;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.samples.bossmonster.game.card.Card;
import org.springframework.samples.bossmonster.game.card.TreasureType;
import org.springframework.samples.bossmonster.game.card.finalBoss.FinalBossCard;
import org.springframework.samples.bossmonster.game.card.hero.HeroCard;
import org.springframework.samples.bossmonster.game.card.room.RoomCard;
import org.springframework.samples.bossmonster.game.card.room.RoomPassiveTrigger;
import org.springframework.samples.bossmonster.game.card.room.RoomType;
import org.springframework.samples.bossmonster.game.card.spell.SpellCard;
import org.springframework.samples.bossmonster.game.gameState.GamePhase;
import org.springframework.samples.bossmonster.game.gameState.GameState;
import org.springframework.samples.bossmonster.game.gameState.GameSubPhase;
import org.springframework.samples.bossmonster.game.player.Player;
import org.springframework.samples.bossmonster.model.BaseEntity;
import org.springframework.samples.bossmonster.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Entity
@Getter
@Setter
@Slf4j
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

    private Integer roomToBuildFromHand;

    //@OneToOne
    //private GameResult result;

    public static final Integer NORMAL_HERO_SOUL_VALUE = 1;
    public static final Integer EPIC_HERO_SOUL_VALUE = 2;
    public static final Integer SOULS_REQUIRED_TO_WIN = 10;

    public Player getPlayerFromUser(User user) {
        return getPlayers().stream().filter(player->player.getUser().equals(user)).findAny().orElse(null);
    }

    public Player getCurrentPlayer() {
        log.debug("Players in game: " + getPlayers());
        log.debug("Fetching current player: " + getState().getCurrentPlayer());
        return getPlayers().get(getState().getCurrentPlayer());
    }

    public List<HeroCard> getSpecifiedCity(TreasureType type) {
        return getCity().stream().filter(heroCard -> heroCard.getTreasure() == type).collect(Collectors.toList());
    }

    ////////////////////////////   AUXILIAR FUNCTIONS   ////////////////////////////

    ////////// PLAYER HAND RELATED //////////

    public void discardCard(Player player, int cardPosition) {
        Card discardedCard = player.removeHandCard(cardPosition);
        discardPile.add(discardedCard);
    }

    public void getNewRoomCard(Player player) {
        RoomCard newCard = roomPile.remove(0);
        player.addHandCard(newCard);
    }

    public void getNewSpellCard(Player player) {
        SpellCard newCard = spellPile.remove(0);
        player.addHandCard(newCard);
    }

    public void getCardFromDiscardPile(Player player, int position) {
        Card newCard = discardPile.remove(position);
        player.addHandCard(newCard);
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
        log.debug("Luring heroes to best dungeons...");
        Iterator<HeroCard> iterator = getCity().iterator();
        while (iterator.hasNext()) {

            HeroCard currentHero = iterator.next();
            List<Player> playersWithBestDungeon = new ArrayList<>();
            Integer bestValue;
            TreasureType targetTreasure = currentHero.getTreasure();
            log.debug("Luring hero: " + currentHero.getName());
            if (targetTreasure != TreasureType.FOOL) {
                bestValue = getPlayers().stream().max(Comparator.comparing(
                    x -> x.getDungeon().getTreasureAmount(targetTreasure)
                )).get().getDungeon().getTreasureAmount(targetTreasure);
                playersWithBestDungeon = getPlayers().stream().filter(x -> x.getDungeon().getTreasureAmount(targetTreasure) == bestValue).collect(Collectors.toList());
            }
            else {
                bestValue = getPlayers().stream().min(Comparator.comparing(x -> x.getSouls())).get().getSouls();
                playersWithBestDungeon = getPlayers().stream().filter(x -> x.getSouls() == bestValue).collect(Collectors.toList());
            }
            if (playersWithBestDungeon.size() == 1) {
                log.debug(String.format("Hero enters %s's Dungeon",playersWithBestDungeon.get(0).getUser().getNickname()));
                playersWithBestDungeon.get(0).getDungeon().addNewHeroToDungeon(currentHero);
                iterator.remove();
             } else {
                log.debug("Tie, can't lure hero");
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
        log.debug("Placing " + room.getName() + " in " + player.getUser().getNickname() + "'s Dungeon");
        player.getDungeon().replaceDungeonRoom(room, 0);
        player.getHand().remove(room);
    }

    public Boolean checkPlaceableRoomInDungeonPosition(Player player, Integer position, RoomCard room) {
        Boolean result;
        RoomCard oldRoom = player.getDungeon().getRoom(position);
        if (oldRoom == null) {
            if(position == player.getDungeon().getBuiltRooms()) result = true;
            else result = false;
        }
        else {
            RoomType oldRoomType = player.getDungeon().getRoom(position).getRoomType();
            RoomType newRoomType = room.getRoomType();
            switch (newRoomType) {
                case ADVANCED_MONSTER: { result = (oldRoomType == RoomType.MONSTER || oldRoomType == RoomType.ADVANCED_MONSTER); break; }
                case ADVANCED_TRAP: { result = (oldRoomType == RoomType.TRAP || oldRoomType == RoomType.ADVANCED_TRAP); break; }
                default: result = true;
            }
        }
        return result;
    }

    public Boolean placeDungeonRoom(Player player, Integer position, RoomCard room) {
        Boolean placed;
        if (checkPlaceableRoomInDungeonPosition(player, position, room)) {
            player.getDungeon().replaceDungeonRoom(room, position);
            player.getHand().remove(room);
            placed = true;
        }
        else placed = false;
        return placed;
    }

    public void destroyDungeonRoom(Player player, Integer position) {
        player.getDungeon().replaceDungeonRoom(null, position);
    }

    public void processAdventurePhase(Player player) {
        player.heroAdvanceRoomDungeon();
    }

    public void revealAllDungeonRooms() {
        for (Player p: players) p.getDungeon().revealRooms();
    }

    public Boolean checkPlayerRoomsEffectTrigger(Player player, RoomPassiveTrigger trigger, Integer slot) {
        return player.getDungeon().checkRoomCardEffectIsTriggered(trigger, slot);
    }

    ////////// END GAME //////////

    public Boolean checkGameEnded() {
        Boolean anyPlayersCollectedAllSouls = players.stream().filter(x -> x.getSouls() >= SOULS_REQUIRED_TO_WIN).collect(Collectors.toList()).size() >= 1;
        Boolean tooManyPlayersHaveNoHealth = players.stream().filter(x -> x.getHealth() > 0).collect(Collectors.toList()).size() <= 1;
        return (anyPlayersCollectedAllSouls || tooManyPlayersHaveNoHealth);
    }

    public Player getWinningPlayer() {
        List<Player> winningCandidates;
        // Collecting 10 souls takes priority over running out of lives
        winningCandidates = players.stream().filter(x -> x.getSouls() >= SOULS_REQUIRED_TO_WIN).collect(Collectors.toList());
        // If only one player has health that player wins
        if (winningCandidates.size() == 0) winningCandidates = players.stream().filter(x -> x.getHealth() > 0).collect(Collectors.toList());
        // If no player is alive see which players died in the last turn
        if (winningCandidates.size() == 0) winningCandidates = players.stream().filter(x -> x.getEliminatedRound() == state.getCurrentRound()).collect(Collectors.toList());
        return winningCandidates.stream().min(Comparator.comparing(x -> x.getDungeon().getBossCard().getXp())).get();
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
            case DISCARD_2_STARTING_CARDS:
                result = getCurrentPlayerHand();
                break;
            case PLACE_FIRST_ROOM:
            case BUILD_NEW_ROOM:
                if(!state.isBuildingRoom()) {
                    result = getCurrentPlayerHand();
                } else {
                    result = Arrays.stream(getCurrentPlayer().getDungeon().getRoomSlots())
                        .map(slot->slot.getRoom())
                        .collect(Collectors.toList());
                }
                break;
            default:
                result = List.of();
                break;
        }
        return result;
    }

    public void makeChoice(Integer index) {
        if (index < 0) {
            log.info("Chose to pass");
            if(!getIsChoiceOptional()) return;
            incrementCounter();
            if (getState().getSubPhase() == GameSubPhase.BUILD_NEW_ROOM)
                incrementCounter();
            return;
        }
        if(getUnplayableCards().contains(index)) return;
        switch (getState().getSubPhase()) {
            case USE_SPELLCARD:
            case DISCARD_2_STARTING_CARDS:
                discardCard(getCurrentPlayer(), index);
                break;
            case PLACE_FIRST_ROOM:
                log.debug("Placing first room...");
                placeFirstRoom(getCurrentPlayer(), (RoomCard) getCurrentPlayerHand().get(index));
                break;
            case BUILD_NEW_ROOM:
                if (!state.isBuildingRoom()) {
                    setRoomToBuildFromHand(index);
                } else {
                    placeDungeonRoom(getCurrentPlayer(),
                        index,
                        (RoomCard) getCurrentPlayerHand().get(getRoomToBuildFromHand()));
                    setRoomToBuildFromHand(null);
                }
                break;
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
        List<Card> hand = getCurrentPlayerHand();
        switch (getState().getSubPhase()) {
            case USE_SPELLCARD:
                result = IntStream.range(0, hand.size()).filter(i->!(hand.get(i) instanceof SpellCard)).boxed().collect(Collectors.toList());
                break;
            case PLACE_FIRST_ROOM:
                result = IntStream.range(0, hand.size()).filter(i->!(hand.get(i) instanceof RoomCard)).boxed().collect(Collectors.toList());
                break;
            case BUILD_NEW_ROOM:
                if(!state.isBuildingRoom()) {
                    result = IntStream.range(0, hand.size()).filter(i->!(hand.get(i) instanceof RoomCard)).boxed().collect(Collectors.toList());
                } else {
                    Card selectedCard = hand.get(roomToBuildFromHand);
                    result = IntStream.range(0, getCurrentPlayer().getDungeon().getRoomSlots().length).filter(i->selectedCard instanceof RoomCard &&!(checkPlaceableRoomInDungeonPosition(getCurrentPlayer(),i,(RoomCard) selectedCard))).boxed().collect(Collectors.toList());
                }
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
