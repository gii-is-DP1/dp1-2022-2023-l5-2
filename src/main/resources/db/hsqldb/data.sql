INSERT INTO users(username, password, enabled,nickname, email, description, avatar) VALUES 
    ('admin1',     '4dm1nrr',          TRUE, 'Sir Admin', 'pwalburn0@tinypic.com',        'Test test test? test! 123456789',  'http://localhost:8080/resources/images/avatars/avatar_00.png'),
    ('user1',      '0wn3rrr',          TRUE, 'Sir User',  'gnorthway1@wikimedia.org',     'What',                             'http://localhost:8080/resources/images/avatars/avatar_01.png'),
    ('eletomvel',  'EleTomas2002',     TRUE, 'Lykhant',   'helloiamemail@gmail.com',      'Twitter should not exist',         'http://localhost:8080/resources/images/avatars/avatar_02.png'),
    ('tadcabgom',  'helloimapassword', TRUE, 'Tadeo',     'iliketrains@gmail.com',        'What I am suppose to write here?', 'http://localhost:8080/resources/images/avatars/avatar_03.png'),
    ('igngongon2', 'userrr',           TRUE, 'Ignacio',   'abcdefghijk@gmail.com',        'Random description',               'http://localhost:8080/resources/images/avatars/avatar_04.png'),
    ('ignarrman',  'mydoggie',         TRUE, 'Nacho',     'manoalzadacocoreto@gmail.com', 'Hot Dog',                          'http://localhost:8080/resources/images/avatars/avatar_05.png'),
    ('jessolort',  'jessolort',        TRUE, 'Jesus',     'randomtext@gmail.com',         'abcde fghi jklmno pqrstu vwxyz',   'http://localhost:8080/resources/images/avatars/avatar_06.png'),
    ('frarosram',  'contra5ena',       TRUE, 'Paco',      'testemail@gmail.com',          'Kuak',                             'http://localhost:8080/resources/images/avatars/avatar_00.png'),
    ('fralarmar',  'qwertyuiop',       TRUE, 'Javi',      'zxcvbnmgl@gmail.com',          'My hat is my friend',              'http://localhost:8080/resources/images/avatars/avatar_01.png');

INSERT INTO authorities(id,username,authority) VALUES 
    (1,'admin1',     'admin'),
    (2,'user1',      'user'),
    (4,'eletomvel',  'user'),
    (5,'tadcabgom',  'user'),
    (6,'igngongon2', 'user'),
    (7,'ignarrman',  'user'),
    (8,'jessolort',  'user'),
    (9,'frarosram',  'user');



