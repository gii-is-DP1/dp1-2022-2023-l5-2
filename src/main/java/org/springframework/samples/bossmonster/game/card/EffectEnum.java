package org.springframework.samples.bossmonster.game.card;

import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import org.springframework.samples.bossmonster.game.Game;
import org.springframework.samples.bossmonster.game.card.hero.HeroCard;
import org.springframework.samples.bossmonster.game.card.hero.HeroCardStateInDungeon;
import org.springframework.samples.bossmonster.game.card.room.RoomCard;
import org.springframework.samples.bossmonster.game.card.room.RoomType;
import org.springframework.samples.bossmonster.game.dungeon.Dungeon;
import org.springframework.samples.bossmonster.game.dungeon.DungeonRoomSlot;
import org.springframework.samples.bossmonster.game.gameState.GamePhase;
import org.springframework.samples.bossmonster.game.gameState.GameState;
import org.springframework.samples.bossmonster.game.gameState.GameSubPhase;
import org.springframework.samples.bossmonster.game.player.Player;

//Enumerado actua como dummy para la base de datos.
public enum EffectEnum implements EffectInterface {

    ENUMERADO {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            // TODO
        }
    },

    // Botomless Pit (Room)
    KILL_ONE_HERO_IN_THIS_ROOM {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            player.getDungeon().damageRandomHeroInDungeonPosition(dungeonPosition, 99);
        }
    },

    // The Crushinator (Room)
    ADD_2_DAMAGE_TO_EVERY_ROOM {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            Dungeon dungeon = player.getDungeon();
            for (DungeonRoomSlot drs: dungeon.getRoomSlots()) {
                if (drs.getRoom() != null) drs.setRoomTrueDamage(drs.getRoomTrueDamage() + 2);
            }
        }
    },

    // Vampire Burdello (Room)
    // Belladonna (Boss)
    CONVERT_A_WOUND_INTO_A_SOUL {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            if (player.getHealth() < 5) {
                player.setHealth(player.getHealth() + 1);
                player.setSouls(player.getSouls() + 1);
            }
        }
    },

    // Boulder Ramp (Room)
    DEAL_5_DAMAGE_TO_A_HERO_IN_THIS_ROOM {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            player.getDungeon().damageRandomHeroInDungeonPosition(dungeonPosition, 5);
        }
    },

    // Construction Zone (Room)
    BUILD_ANOTHER_ROOM {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            if (game.getState().getPhase() != GamePhase.START_GAME) game.getState().setActionLimit(game.getState().getActionLimit() + 2);
        }
    },

    // Dark Altar (Room)
    CHOOSE_CARD_FROM_DISCARD_PILE {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            game.getState().triggerSpecialCardEffectState(GameSubPhase.CHOOSE_A_CARD_FROM_DISCARD_PILE);
        }
    },

    // Monster's Ballroom (Room)
    // Dragon Hatchery (Room)
    // Neandeltal Cave (Room)
    NOTHING {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {}
    },

    // Open Grave (Room)
    CHOOSE_ROOM_CARD_FROM_DISCARD_PILE {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            game.getState().triggerSpecialCardEffectState(GameSubPhase.CHOOSE_A_ROOM_CARD_FROM_DISCARD_PILE);
        }
    },

    // Recycling Center (Room)
    DRAW_2_ROOM_CARDS {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            for (int i = 0; i < 2; i ++) game.getNewRoomCard(player);
        }
    },

    // Liger's Den (Room)
    // Brainsucker Hive (Room)
    DRAW_A_SPELL_CARD {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            game.getNewSpellCard(player);
        }
    },

    // Goblin Armory (Room)
    ADD_1_DAMAGE_TO_ADYACENT_MONSTER_ROOMS {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            for(int i = dungeonPosition - 1; i <= dungeonPosition + 1; i += 2) {
                if (i >= 0 && i <= 4) {
                    DungeonRoomSlot slot = player.getDungeon().getRoomSlots()[i];
                    if (slot.getRoom() != null && slot.getRoom().isMonsterType()) slot.setRoomTrueDamage(slot.getRoomTrueDamage() + 1);
                }
            }
        }
    },

    // Golem Factory (Room)
    // Beast Menagerie (Room)
    DRAW_A_ROOM_CARD {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            game.getNewRoomCard(player);
        }
    },

    // Jackpot Stash (Room)
    DOUBLE_DUNGEON_TREASURE_VALUE {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            player.getDungeon().setJackpotStashEffectActivated(true);
        }
    },

    // Dark Laboratory (Room)
    DRAW_2_SPELL_CARDS_AND_DISCARD_1_SPELL_CARD {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            for (int i = 0; i < 2; i ++) game.getNewSpellCard(player);
            game.getState().triggerSpecialCardEffectState(GameSubPhase.DISCARD_A_SPELL_CARD);
        }
    },

    // Monstrous Monument (Room)
    CHOOSE_MONSTER_ROOM_CARD_FROM_DISCARD_PILE {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            game.getState().triggerSpecialCardEffectState(GameSubPhase.CHOOSE_A_MONSTER_ROOM_CARD_FROM_DISCARD_PILE);
        }
    },

    // Dizzygas Hallway (Room)
    ADD_2_DAMAGE_TO_NEXT_ROOM_IF_IT_IS_A_TRAP_ROOM {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            if (dungeonPosition > 0) {
                DungeonRoomSlot drs = player.getDungeon().getRoomSlots()[dungeonPosition - 1];
                if (drs.getRoom() != null && (drs.getRoom().getRoomType() == RoomType.TRAP || drs.getRoom().getRoomType() == RoomType.ADVANCED_TRAP)) drs.setRoomTrueDamage(drs.getRoomTrueDamage() + 2);
            }
        }
    },

    // Minotaur's Maze (Room)
    PUSH_HERO_TO_PREVIOUS_ROOM_ONCE {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            if (player.getDungeon().getFirstRoomSlot() > dungeonPosition) {
                DungeonRoomSlot currentSlot = player.getDungeon().getRoomSlots()[dungeonPosition];
                DungeonRoomSlot previousSlot = player.getDungeon().getRoomSlots()[dungeonPosition + 1];
                Iterator<HeroCardStateInDungeon> iterator = currentSlot.getHeroesInRoom().iterator();
                while (iterator.hasNext()) {
                    HeroCardStateInDungeon hero = iterator.next();
                    if (!hero.getMinotaursMazeEffectTriggered()) {
                        hero.setMinotaursMazeEffectTriggered(true);
                        iterator.remove();
                        previousSlot.addHero(hero);
                    }
                }
            }
        }
    },

    // The Brothers Wise (Boss)
    CHOOSE_SPELL_CARD_FROM_SPELL_PILE {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            game.getState().triggerSpecialCardEffectState(GameSubPhase.CHOOSE_SPELL_FROM_SPELL_PILE);
        }
    },

    // Xyzax (Boss)
    CHOOSE_2_CARDS_FROM_DISCARD_PILE {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            game.getState().triggerSpecialCardEffectState(GameSubPhase.CHOOSE_2_CARDS_FROM_DISCARD_PILE);
        }
    },

    // Cerebellus (Boss)
    DRAW_3_SPELL_CARDS_AND_DISCARD_1_SPELL_CARD {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            for (int i = 0; i < 3; i ++) game.getNewSpellCard(player);
            game.getState().triggerSpecialCardEffectState(GameSubPhase.DISCARD_A_SPELL_CARD);
        }
    },

    // King Croak (Boss)
    BUILD_AN_ADVANCED_MONSTER_ROOM_CHOSEN_FROM_THE_ROOM_PILE_OR_DISCARD_PILE {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            game.getState().triggerSpecialCardEffectState(GameSubPhase.BUILD_ADVANCED_CARD_FROM_DISCARD_OR_ROOM_PILE);
        }
    },

    // Seducia (Boss)
    LURE_A_CHOSEN_HERO_FROM_CITY_OR_HERO_PILE_TO_DUNGEON {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            // TODO
        }
    },

    // Cleopatra (Boss)
    BUILD_AN_ADVANCED_TRAP_ROOM_CHOSEN_FROM_THE_ROOM_PILE_OR_DISCARD_PILE {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            // TODO
        }
    },

    // Giant Size (Spell)
    ADD_3_DAMAGE_TO_A_CHOSEN_MONSTER_ROOM {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            for (DungeonRoomSlot drs: player.getDungeon().getRoomSlots()) {
                if (drs.getRoom() != null && drs.getRoom().isMonsterType()) drs.setRoomTrueDamage(drs.getRoomTrueDamage() + 3);
            }
        }
    },

    // Soul Harvest (Spell)
    TRADE_A_SOUL_FOR_2_SPELL_CARDS {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            if (player.getSouls() > 0) {
                player.setSouls(player.getSouls() - 1);
                for (int i = 0; i < 2; i ++) game.getNewSpellCard(player);
            }
        }
    },

    // Princess In Peril (Spell)
    LURE_A_CHOSEN_HERO_FROM_CITY_TO_DUNGEON {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            Collections.shuffle(game.getCity(), new Random());
            HeroCard chosenHero = game.getCity().get(0);
            player.getDungeon().addNewHeroToDungeon(chosenHero);
            game.getCity().remove(chosenHero);
        }
    },

    // Motivation (Spell)
    BUILD_ANOTHER_ROOM_IF_ANOTHER_PLAYER_HAS_MORE_ROOMS {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            Integer playersWithMoreRooms = (int) game.getPlayers().stream().filter(x -> x.getDungeon().getBuiltRooms() > player.getDungeon().getBuiltRooms()).count();
            if (playersWithMoreRooms > 0) {
                game.getState().setSubPhase(GameSubPhase.BUILD_NEW_ROOM);
                game.getState().updateChangeConditionCounter(2);
            }
        }
    },

    // Exhaustion (Spell)
    DEAL_ROOM_AMOUNT_DAMAGE_TO_HERO {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            // TODO
        }
    },

    // Annihilator (Spell)
    ADD_3_DAMAGE_TO_A_CHOSEN_TRAP_ROOM {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            for (DungeonRoomSlot drs: player.getDungeon().getRoomSlots()) {
                if (drs.getRoom() != null && drs.getRoom().isTrapType()) drs.setRoomTrueDamage(drs.getRoomTrueDamage() + 3);
            }
        }
    },

    // Cave-In (Spell)
    DESTROY_A_DUNGEON_KILLING_EVERY_HERO_IN_IT {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            Integer firstRoom = player.getDungeon().getFirstRoomSlot();
            if (firstRoom != 0) {
                while(player.getDungeon().getRoomSlots()[firstRoom].getHeroesInRoom().size() != 0) {
                    player.getDungeon().damageRandomHeroInDungeonPosition(firstRoom, 99);
                }
                RoomCard room = player.getDungeon().getRoomSlots()[firstRoom].getRoom();
                player.getDungeon().getRoomSlots()[firstRoom].setRoom(null);
                game.getDiscardPile().add(room);
            }
        }
    },

    // Kobold Strike (Spell)
    SKIP_BUILD_PHASE {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            for (Player p: game.getPlayers()) {
                DungeonRoomSlot firstSlot = p.getDungeon().getRoomSlots()[p.getDungeon().getFirstRoomSlot()];
                if (!firstSlot.getIsVisible()) {
                    RoomCard room = firstSlot.getRoom();
                    if (p.getDungeon().getFirstRoomSlot() != 0) {
                        firstSlot.setRoom(null);
                        player.getHand().add(room);
                    }
                }
            }
            game.skipBuildPhase();
        }
    },

    // Teleportation (Spell)
    SEND_HERO_TO_FIRST_ROOM {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            // TODO
        }
    },

    // Jeopardy (Spell)
    EVERY_PLAYER_RESETS_THEIR_HAND {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            for (Player p: game.getPlayers()) {
                game.discardAllCards(p);
                for (int i = 0; i < 2; i ++) game.getNewRoomCard(p);
                game.getNewSpellCard(p);
            }
        }
    }

}
