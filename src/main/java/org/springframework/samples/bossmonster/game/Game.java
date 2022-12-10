package org.springframework.samples.bossmonster.game;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.bossmonster.game.card.Card;
import org.springframework.samples.bossmonster.game.card.TreasureType;
import org.springframework.samples.bossmonster.game.card.finalBoss.FinalBossCard;
import org.springframework.samples.bossmonster.game.card.hero.HeroCard;
import org.springframework.samples.bossmonster.game.card.room.RoomCard;
import org.springframework.samples.bossmonster.game.card.spell.SpellCard;
import org.springframework.samples.bossmonster.game.dungeon.Dungeon;
import org.springframework.samples.bossmonster.game.dungeon.DungeonRoomSlot;
import org.springframework.samples.bossmonster.game.gameState.GameState;
import org.springframework.samples.bossmonster.game.player.Player;
import org.springframework.samples.bossmonster.model.BaseEntity;
import org.springframework.samples.bossmonster.user.User;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
                // I gave up
            }
        }
    }

    public void refillSpellPile() {

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

    public void checkPlaceableRoomInDungeonPosition(Player player, Integer position, RoomCard room) {

    }

    public void placeDungeonRoom(Player player, Integer position, RoomCard room) {
        Dungeon playerDungeon = player.getDungeon();
        DungeonRoomSlot[] slots = playerDungeon.getRoomSlots();
        slots[position].setRoom(room);
        player.setDungeon(playerDungeon);
    }

    public void destroyDungeonRoom(Player player, Integer position) {
        // Almost the same that the last one. I still made it to make the code easier to understand
        Dungeon playerDungeon = player.getDungeon();
        DungeonRoomSlot[] slots = playerDungeon.getRoomSlots();
        slots[position].setRoom(null);
        player.setDungeon(playerDungeon);
    }

    public void heroAdvanceRoomDungeon() {

    }


}