INSERT INTO rooms(id, name, card_image, room_type, treasure, damage, passive_trigger, effect, effect_target) VALUES

    (1, 'Bottomless Pit',      '/resources/images/rooms/room_00.jpg', 'TRAP', '0001', 1, 'DESTROY_THIS_ROOM','ENUMERADO','ENUMERADO'),
    (2, 'The Crushinator',     '/resources/images/rooms/room_01.jpg', 'ADVANCED_TRAP', '0001', 2, 'DESTROY_ANOTHER_ROOM','ENUMERADO','ENUMERADO'),
    (3, 'Vampire Bordello',    '/resources/images/rooms/room_02.jpg', 'ADVANCED_MONSTER', '0010', 3, 'HERO_DIES_IN_THIS_ROOM','ENUMERADO','ENUMERADO'),
    (4, 'Monster Ballroom',    '/resources/images/rooms/room_03.jpg', 'ADVANCED_MONSTER', '0100', 0, 'NONE','ENUMERADO','ENUMERADO'),
    (5, 'Boulder Ramp',        '/resources/images/rooms/room_04.jpg', 'TRAP', '0001', 1, 'DESTROY_ANOTHER_ROOM','ENUMERADO','ENUMERADO'),
    (6, 'Construction Zone',   '/resources/images/rooms/room_05.jpg', 'TRAP', '0101', 1, 'BUILD_THIS_ROOM','ENUMERADO','ENUMERADO'),
    (7, 'Dark Altar',          '/resources/images/rooms/room_06.jpg', 'TRAP', '0020', 1, 'DESTROY_THIS_ROOM','ENUMERADO','ENUMERADO'),
    (8, 'Dragon Hatchery',     '/resources/images/rooms/room_07.jpg', 'MONSTER', '1111', 0, 'NONE','ENUMERADO','ENUMERADO'),
    (9, 'Neanderthal Cave',    '/resources/images/rooms/room_08.jpg', 'MONSTER', '0100', 3, 'NONE','ENUMERADO','ENUMERADO'),
    (10, 'Open Grave',         '/resources/images/rooms/room_09.jpg', 'TRAP', '0010', 2, 'HERO_DIES_IN_THIS_ROOM','ENUMERADO','ENUMERADO'),
    (11, 'Recycling Center',   '/resources/images/rooms/room_10.jpg', 'ADVANCED_TRAP', '0001', 3, 'DESTROY_ANOTHER_ROOM','ENUMERADO','ENUMERADO'),
    (12, 'Ligers Den',         '/resources/images/rooms/room_11.jpg', 'ADVANCED_MONSTER', '1000', 2, 'USE_SPELL_CARD','ENUMERADO','ENUMERADO'),
    (13, 'Goblin Armory',      '/resources/images/rooms/room_12.jpg', 'MONSTER', '0200', 1, 'NONE','ENUMERADO','ENUMERADO'),
    (14, 'Golem Factory',      '/resources/images/rooms/room_13.jpg', 'MONSTER', '0100', 2, 'HERO_DIES_IN_THIS_ROOM','ENUMERADO','ENUMERADO'),
    (15, 'Jackpot Stash',      '/resources/images/rooms/room_14.jpg', 'TRAP', '0002', 1, 'DESTROY_THIS_ROOM','ENUMERADO','ENUMERADO'),
    (16, 'Dark Laboratory',    '/resources/images/rooms/room_15.jpg', 'TRAP', '2000', 1, 'BUILD_THIS_ROOM','ENUMERADO','ENUMERADO'),
    (17, 'Monstrous Monument', '/resources/images/rooms/room_16.jpg', 'TRAP', '0110', 1, 'BUILD_THIS_ROOM','ENUMERADO','ENUMERADO'),
    (18, 'Beast Menagerie',    '/resources/images/rooms/room_17.jpg', 'ADVANCED_MONSTER', '0100', 4, 'BUILD_MONSTER_ROOM','ENUMERADO','ENUMERADO'),
    (19, 'Brainsucker Hive',   '/resources/images/rooms/room_18.png', 'MONSTER', '1000', 2, 'HERO_DIES_IN_THIS_ROOM','ENUMERADO','ENUMERADO'),
    (20, 'Dizzygas Hallway',   '/resources/images/rooms/room_19.png', 'TRAP', '0001', 1, 'NONE','ENUMERADO','ENUMERADO'),
    (21, 'Minotaurs Maze',     '/resources/images/rooms/room_20.png', 'MONSTER', '0100', 0, 'NONE','ENUMERADO','ENUMERADO');

INSERT INTO spell_card(id, name, card_image, phase, target, effect, requirements) VALUES
    (22,'Giant Size'       ,'/resources/images/spells/spell_00.jpg','adventurePhase'               ,'MONSTERROOM'  ,'ENUMERADO','HAVEMONSTERROOM')      ,
    (23,'Soul Harvest'     ,'/resources/images/spells/spell_01.jpg','adventureAndConstructionPhase','HEROINSCORE'  ,'ENUMERADO','HAVESOULS')            ,
    (24,'Princess in Peril','/resources/images/spells/spell_02.jpg','constructionPhase'            ,'HEROINTOWN'   ,'ENUMERADO','THEREISHEROINTOWN')    ,
    (25,'Motivation'       ,'/resources/images/spells/spell_03.jpg','constructionPhase'            ,'ALLPLAYERS'   ,'ENUMERADO','NONE')                 ,
    (26,'Exhaustion'       ,'/resources/images/spells/spell_04.jpg','adventurePhase'               ,'HEROINDUNGEON','ENUMERADO','THEREISHEROINDUNGEON') ,
    (27,'Annihilator'      ,'/resources/images/spells/spell_05.jpg','adventurePhase'               ,'TRAPROOM'     ,'ENUMERADO','HAVETRAPROOM')         ,
    (28,'Cave-in'          ,'/resources/images/spells/spell_06.jpg','adventurePhase'               ,'ROOM'         ,'ENUMERADO','HAVEROOM')             ,
    (29,'Kobold Strike'    ,'/resources/images/spells/spell_07.jpg','constructionPhase'            ,'ALLPLAYERS'   ,'ENUMERADO','NONE')                 ,
    (30,'Teleportation'    ,'/resources/images/spells/spell_08.jpg','adventurePhase'               ,'HEROINDUNGEON','ENUMERADO','THEREISHEROINDUNGEON');

