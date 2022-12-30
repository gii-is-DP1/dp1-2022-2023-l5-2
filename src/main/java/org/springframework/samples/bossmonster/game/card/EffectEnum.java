package org.springframework.samples.bossmonster.game.card;

import org.springframework.samples.bossmonster.game.Game;
import org.springframework.samples.bossmonster.game.card.room.RoomType;
import org.springframework.samples.bossmonster.game.dungeon.Dungeon;
import org.springframework.samples.bossmonster.game.dungeon.DungeonRoomSlot;
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
            player.damageRandomHeroInDungeonPosition(dungeonPosition, 99);
        }
    },

    // The Crushinator (Room)
    ADD_2_DAMAGE_TO_EVERY_ROOM {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            Dungeon dungeon = player.getDungeon();
            for (DungeonRoomSlot drs: dungeon.getRoomSlots()) {
                drs.setRoomTrueDamage(drs.getRoomTrueDamage() + 2);
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
            player.damageRandomHeroInDungeonPosition(dungeonPosition, 5);
        }
    },

    // Construction Zone (Room)
    BUILD_ANOTHER_ROOM {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            game.getState().setActionLimit(game.getState().getActionLimit() + 2);
        }
    },

    // Dark Altar (Room)
    CHOOSE_CARD_FROM_DISCARD_PILE {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            // TODO
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
            // TODO
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
            // TODO
        }
    },

    // Dark Laboratory (Room)
    DRAW_2_SPELL_CARDS_AND_DISCARD_1_SPELL_CARD {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            for (int i = 0; i < 2; i ++) game.getNewSpellCard(player);
            // TODO
        }
    },

    // Monstrous Monument (Room)
    CHOOSE_MONSTER_ROOM_CARD_FROM_DISCARD_PILE {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            // TODO
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
            // TODO
        }
    },

    // The Brothers Wise (Boss)
    CHOOSE_SPELL_CARD_FROM_SPELL_PILE {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            // TODO
        }
    },

    // Xyzax (Boss)
    CHOOSE_2_CARDS_FROM_DISCARD_PILE {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            // TODO
        }
    },

    // Cerebellus (Boss)
    DRAW_3_SPELL_CARDS_AND_DISCARD_1_SPELL_CARD {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            for (int i = 0; i < 3; i ++) game.getNewSpellCard(player);
            // TODO
        }
    },

    // King Croak (Boss)
    BUILD_AN_ADVANCED_MONSTER_ROOM_CHOSEN_FROM_THE_ROOM_PILE_OR_DISCARD_PILE {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            // TODO
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
            // TODO
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
            // TODO
        }
    },

    // Motivation (Spell)
    BUILD_ANOTHER_ROOM_IF_ANOTHER_PLAYER_HAS_MORE_ROOMS {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            
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
            // TODO
        }
    },

    // Cave-In (Spell)
    DESTROY_A_DUNGEON_KILLING_EVERY_HERO_IN_IT {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            // TODO
        } 
    },

    // Kobold Strike (Spell)
    SKIP_BUILD_PHASE {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            // TODO
        } 
    },

    // Teleportation (Spell)
    SEND_HERO_TO_FIRST_ROOM {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            // TODO
        } 
    }

}
