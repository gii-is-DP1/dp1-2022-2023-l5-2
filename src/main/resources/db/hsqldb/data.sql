
INSERT INTO user_avatar(id, name) VALUES
    (1, 'AVATAR_1'),
    (2, 'AVATAR_2'),
    (3, 'AVATAR_3'),
    (4, 'AVATAR_4'),
    (5, 'AVATAR_5'),
    (6, 'AVATAR_6'),
    (7, 'AVATAR_7');

INSERT INTO users(username, password, enabled,nickname, email, description, avatar) VALUES 
    ('admin1',     '4dm1n',            TRUE, 'Sir Admin', 'pwalburn0@tinypic.com',        'Test test tes? test! 123456789',   'http://dummyimage.com/176x100.png/5fa2dd/ffffff'),
    ('user1',      '0wn3r',            TRUE, 'Sir User',  'gnorthway1@wikimedia.org',     'What',                             'http://dummyimage.com/105x100.png/5fa2dd/ffffff'),
    ('eletomvel',  'EleTomas2002',     TRUE, 'Lykhant',   'helloiamemail@gmail.com',      'Twitter should not exist',         'http://dummyimage.com/105x100.png/5fa2dd/ffffff'),
    ('tadcabgom',  'helloimapassword', TRUE, 'Tadeo',     'iliketrains@gmail.com',        'What I am suppose to write here?', 'http://dummyimage.com/105x100.png/5fa2dd/ffffff'),
    ('igngongon2', 'user',             TRUE, 'Ignacio',   'abcdefghijk@gmail.com',        'Random description',               'http://dummyimage.com/105x100.png/5fa2dd/ffffff'),
    ('ignarrman',  'password',         TRUE, 'Nacho',     'manoalzadacocoreto@gmail.com', 'Hot Dog',                          'http://dummyimage.com/105x100.png/5fa2dd/ffffff'),
    ('jessolort',  'jessolort',        TRUE, 'Jesus',     'randomtext@gmail.com',         'abcde fghi jklmno pqrstu vwxyz',   'http://dummyimage.com/105x100.png/5fa2dd/ffffff'),
    ('frarosram',  'contra5ena',       TRUE, 'Paco',      'testemail@gmail.com',          'Kuak',                             'http://dummyimage.com/105x100.png/5fa2dd/ffffff'),
    ('fralarmar',  'qwertyuiop',       TRUE, 'Javi',      'zxcvbnmgl@gmail.com',          'My hat is my friend',              'http://dummyimage.com/105x100.png/5fa2dd/ffffff');

INSERT INTO authorities(id,username,authority) VALUES 
    (1,'admin1',     'admin'),
    (2,'user1',      'user'),
    (4,'eletomvel',  'user'),
    (5,'tadcabgom',  'user'),
    (6,'igngongon2', 'user'),
    (7,'ignarrman',  'user'),
    (8,'jessolort',  'user'),
    (9,'frarosram',  'user');

INSERT INTO room_type(id, name) VALUES
    (1,'TRAP'), (2,'MONSTER'), (3,'ADVANCED_TRAP'), (4,'ADVANCED_MONSTER');

INSERT INTO room_passive_trigger(id, name) VALUES
    (1,'DESTROY_THIS_ROOM'),
    (2,'DESTROY_ANOTHER_ROOM'),
    (3,'HERO_DIES_IN_THIS_ROOM'),
    (4,'DAMAGE_HERO'),
    (5,'BUILD_THIS_ROOM'),
    (6,'NONE'),
    (7,'USE_SPELL_CARD'),
    (8,'BUILD_MONSTER_ROOM');


