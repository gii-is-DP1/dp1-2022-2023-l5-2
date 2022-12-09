package org.springframework.samples.bossmonster.game;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.bossmonster.game.card.Card;
import org.springframework.samples.bossmonster.game.card.TreasureType;
import org.springframework.samples.bossmonster.game.card.finalBoss.FinalBossCard;
import org.springframework.samples.bossmonster.game.card.hero.HeroCard;
import org.springframework.samples.bossmonster.game.card.room.RoomCard;
import org.springframework.samples.bossmonster.game.card.spell.SpellCard;
import org.springframework.samples.bossmonster.game.gameState.GameState;
import org.springframework.samples.bossmonster.game.player.Player;
import org.springframework.samples.bossmonster.model.BaseEntity;
import org.springframework.samples.bossmonster.user.User;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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

    public void discardCard(Player player, int cardPosition) {
        //cardService.giveCard(player.getHand(), game.get().getDiscardPile(), cardPosition);
    }

    public void checkPlaceableRoomInDungeonPosition(Player player, Integer position, RoomCard room) {

    }

    public void placeDungeonRoom(Player player, Integer position, RoomCard room) {

    }

    public void destroyDungeonRoom(Player player, Integer position, RoomCard room) {

    }

    public void getNewRoomCard(Player player) {
        //List<Card> cardList = new ArrayList<>(game.getRoomPile());
        //cardService.giveCard(cardList, player.getHand(), 0);

    }

    public void getNewSpellCard(Player player) {

    }

    public void getCardFromDiscardPile(Player player, Card card) {

    }

    public void heroAdvanceRoomDungeon() {

    }

    public void lureHeroToBestDungeon() {

        for (int i = 0; i < getCity().size(); i ++) {

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
                // TODO El heroe entra en la mazmorra

             }
        }

    }
}
