package org.springframework.samples.bossmonster.game;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.samples.bossmonster.converters.StringIntStackConverter;
import org.springframework.samples.bossmonster.game.card.Card;
import org.springframework.samples.bossmonster.game.card.TreasureType;
import org.springframework.samples.bossmonster.game.card.finalBoss.FinalBossCard;
import org.springframework.samples.bossmonster.game.card.hero.HeroCard;
import org.springframework.samples.bossmonster.game.card.room.RoomCard;
import org.springframework.samples.bossmonster.game.card.room.RoomPassiveTrigger;
import org.springframework.samples.bossmonster.game.card.room.RoomType;
import org.springframework.samples.bossmonster.game.card.spell.SpellCard;
import org.springframework.samples.bossmonster.game.chat.Chat;
import org.springframework.samples.bossmonster.game.dungeon.Dungeon;
import org.springframework.samples.bossmonster.game.gameState.GamePhase;
import org.springframework.samples.bossmonster.game.gameState.GameState;
import org.springframework.samples.bossmonster.game.player.Player;
import org.springframework.samples.bossmonster.gameResult.GameResult;
import org.springframework.samples.bossmonster.model.BaseEntity;
import org.springframework.samples.bossmonster.user.User;

import javax.persistence.*;
import java.time.Duration;
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

    @OneToOne(cascade = CascadeType.ALL)
    private Chat chat;

    private LocalDateTime startedTime;

    @Convert(converter = StringIntStackConverter.class)
    private Stack<Integer> previousChoices;

    @OneToOne(cascade = CascadeType.ALL)
    private GameResult result;

    @Version
    private Integer version;
    public static final Integer NORMAL_HERO_SOUL_VALUE = 1;
    public static final Integer EPIC_HERO_SOUL_VALUE = 2;
    public static final Integer SOULS_REQUIRED_TO_WIN = 10;

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
        Card discardedCard = player.removeHandCard(cardPosition);
        discardPile.add(discardedCard);
    }

    public void getNewRoomCard(Player player) {
        if (roomPile.isEmpty()) refillRoomPile();
        if (!roomPile.isEmpty()) {
            RoomCard newCard = roomPile.remove(0);
            player.addHandCard(newCard);
        }
    }

    public void getNewSpellCard(Player player) {
        if (spellPile.isEmpty()) refillSpellPile();
        if (!spellPile.isEmpty()) {
            SpellCard newCard = spellPile.remove(0);
            player.addHandCard(newCard);
        }
    }

    public void getCardFromDiscardPile(Player player, int position) {
        Card newCard = discardPile.remove(position);
        player.addHandCard(newCard);
    }

    public void discardAllCards(Player player) {
        Integer handSize = player.getHand().size();
        for (int i = 0; i < handSize; i ++) discardCard(player, 0);
    }


    ////////// REFILL PILE RELATED //////////

    public void refillRoomPile() {
        Iterator<Card> iterator = getDiscardPile().iterator();
        while (iterator.hasNext()) {
            Card card = iterator.next();
            if (card.getClass() == RoomCard.class) {
                roomPile.add((RoomCard) card);
                iterator.remove();
            }
        }
        Collections.shuffle(roomPile);
    }

    public void refillSpellPile() {
        Iterator<Card> iterator = getDiscardPile().iterator();
        while (iterator.hasNext()) {
            Card card = iterator.next();
            if (card.getClass() == SpellCard.class) {
                spellPile.add((SpellCard) card);
                iterator.remove();
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
                log.debug(String.format("Hero enters %s's Dungeon",playersWithBestDungeon.get(0)));
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
        tryTriggerRoomCardEffect(RoomPassiveTrigger.BUILD_THIS_ROOM,player,0);
        player.getHand().remove(room);
    }

    public Boolean checkPlaceableRoomInDungeonPosition(Player player, Integer position, RoomCard room) {
        Boolean result;
        RoomCard oldRoom = player.getDungeon().getRoom(position);
        if (oldRoom == null) {
            if (position.equals(player.getDungeon().getBuiltRooms())) result = !room.isAdvanced();
            else result = false;
        }
        else {
            RoomType newRoomType = room.getRoomType();
            switch (newRoomType) {
                case ADVANCED_MONSTER: { result = oldRoom.isMonsterType() && !oldRoom.isNeanderthalCave(); break; }
                case ADVANCED_TRAP: { result = oldRoom.isTrapType(); break; }
                default: result = true;
            }
        }
        return result;
    }

    public void checkForPlayerBossLeveledUp(Player player) {
        if (player.getDungeon().checkBossLeveledUp()) {
            player.getDungeon().setBossCardLeveledUp(true);
            player.getDungeon().getBossCard().getEffect().apply(player, null, this);
            if(getState().getPhase().equals(GamePhase.EFFECT)) getState().setEffectIsBeingTriggered(true);
        }
    }

    public Boolean placeDungeonRoom(Player player, Integer position, RoomCard room, Boolean force) {
        Boolean placed;
        if (checkPlaceableRoomInDungeonPosition(player, position, room) || force) {
            tryTriggerRoomCardEffect(RoomPassiveTrigger.DESTROY_THIS_ROOM,player,position);

            if (player.getDungeon().getRoomSlots()[position].getRoom() != null) destroyDungeonRoom(player, position);
            player.getDungeon().replaceDungeonRoom(room, position);
            player.getHand().remove(room);

            checkForPlayerBossLeveledUp(player);
            tryTriggerRoomCardEffect(RoomPassiveTrigger.BUILD_THIS_ROOM,player,position);
            for(int pos = 0; pos < player.getDungeon().getBuiltRooms(); pos++) {
                if (pos != position) tryTriggerRoomCardEffect(RoomPassiveTrigger.DESTROY_ANOTHER_ROOM,player,pos);
                if (room.isMonsterType()) tryTriggerRoomCardEffect(RoomPassiveTrigger.BUILD_MONSTER_ROOM,player,pos);
            }
            placed = true;
        }
        else placed = false;
        return placed;
    }

    public void destroyDungeonRoom(Player player, Integer position) {
        RoomCard deletedCard = player.getDungeon().getRoom(position);
        player.getDungeon().replaceDungeonRoom(null, position);
        discardPile.add(deletedCard);
    }

    public void processAdventurePhase(Player player) {
        log.debug(String.format("Advancing heroes in %s's dungeon",player));
        player.getDungeon().heroAdvanceRoomDungeon();
    }

    public void revealAllDungeonRooms() {
        for (Player p: players) p.getDungeon().revealRooms();
    }

    public void tryTriggerRoomCardEffect(RoomPassiveTrigger trigger, Player player,  Integer slot) {
        if (!getState().getEffectIsBeingTriggered() &&
            checkPlayerRoomsEffectTrigger(player, trigger, slot)) triggerRoomCardEffect(player, slot);
    }

    public Boolean checkPlayerRoomsEffectTrigger(Player player, RoomPassiveTrigger trigger, Integer slot) {
        return  player.getDungeon().checkRoomCardEffectIsTriggered(trigger, slot) && getState().getPhase() != GamePhase.EFFECT;
    }

    public void triggerRoomCardEffect(Player player, Integer position) {
        RoomCard room = player.getDungeon().getRoomSlots()[position].getRoom();
        room.getEffect().apply(player, position, this);
        if(getState().getPhase().equals(GamePhase.EFFECT)) getState().setEffectIsBeingTriggered(true);
    }

    public void triggerSpellCardEffect(SpellCard spell) {
        Dungeon currentPlayerDungeon = getCurrentPlayer().getDungeon();
        if(spell.getEffect() == null) return;
        for(int pos = 0; pos < currentPlayerDungeon.getBuiltRooms(); pos++) {
            tryTriggerRoomCardEffect(RoomPassiveTrigger.USE_SPELL_CARD,getCurrentPlayer(),pos);
        }
        spell.getEffect().apply(getCurrentPlayer(),null,this);
        if(getState().getPhase().equals(GamePhase.EFFECT)) getState().setEffectIsBeingTriggered(true);
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

    public GameResult generateGameResult() {
        GameResult result = new GameResult();
        result.setMinutes(Math.floor(Duration.between(getStartedTime(), LocalDateTime.now()).getSeconds() / 60));
        result.setDate(getStartedTime().toLocalDate());
        result.setRounds(getState().getCurrentRound());
        result.setWinner(getWinningPlayer().getUser());
        result.setParticipants(getPlayers().stream().map(x -> x.getUser()).collect(Collectors.toList()));
        String health = "";
        String souls = "";
        for (int i = 0; i < getPlayers().size(); i ++) {
            if (i == 0) {
                health = String.valueOf(getPlayers().get(i).getHealth());
                souls = String.valueOf(getPlayers().get(i).getSouls());
            }
            else {
                health = String.format("%s/%s", health, getPlayers().get(i).getHealth());
                souls = String.format("%s/%s", souls, getPlayers().get(i).getSouls());
            }
        result.setSouls(souls);
        result.setHealths(health);
        }
        setResult(result);
        return result;
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

    public void skipBuildPhase() {
        getState().changePhase(GamePhase.LURE);
    }

    public void updateEliminatedPlayersRound() {
        for (Player p: getPlayers()) {
            if (p.isDead() && p.getEliminatedRound() == -1) p.setEliminatedRound(getState().getCurrentRound());
        }
    }

    ////////// PROCESS STATE //////////

    public List<Card> getChoice() {
        List<Card> result = getState().getSubPhase().getChoice(this);
        if(result == null) return result;
        Boolean validChoicesExist = IntStream.range(0, result.size())
            .anyMatch(index->getState().getSubPhase().isValidChoice(index,this));
        if(!validChoicesExist && !getState().getSubPhase().canDecrementCounter()) {
            log.debug("Player can't choose, triggering failsafe. Choice: "+result);
            incrementCounter();
        }
        return result;
    }

    public void makeChoice(Integer index) {
        getState().setEffectIsBeingTriggered(false);
        if (index < 0) {
            log.info("Chose to pass");
            if(!getState().getSubPhase().isOptional()) return;

            if (getState().getSubPhase().canDecrementCounter()) {
                if(!getPreviousChoices().empty()) {
                    decrementCounter();
                    previousChoices.pop();
                }
                else {
                    getState().setCounter(getState().getSubPhase().getActionLimit());
                    getState().checkStateStatus();
                }
            } else incrementCounter();
            return;
        }
        if(!getState().getSubPhase().isValidChoice(index,this)) return;

        getState().getSubPhase().makeChoice(this,index);
        if (getState().getEffectIsBeingTriggered()) getState().setCounterBeforeEffect(getState().getCounterBeforeEffect() + 1);
        else incrementCounter();
    }

    public Boolean getPlayerHasToChoose(Player player) {
        List<Card> choice = getChoice();
        return player == getCurrentPlayer() && choice != null;
    }
}
