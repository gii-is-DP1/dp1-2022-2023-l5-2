package org.springframework.samples.bossmonster.game.card.room;


//Enumerado actua como dummy para la base de datos.
public enum RoomPassiveTrigger{
    ENUMERADO,
    DESTROY_THIS_ROOM,
    DESTROY_ANOTHER_ROOM,
    HERO_DIES_IN_THIS_ROOM,
    DAMAGE_HERO,
    BUILD_THIS_ROOM,
    NONE,
    USE_SPELL_CARD,
    BUILD_MONSTER_ROOM,
    ADD_EXTRA_ROOM_DAMAGE,
    HERO_ENTERS_ROOM;
}