INSERT INTO heroes(id, name, card_image, treasure, health, is_epic, necessary_players) VALUES 
    (31,     'Acacia,_Warrior_of_Light',             '/resources/images/heroes/Acacia,_Warrior_of_Light.jpg',           'CROSS', 6, false, 2),
    (32,     'Antonius,_the_Rune_Knight',            '/resources/images/heroes/Antonius,_the_Rune_Knight.jpg',          'SWORD', 13, true, 2),
    (33,     'Asmor_the_Aweless',                    '/resources/images/heroes/Asmor_the_Aweless.jpg',                  'SWORD', 13, true, 4),
    (34,     'Blackbeard_Jake',                      '/resources/images/heroes/Blackbeard_Jake.jpg',                    'BAG', 11, true, 3),
    (35,     'Boden_the_Pantless' ,                  '/resources/images/heroes/Boden_the_Pantless.jpg',                 'SWORD', 4, false, 2),
    (36,     'Brandork_the_Neverwrong' ,             '/resources/images/heroes/Brandork_the_Neverwrong.jpg',            'BOOK', 4, false, 4),
    (37,     'Cecil_Leoran,_Master_Factotum' ,       '/resources/images/heroes/Cecil_Leoran,_Master_Factotum.jpg',      'BAG', 13, true, 2),
    (38,     'Charles_the_Young' ,                   '/resources/images/heroes/Charles_the_Young.jpg',                  'CROSS', 6, false, 3),
    (39,     'Chia_Kang,_Mystical_Warlock_of_Yu' ,   '/resources/images/heroes/Chia_Kang,_Mystical_Warlock_of_Yu.jpg',  'BOOK', 11, true, 3),
    (40,    'Crystol_and_A`lan_of_Gerd' ,           '/resources/images/heroes/Crystol_and_A%27lan_of_Gerd.jpg',        'SWORD', 8, false, 4),
    (41,    'Dartteon,_Elf_Pyromancer' ,            '/resources/images/heroes/Dartteon,_Elf_Pyromancer.jpg',           'BOOK', 8, false, 2),
    (42,    'Delatorious,_Angel_of_Light' ,         '/resources/images/heroes/Delatorious,_Angel_of_Light.jpg',        'CROSS', 8, false, 2),
    (43,    'Fire%27s_Breath,_Heroine_of_Arcadia' , '/resources/images/heroes/Fire%27s_Breath,_Heroine_of_Arcadia.jpg','SWORD', 6, false, 2),
    (44,    'Frankov,_the_Envoy' ,                  '/resources/images/heroes/Frankov,_the_Envoy.jpg',                 'SWORD', 11, true, 2),
    (45,    'Hya,_Legendary_Shinobi' ,              '/resources/images/heroes/Hya,_Legendary_Shinobi.jpg',             'BAG', 11, true, 2),
    (46,    'Jarek,_Squire_to_the_Lion_Knights' ,   '/resources/images/heroes/Jarek,_Squire_to_the_Lion_Knights.jpg',  'SWORD', 4, false, 4),
    (47,    'Jejune_%26_Everlea,_Holy_Sisters' ,    '/resources/images/heroes/Jejune_%26_Everlea,_Holy_Sisters.jpg',   'CROSS', 13, true, 4),
    (48,    'Jerome,_Kung_Fu_Monkey' ,              '/resources/images/heroes/Jerome,_Kung_Fu_Monkey.jpg',             'BAG', 8, false, 4),
    (49,    'Jesta_the_Rogue' ,                     '/resources/images/heroes/Jesta_the_Rogue.jpg',                    'BAG', 8, false, 4),
    (50,    'Johnny_of_the_Evening_Watch' ,         '/resources/images/heroes/Johnny_of_the_Evening_Watch.jpg',        'SWORD', 8, false, 2),
    (51,    'Joman_Chimm,_Cutpurse' ,               '/resources/images/heroes/Joman_Chimm,_Cutpurse.jpg',              'BAG', 4, false, 2),
    (52,    'Kalish_Ninefingers' ,                  '/resources/images/heroes/Kalish_Ninefingers.jpg',                 'BOOK', 6, false, 3),
    (53,    'Katelyn,_Angelic_Healer' ,             '/resources/images/heroes/Katelyn,_Angelic_Healer.jpg',            'CROSS', 11, true, 2),
    (54,    'Kerberos_Dirtbeard,_Canine_Cleric' ,   '/resources/images/heroes/Kerberos_Dirtbeard,_Canine_Cleric.jpg',  'CROSS', 11, true, 3),
    (55,    'Kins_Klauski,_Mad_Conquistador' ,      '/resources/images/heroes/Kins_Klauski,_Mad_Conquistador.jpg',     'BAG', 6, false, 3),
    (56,    'Koey,_the_Last_Dragon_Mage' ,          '/resources/images/heroes/Koey,_the_Last_Dragon_Mage.jpg',         'BOOK', 8, false, 4),
    (57,    'Lance_Uppercut,_Treasure_Hunter' ,     '/resources/images/heroes/Lance_Uppercut,_Treasure_Hunter.jpg',    'BAG', 4, false, 4),
    (58,    'Lord_Van_Ette' ,                       '/resources/images/heroes/Lord_Van_Ette.jpg',                      'CROSS', 13, true, 2),
    (59,    'Mitchell,_the_Judge' ,                 '/resources/images/heroes/Mitchell,_the_Judge.jpg',                'BOOK', 6, false, 2),
    (60,    'Nate_the_Squidslayer' ,                '/resources/images/heroes/Nate_the_Squidslayer.jpg',               'SWORD', 11, true, 3),
    (61,    'Nick_the_Masher' ,                     '/resources/images/heroes/Nick_the_Masher.jpg',                    'CROSS', 4, false, 2),
    (62,    'Pugi_the_Druidess' ,                   '/resources/images/heroes/Pugi_the_Druidess.jpg',                  'CROSS', 4, false, 4),
    (63,    'Romero,_the_Indigo_Friar' ,            '/resources/images/heroes/Romero,_the_Indigo_Friar.jpg',           'CROSS', 8, false, 4),
    (64,    'Samurai_Tom' ,                         '/resources/images/heroes/Samurai_Tom.jpg',                        'SWORD', 6, false, 3),
    (65,    'Sir_Digby_Apple,_Ace_Detective' ,      '/resources/images/heroes/Sir_Digby_Apple,_Ace_Detective.jpg',     'BAG', 8, false, 2),
    (66,    'Tempros_the_Time_Marauder',            '/resources/images/heroes/Tempros_the_Time_Marauder.jpg',          'BOOK', 11, true, 2),
    (67,    'Terric_Warhelm,_Half-Elf_Archmage',    '/resources/images/heroes/Terric_Warhelm,_Half-Elf_Archmage.jpg',  'BOOK', 13, true, 2),
    (68,    'The_Fool' ,                            '/resources/images/heroes/The_Fool.jpg',                           'FOOL', 2, false, 2),
    (69,    'Tieg_and_the_Magic_Bubble',            '/resources/images/heroes/Tieg_and_the_Magic_Bubble.jpg',          'BOOK', 4, false, 2),
    (70,    'Wallbanger_Basketweaver',              '/resources/images/heroes/Wallbanger_Basketweaver.jpg',            'BAG', 13, true, 4),
    (71,    'Wayward,_the_Drifter',                 '/resources/images/heroes/Wayward,_the_Drifter.jpg',               'BOOK', 13, true, 4);