INSERT INTO rooms(id, name, card_image, room_type_id, treasure, damage, passive_trigger_id) VALUES

    (1, 'Bottomless Pit',      'resources/static/resources/images/rooms/room_00.jpg', 1, '0001', 1, 1),
    (2, 'The Crushinator',     'resources/static/resources/images/rooms/room_01.jpg', 3, '0001', 2, 2),
    (3, 'Vampire Bordello',    'resources/static/resources/images/rooms/room_02.jpg', 4, '0010', 3, 3),
    (4, 'Monster Ballroom',    'resources/static/resources/images/rooms/room_03.jpg', 4, '0100', 0, 4),
    (5, 'Boulder Ramp',        'resources/static/resources/images/rooms/room_04.jpg', 1, '0001', 1, 2),
    (6, 'Construction Zone',   'resources/static/resources/images/rooms/room_05.jpg', 1, '0101', 1, 5),
    (7, 'Dark Altar',          'resources/static/resources/images/rooms/room_06.jpg', 1, '0020', 1, 1),
    (8, 'Dragon Hatchery',     'resources/static/resources/images/rooms/room_07.jpg', 2, '1111', 0, 6),
    (9, 'Neanderthal Cave',    'resources/static/resources/images/rooms/room_08.jpg', 2, '0100', 3, 6),
    (10, 'Open Grave',         'resources/static/resources/images/rooms/room_09.jpg', 1, '0010', 2, 3),
    (11, 'Recycling Center',   'resources/static/resources/images/rooms/room_10.jpg', 3, '0001', 3, 2),
    (12, 'Ligers Den',         'resources/static/resources/images/rooms/room_11.jpg', 4, '1000', 2, 7),
    (13, 'Goblin Armory',      'resources/static/resources/images/rooms/room_12.jpg', 2, '0200', 1, 6),
    (14, 'Golem Factory',      'resources/static/resources/images/rooms/room_13.jpg', 2, '0100', 2, 3),
    (15, 'Jackpot Stash',      'resources/static/resources/images/rooms/room_14.jpg', 1, '0002', 1, 1),
    (16, 'Dark Laboratory',    'resources/static/resources/images/rooms/room_15.jpg', 1, '2000', 1, 5),
    (17, 'Monstrous Monument', 'resources/static/resources/images/rooms/room_16.jpg', 1, '0110', 1, 5),
    (18, 'Beast Menagerie',    'resources/static/resources/images/rooms/room_17.jpg', 4, '0100', 4, 8),
    (19, 'Brainsucker Hive',   'resources/static/resources/images/rooms/room_18.jpg', 2, '1000', 2, 3),
    (20, 'Dizzygas Hallway',   'resources/static/resources/images/rooms/room_19.jpg', 1, '0001', 1, 6),
    (21, 'Minotaurs Maze',     'resources/static/resources/images/rooms/room_20.jpg', 2, '0100', 0, 4);

