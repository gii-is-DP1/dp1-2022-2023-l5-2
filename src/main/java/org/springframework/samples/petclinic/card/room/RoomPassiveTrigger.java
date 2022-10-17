package org.springframework.samples.petclinic.card.room;

public enum RoomPassiveTrigger {
    DESTROY_THIS_ROOM, 
    DESTROY_ANOTHER_ROOM, 
    HERO_DIES_IN_THIS_ROOM,
    DAMAGE_HERO,
    BUILD_THIS_ROOM,
    NONE,
    USE_SPELL_CARD,
    BUILD_MONSTER_ROOM
};