INSERT INTO bosses(id, name, card_image, xp, treasure) VALUES
    (72, 'Belladona',         '/resources/images/bosses/boss_00.jpg', 350, 'CROSS'),
    (73, 'The Brothers Wise', '/resources/images/bosses/boss_01.jpg', 775, 'BOOK'),
    (74, 'Xyzax',             '/resources/images/bosses/boss_02.jpg', 750, 'CROSS'),
    (75, 'Cerebellus',        '/resources/images/bosses/boss_03.jpg', 650, 'BOOK'),
    (76, 'King Croak',        '/resources/images/bosses/boss_04.jpg', 800, 'SWORD'),
    (77, 'Seducia',           '/resources/images/bosses/boss_05.jpg', 600, 'BOOK'),
    (78, 'Cleopatra',         '/resources/images/bosses/boss_06.jpg', 850, 'BAG');

INSERT INTO lobbies(id, max_players, leader, game_id) VALUES
    (1, 2, 'ignarrman',null),
    (2, 3, 'eletomvel',null),
    (3, 3, 'admin1',null);

INSERT INTO lobby_users(lobby_id,user_id) VALUES
    (1, 'ignarrman'),
    (1, 'tadcabgom'),
    (2, 'frarosram'),
    (2, 'jessolort'),
    (2, 'eletomvel'),
    -- Para comprobar el juego, unirse al lobby 3 como admin1 y "espectador"
    (3, 'eletomvel'),
    (3, 'admin1');