INSERT INTO heroes(id, name, card_image, treasure, health, is_epic, necessary_players) VALUES 
    (1,     'Acacia,_Warrior_of_Light',             'resources/static/resources/images/heroes/Acacia,_Warrior_of_Light.jpg',           'cleric', 6 , false, 2),
    (2,     'Antonius,_the_Rune_Knight',            'resources/static/resources/images/heroes/Antonius,_the_Rune_Knight.jpg',          'fighter', 13 , true, 2),
    (3,     'Asmor_the_Aweless',                    'resources/static/resources/images/heroes/Asmor_the_Aweless.jpg',                  'fighter', 13 , true, 4),
    (4,     'Blackbeard_Jake',                      'resources/static/resources/images/heroes/Blackbeard_Jake.jpg',                    'thief', 11 , true, 3),
    (5,     'Boden_the_Pantless' ,                  'resources/static/resources/images/heroes/Boden_the_Pantless.jpg',                 'fighter', 4 , false, 2),
    (6,     'Brandork_the_Neverwrong' ,             'resources/static/resources/images/heroes/Brandork_the_Neverwrong.jpg',            'mage', 4 , false, 4),
    (7,     'Cecil_Leoran,_Master_Factotum' ,       'resources/static/resources/images/heroes/Cecil_Leoran,_Master_Factotum.jpg',      'thief', 13 , true, 2),
    (8,     'Charles_the_Young' ,                   'resources/static/resources/images/heroes/Charles_the_Young.jpg',                  'cleric', 6 , false, 3),
    (9,     'Chia_Kang,_Mystical_Warlock_of_Yu' ,   'resources/static/resources/images/heroes/Chia_Kang,_Mystical_Warlock_of_Yu.jpg',  'mage', 11 , true, 3),
    (10,    'Crystol_and_A`lan_of_Gerd' ,           'resources/static/resources/images/heroes/Crystol_and_A%27lan_of_Gerd.jpg',        'fighter', 8 , false, 4),
    (11,    'Dartteon,_Elf_Pyromancer' ,            'resources/static/resources/images/heroes/Dartteon,_Elf_Pyromancer.jpg',           'mage', 8 , false, 2),
    (12,    'Delatorious,_Angel_of_Light' ,         'resources/static/resources/images/heroes/Delatorious,_Angel_of_Light.jpg',        'cleric', 8 , false, 2),
    (13,    'Fire%27s_Breath,_Heroine_of_Arcadia' , 'resources/static/resources/images/heroes/Fire%27s_Breath,_Heroine_of_Arcadia.jpg','fighter', 6 , false, 2),
    (14,    'Frankov,_the_Envoy' ,                  'resources/static/resources/images/heroes/Frankov,_the_Envoy.jpg',                 'fighter', 11 , true, 2),
    (15,    'Hya,_Legendary_Shinobi' ,              'resources/static/resources/images/heroes/Hya,_Legendary_Shinobi.jpg',             'thief', 11 , true, 2),
    (16,    'Jarek,_Squire_to_the_Lion_Knights' ,   'resources/static/resources/images/heroes/Jarek,_Squire_to_the_Lion_Knights.jpg',  'fighter', 4 , false, 4),
    (17,    'Jejune_%26_Everlea,_Holy_Sisters' ,    'resources/static/resources/images/heroes/Jejune_%26_Everlea,_Holy_Sisters.jpg',   'cleric', 13, true, 4),
    (18,    'Jerome,_Kung_Fu_Monkey' ,              'resources/static/resources/images/heroes/Jerome,_Kung_Fu_Monkey.jpg',             'thief', 8 , false, 4),
    (19,    'Jesta_the_Rogue' ,                     'resources/static/resources/images/heroes/Jesta_the_Rogue.jpg',                    'thief', 8 , false, 4),
    (20,    'Johnny_of_the_Evening_Watch' ,         'resources/static/resources/images/heroes/Johnny_of_the_Evening_Watch.jpg',        'fighter', 8 , false, 2),
    (21,    'Joman_Chimm,_Cutpurse' ,               'resources/static/resources/images/heroes/Joman_Chimm,_Cutpurse.jpg',              'thief', 4, false, 2),
    (22,    'Kalish_Ninefingers' ,                  'resources/static/resources/images/heroes/Kalish_Ninefingers.jpg',                 'mage', 6, false, 3),
    (23,    'Katelyn,_Angelic_Healer' ,             'resources/static/resources/images/heroes/Katelyn,_Angelic_Healer.jpg',            'cleric', 11, true, 2),
    (24,    'Kerberos_Dirtbeard,_Canine_Cleric' ,   'resources/static/resources/images/heroes/Kerberos_Dirtbeard,_Canine_Cleric.jpg',  'cleric', 11, true, 3),
    (25,    'Kins_Klauski,_Mad_Conquistador' ,      'resources/static/resources/images/heroes/Kins_Klauski,_Mad_Conquistador.jpg',     'thief', 6, false, 3),
    (26,    'Koey,_the_Last_Dragon_Mage' ,          'resources/static/resources/images/heroes/Koey,_the_Last_Dragon_Mage.jpg',         'mage', 8, false, 4),
    (27,    'Lance_Uppercut,_Treasure_Hunter' ,     'resources/static/resources/images/heroes/Lance_Uppercut,_Treasure_Hunter.jpg',    'thief', 4, false, 4),
    (28,    'Lord_Van_Ette' ,                       'resources/static/resources/images/heroes/Lord_Van_Ette.jpg',                      'cleric', 13, true, 2),
    (29,    'Mitchell,_the_Judge' ,                 'resources/static/resources/images/heroes/Mitchell,_the_Judge.jpg',                'mage', 6, false, 2),
    (30,    'Nate_the_Squidslayer' ,                'resources/static/resources/images/heroes/Nate_the_Squidslayer.jpg',               'fighter', 11, true, 3),
    (31,    'Nick_the_Masher' ,                     'resources/static/resources/images/heroes/Nick_the_Masher.jpg',                    'cleric', 4, false, 2),
    (32,    'Pugi_the_Druidess' ,                   'resources/static/resources/images/heroes/Pugi_the_Druidess.jpg',                  'cleric', 4, false, 4),
    (33,    'Romero,_the_Indigo_Friar' ,            'resources/static/resources/images/heroes/Romero,_the_Indigo_Friar.jpg',           'cleric', 8, false, 4),
    (34,    'Samurai_Tom' ,                         'resources/static/resources/images/heroes/Samurai_Tom.jpg',                        'fighter', 6, false, 3),
    (35,    'Sir_Digby_Apple,_Ace_Detective' ,      'resources/static/resources/images/heroes/Sir_Digby_Apple,_Ace_Detective.jpg',     'thief', 8, false, 2),
    (36,    'Tempros_the_Time_Marauder',            'resources/static/resources/images/heroes/Tempros_the_Time_Marauder.jpg',          'mage', 11, true, 2),
    (37,    'Terric_Warhelm,_Half-Elf_Archmage',    'resources/static/resources/images/heroes/Terric_Warhelm,_Half-Elf_Archmage.jpg',  'mage', 13, true, 2),
    (38,    'The_Fool' ,                            'resources/static/resources/images/heroes/The_Fool.jpg',                           'fool', 2, false, 2),
    (39,    'Tieg_and_the_Magic_Bubble',            'resources/static/resources/images/heroes/Tieg_and_the_Magic_Bubble.jpg',          'mage', 4, false, 2),
    (40,    'Wallbanger_Basketweaver',              'resources/static/resources/images/heroes/Wallbanger_Basketweaver.jpg',            'thief', 13, true, 4),
    (41,    'Wayward,_the_Drifter',                 'resources/static/resources/images/heroes/Wayward,_the_Drifter.jpg',               'mage', 13, true, 4);

