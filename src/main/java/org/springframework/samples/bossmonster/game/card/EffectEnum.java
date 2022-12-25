package org.springframework.samples.bossmonster.game.card;

import org.springframework.samples.bossmonster.game.Game;
import org.springframework.samples.bossmonster.game.dungeon.Dungeon;
import org.springframework.samples.bossmonster.game.dungeon.DungeonRoomSlot;
import org.springframework.samples.bossmonster.game.player.Player;

//Enumerado actua como dummy para la base de datos.
public enum EffectEnum implements EffectInterface {
    
    KILL_ONE_HERO_IN_THIS_ROOM {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            // TODO
        }
    },

    // Implemented. Not tested
    ADD_2_DAMAGE_TO_EVERY_ROOM {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            Dungeon dungeon = player.getDungeon();
            for (DungeonRoomSlot drs: dungeon.getRoomSlots()) {
                drs.setRoomTrueDamage(drs.getRoomTrueDamage() + 2);
            }
        }
    },

    // Implemented. Not tested
    CONVERT_A_WOUND_INTO_A_SOUL {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            if (player.getHealth() < 5) {
                player.setHealth(player.getHealth() + 1);
                player.setSouls(player.getSouls() - 1);
            }
        }
    },

    DEAL_5_DAMAGE_TO_A_HERO_IN_THIS_ROOM {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            // TODO
        }
    },

    // Implemented. Not tested
    BUILD_ANOTHER_ROOM {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            game.getState().setActionLimit(game.getState().getActionLimit() + 2);
        }
    },

    CHOOSE_CARD_FROM_DISCARD_PILE {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            // TODO
        }
    },

    // Implemented. Not tested
    NOTHING {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {}
    },

    CHOOSE_ROOM_CARD_FROM_DISCARD_PILE {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {

        }
    },

    // Implemented. Not tested
    DRAW_2_ROOM_CARDS {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            for (int i = 0; i < 2; i ++) game.getNewRoomCard(player);
        }
    },

    // Implemented. Not tested
    DRAW_A_SPELL_CARD {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            game.getNewSpellCard(player);
        }
    },

    ADD_1_DAMAGE_TO_ADYACENT_MONSTER_ROOMS {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            // TODO
        }
    },

    // Implemented. Not tested
    DRAW_A_ROOM_CARD {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            game.getNewRoomCard(player);
        }
    },

    DOUBLE_DUNGEON_TREASURE_VALUE {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            // TODO
        }
    },

    DRAW_2_SPELL_CARDS_AND_DISCARD_1_SPELL_CARD {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            for (int i = 0; i < 2; i ++) game.getNewSpellCard(player);
            // TODO
        }
    },

    CHOOSE_MONSTER_ROOM_CARD_FROM_DISCARD_PILE {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            // TODO
        }
    },

    ADD_2_DAMAGE_TO_NEXT_ROOM_IF_IT_IS_A_TRAP_ROOM {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            // TODO
        }
    },

    PUSH_HERO_TO_PREVIOUS_ROOM_ONCE {
        @Override
        public void apply(Player player, Integer dungeonPosition, Game game) {
            // TODO
        }
    }

}