INSERT INTO game_result(id,winner,duration,date) VALUES
    (1,'ignarrman', 1.26, '2018-08-12'),
    (2,'ignarrman', 3.40, '2018-08-14'),
    (3,'jessolort', 0.45, '2018-08-15'),
    (4,'tadcabgom', 2.11, '2018-08-15'),
    (5,'user1',     1.01, '2018-08-16'),
    (6,'fralarmar', 3.57, '2018-08-20');

INSERT INTO results_users(game_result_id,user_id) VALUES 
    (1,'tadcabgom'),
    (1,'jessolort'),
    (1,'fralarmar'),
    (1,'ignarrman'),
    (2,'eletomvel'),
    (2,'user1'),
    (2,'igngongon2'),
    (2,'ignarrman'),
    (3,'jessolort'),
    (3,'igngongon2'),
    (3,'tadcabgom'),
    (4,'tadcabgom'),
    (4,'ignarrman'),
    (5,'tadcabgom'),
    (5,'fralarmar'),
    (5,'jessolort'),
    (5,'user1'),
    (6,'eletomvel'),
    (6,'fralarmar'),
    (6,'igngongon2'),
    (6,'ignarrman');

INSERT INTO achievement(id, name, description, image, threshold, metric) VALUES
    (1, 'Tutorial Monster', 'Win 1 game.', '/resources/images/bosses/boss_00.jpg', 1, 'VICTORIES'),
    (2, 'Room Monster', 'Win 5 game.', '/resources/images/bosses/boss_00.jpg', 5, 'VICTORIES'),
    (3, 'Midboss Monster', 'Win 25 games.', '/resources/images/bosses/boss_00.jpg', 10, 'VICTORIES');

INSERT INTO achievement_users(username, achievement_id) VALUES
    ('igngongon2', 1),
    ('jessolort', 2),
    ('fralarmar', 1),
    ('tadcabgom', 3),
    ('eletomvel', 3),
    ('ignarrman', 1);

INSERT INTO friend_requests(id,accepted, requester, receiver) VALUES
    (0,TRUE, 'ignarrman', 'fralarmar'),
    (1,TRUE, 'ignarrman', 'eletomvel'),
    (2,TRUE, 'ignarrman', 'jessolort'),
    (3,TRUE, 'ignarrman', 'frarosram'),
    (4,TRUE, 'ignarrman', 'igngongon2'),
    (5,FALSE, 'user1', 'ignarrman');