INSERT INTO bosses(id, name, card_image, xp, treasure) VALUES
    (1, 'Belladona',         'resources/static/resources/images/bosses/boss_00.jpg', 350, '0010'),
    (2, 'The Brothers Wise', 'resources/static/resources/images/bosses/boss_01.jpg', 775, '1000'),
    (3, 'Xyzax',             'resources/static/resources/images/bosses/boss_02.jpg', 750, '0010'),
    (4, 'Cerebellus',        'resources/static/resources/images/bosses/boss_03.jpg', 650, '1000'),
    (5, 'King Croak',        'resources/static/resources/images/bosses/boss_04.jpg', 800, '0100'),
    (6, 'Seducia',           'resources/static/resources/images/bosses/boss_05.jpg', 600, '1000'),
    (7, 'Cleopatra',         'resources/static/resources/images/bosses/boss_06.jpg', 850, '0001');

INSERT INTO lobbies(id, max_players) VALUES
    (1, 2),
    (2, 3);

INSERT INTO game_result(id,winner,duration,date) VALUES
    (1,'ignarrman',1.26,'2018-08-12'),
    (2,'ignarrman',3.40,'2018-08-14');

INSERT INTO results_users(game_result_id,user_id) VALUES 
    (1,'tadcabgom'),
    (1,'jessolort'),
    (1,'fralarmar'),
    (1,'ignarrman'),
    (2,'eletomvel'),
    (2,'user1'),
    (2,'igngongon2'),
    (2,'ignarrman');

