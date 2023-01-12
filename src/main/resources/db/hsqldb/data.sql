/*      
        Usuario        //       Passwords
        'admin1'                '4dm1nrr'
        'user1',                '0wn3rrr'
        'eletomvel'             'EleTomas2002'  
        'tadcabgom'             'helloimapassword'
        'igngongon2'            'userrr'      
        'ignarrman'             'mydoggie'      
        'jessolort'             'jessolort'      
        'frarosram'             'contra5ena' 
        'fralarmar'             'qwertyuiop'
*/

INSERT INTO users(username, password, enabled,nickname, email, description, avatar) VALUES 
    ('admin1',     '$2a$10$13lTS09EGbjGdINpgAP5kOmzOgJROhL/NoQeFH7nrK0VnHCOoA.Jm', TRUE, 'Sir Admin', 'pwalburn0@tinypic.com',        'Test test test? test! 123456789',  'http://localhost:8080/resources/images/avatars/avatar_00.png'),
    ('user1',      '$2a$10$9U8JtIwMD53xR7ehAYcx.OBKxmwJQXPVXEQYBNeK6YHaHtEpIMhPa', TRUE, 'Sir User',  'gnorthway1@wikimedia.org',     'What',                             'http://localhost:8080/resources/images/avatars/avatar_01.png'),
    ('eletomvel',  '$2a$10$3Bpea0yG4CR/uA/NBTHFmeZ.92BiyVh7I9zNYXbo3wmwc8FNumj2e', TRUE, 'Lykhant',   'helloiamemail@gmail.com',      'Twitter should not exist',         'http://localhost:8080/resources/images/avatars/avatar_02.png'),
    ('tadcabgom',  '$2a$10$b2gaBr6egp2ohlrvzFXGsu73g/d8jZ03pg4qPVN2dAvQEUAj2ah1e', TRUE, 'Tadeo',     'iliketrains@gmail.com',        'What I am suppose to write here?', 'http://localhost:8080/resources/images/avatars/avatar_03.png'),
    ('igngongon2', '$2a$10$wxicoASFzl0uWd/cVWlMi.rlL6CEQfVq4RaepISwUzG8Z5Y8dYZ0.', TRUE, 'Ignacio',   'abcdefghijk@gmail.com',        'Random description',               'http://localhost:8080/resources/images/avatars/avatar_04.png'),
    ('ignarrman',  '$2a$10$BNhD0LI5MUMV6tyae5cAKOShRT4EUD3qJzTrMjJ0F9RcuolpKTVAa', TRUE, 'Nacho',     'manoalzadacocoreto@gmail.com', 'Hot Dog',                          'http://localhost:8080/resources/images/avatars/avatar_05.png'),
    ('jessolort',  '$2a$10$2L.x15N9ZQRw2pckKJRcOOkYyEV9/pjwsLzCBo4oxnO21rvGGLITu', TRUE, 'Jesus',     'randomtext@gmail.com',         'abcde fghi jklmno pqrstu vwxyz',   'http://localhost:8080/resources/images/avatars/avatar_06.png'),
    ('frarosram',  '$2a$10$juzQUREZaBuYU6Uqx/Zvievu4B29zbCwh82Eg06yzHefeW86t1UFy', TRUE, 'Paco',      'testemail@gmail.com',          'Kuak',                             'http://localhost:8080/resources/images/avatars/avatar_00.png'),
    ('fralarmar',  '$2a$10$jjemtc1bmCYD0XfAXzwUUufbeHzRRhfL0EDJgOjE5hjBwhqnw7Ux2', TRUE, 'Javi',      'zxcvbnmgl@gmail.com',          'My hat is my friend',              'http://localhost:8080/resources/images/avatars/avatar_01.png');

INSERT INTO authorities(id,username,authority) VALUES 
    (1,'admin1',     'admin'),
    (2,'user1',      'user'),
    (4,'eletomvel',  'user'),
    (5,'tadcabgom',  'user'),
    (6,'igngongon2', 'user'),
    (7,'ignarrman',  'user'),
    (8,'jessolort',  'user'),
    (9,'frarosram',  'user');



INSERT INTO rooms(id, name, card_image, room_type, treasure, damage, passive_trigger, effect) VALUES

    (1, 'Bottomless Pit',      '/resources/images/rooms/room_00.jpg', 'TRAP', '0001', 1, 'DESTROY_THIS_ROOM','KILL_ONE_HERO_IN_THIS_ROOM'),
    (2, 'The Crushinator',     '/resources/images/rooms/room_01.jpg', 'ADVANCED_TRAP', '0001', 2, 'DESTROY_ANOTHER_ROOM','ADD_2_DAMAGE_TO_EVERY_ROOM'),
    (3, 'Vampire Bordello',    '/resources/images/rooms/room_02.jpg', 'ADVANCED_MONSTER', '0010', 3, 'HERO_DIES_IN_THIS_ROOM','CONVERT_A_WOUND_INTO_A_SOUL'),
    (4, 'Monster Ballroom',    '/resources/images/rooms/room_03.jpg', 'ADVANCED_MONSTER', '0100', 0, 'NONE','NOTHING'),
    (5, 'Boulder Ramp',        '/resources/images/rooms/room_04.jpg', 'TRAP', '0001', 1, 'DESTROY_ANOTHER_ROOM','DEAL_5_DAMAGE_TO_A_HERO_IN_THIS_ROOM'),
    (6, 'Construction Zone',   '/resources/images/rooms/room_05.jpg', 'TRAP', '0101', 1, 'BUILD_THIS_ROOM','BUILD_ANOTHER_ROOM'),
    (7, 'Dark Altar',          '/resources/images/rooms/room_06.jpg', 'TRAP', '0020', 1, 'DESTROY_THIS_ROOM','CHOOSE_CARD_FROM_DISCARD_PILE'),
    (8, 'Dragon Hatchery',     '/resources/images/rooms/room_07.jpg', 'MONSTER', '1111', 0, 'NONE','NOTHING'),
    (9, 'Neanderthal Cave',    '/resources/images/rooms/room_08.jpg', 'MONSTER', '0100', 3, 'NONE','NOTHING'),
    (10, 'Open Grave',         '/resources/images/rooms/room_09.jpg', 'TRAP', '0010', 2, 'HERO_DIES_IN_THIS_ROOM','CHOOSE_ROOM_CARD_FROM_DISCARD_PILE'),
    (11, 'Recycling Center',   '/resources/images/rooms/room_10.jpg', 'ADVANCED_TRAP', '0001', 3, 'DESTROY_ANOTHER_ROOM','DRAW_2_ROOM_CARDS'),
    (12, 'Ligers Den',         '/resources/images/rooms/room_11.jpg', 'ADVANCED_MONSTER', '1000', 2, 'USE_SPELL_CARD','DRAW_A_SPELL_CARD'),
    (13, 'Goblin Armory',      '/resources/images/rooms/room_12.jpg', 'MONSTER', '0200', 1, 'ADD_EXTRA_ROOM_DAMAGE','ADD_1_DAMAGE_TO_ADYACENT_MONSTER_ROOMS'),
    (14, 'Golem Factory',      '/resources/images/rooms/room_13.jpg', 'MONSTER', '0100', 2, 'HERO_DIES_IN_THIS_ROOM','DRAW_A_ROOM_CARD'),
    (15, 'Jackpot Stash',      '/resources/images/rooms/room_14.jpg', 'TRAP', '0002', 1, 'DESTROY_THIS_ROOM','DOUBLE_DUNGEON_TREASURE_VALUE'),
    (16, 'Dark Laboratory',    '/resources/images/rooms/room_15.jpg', 'TRAP', '2000', 1, 'BUILD_THIS_ROOM','DRAW_2_SPELL_CARDS_AND_DISCARD_1_SPELL_CARD'),
    (17, 'Monstrous Monument', '/resources/images/rooms/room_16.jpg', 'TRAP', '0110', 1, 'BUILD_THIS_ROOM','CHOOSE_MONSTER_ROOM_CARD_FROM_DISCARD_PILE'),
    (18, 'Beast Menagerie',    '/resources/images/rooms/room_17.jpg', 'ADVANCED_MONSTER', '0100', 4, 'BUILD_MONSTER_ROOM','DRAW_A_ROOM_CARD'),
    (19, 'Brainsucker Hive',   '/resources/images/rooms/room_18.png', 'MONSTER', '1000', 2, 'HERO_DIES_IN_THIS_ROOM','DRAW_A_SPELL_CARD'),
    (20, 'Dizzygas Hallway',   '/resources/images/rooms/room_19.png', 'TRAP', '0001', 1, 'ADD_EXTRA_ROOM_DAMAGE','ADD_2_DAMAGE_TO_NEXT_ROOM_IF_IT_IS_A_TRAP_ROOM'),
    (21, 'Minotaurs Maze',     '/resources/images/rooms/room_20.png', 'MONSTER', '0100', 0, 'HERO_ENTERS_ROOM','PUSH_HERO_TO_PREVIOUS_ROOM_ONCE'),

    (89, 'Dragon Hatchery',    '/resources/images/rooms/room_07.jpg', 'MONSTER', '1111', 0, 'NONE','NOTHING'),
    (90, 'Golem Factory',      '/resources/images/rooms/room_13.jpg', 'MONSTER', '0100', 2, 'HERO_DIES_IN_THIS_ROOM','DRAW_A_ROOM_CARD'),
    (91, 'Bottomless Pit',     '/resources/images/rooms/room_00.jpg', 'TRAP', '0001', 1, 'DESTROY_THIS_ROOM','KILL_ONE_HERO_IN_THIS_ROOM'),
    (92, 'Dark Laboratory',    '/resources/images/rooms/room_15.jpg', 'TRAP', '2000', 1, 'BUILD_THIS_ROOM','DRAW_2_SPELL_CARDS_AND_DISCARD_1_SPELL_CARD'),
    (93, 'Brainsucker Hive',   '/resources/images/rooms/room_18.png', 'MONSTER', '1000', 2, 'HERO_DIES_IN_THIS_ROOM','DRAW_A_SPELL_CARD');

INSERT INTO spell_card(id, name, card_image, phase,effect) VALUES
    (22,'Giant Size'       ,'/resources/images/spells/spell_00.jpg','constructionPhase'               ,'ADD_3_DAMAGE_TO_A_CHOSEN_MONSTER_ROOM'),
    (23,'Soul Harvest'     ,'/resources/images/spells/spell_01.jpg','adventureAndConstructionPhase','TRADE_A_SOUL_FOR_2_SPELL_CARDS'),
    (24,'Princess in Peril','/resources/images/spells/spell_02.jpg','constructionPhase'            ,'LURE_A_CHOSEN_HERO_FROM_CITY_TO_DUNGEON'),
    (25,'Motivation'       ,'/resources/images/spells/spell_03.jpg','constructionPhase'            ,'BUILD_ANOTHER_ROOM_IF_ANOTHER_PLAYER_HAS_MORE_ROOMS') ,
    (26,'Exhaustion'       ,'/resources/images/spells/spell_04.jpg','adventurePhase'               ,'DEAL_ROOM_AMOUNT_DAMAGE_TO_HERO') ,
    (27,'Annihilator'      ,'/resources/images/spells/spell_05.jpg','constructionPhase'               ,'ADD_3_DAMAGE_TO_A_CHOSEN_TRAP_ROOM'),
    (28,'Cave-in'          ,'/resources/images/spells/spell_06.jpg','adventurePhase'               ,'DESTROY_A_DUNGEON_KILLING_EVERY_HERO_IN_IT'),
    (29,'Kobold Strike'    ,'/resources/images/spells/spell_07.jpg','constructionPhase'            ,'SKIP_BUILD_PHASE'),
    (30,'Teleportation'    ,'/resources/images/spells/spell_08.jpg','adventurePhase'               ,'SEND_HERO_TO_FIRST_ROOM'),
    
    (79,'Giant Size'       ,'/resources/images/spells/spell_00.jpg','adventurePhase'               ,'ADD_3_DAMAGE_TO_A_CHOSEN_MONSTER_ROOM') ,
    (80,'Soul Harvest'     ,'/resources/images/spells/spell_01.jpg','adventureAndConstructionPhase','TRADE_A_SOUL_FOR_2_SPELL_CARDS'),
    (81,'Princess in Peril','/resources/images/spells/spell_02.jpg','constructionPhase'            ,'LURE_A_CHOSEN_HERO_FROM_CITY_TO_DUNGEON'),
    (82,'Motivation'       ,'/resources/images/spells/spell_03.jpg','constructionPhase'            ,'BUILD_ANOTHER_ROOM_IF_ANOTHER_PLAYER_HAS_MORE_ROOMS'),
    (83,'Exhaustion'       ,'/resources/images/spells/spell_04.jpg','adventurePhase'               ,'DEAL_ROOM_AMOUNT_DAMAGE_TO_HERO') ,
    (84,'Annihilator'      ,'/resources/images/spells/spell_05.jpg','adventurePhase'               ,'ADD_3_DAMAGE_TO_A_CHOSEN_TRAP_ROOM'),
    (85,'Cave-in'          ,'/resources/images/spells/spell_06.jpg','adventurePhase'               ,'DESTROY_A_DUNGEON_KILLING_EVERY_HERO_IN_IT'),
    (86,'Kobold Strike'    ,'/resources/images/spells/spell_07.jpg','constructionPhase'            ,'SKIP_BUILD_PHASE'),
    (87,'Teleportation'    ,'/resources/images/spells/spell_08.jpg','adventurePhase'               ,'SEND_HERO_TO_FIRST_ROOM'),
    
    (88,'Jeopardy'         ,'/resources/images/spells/spell_09.jpg','adventureAndConstructionPhase','EVERY_PLAYER_RESETS_THEIR_HAND');

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
    (40,    'Crystol_and_Alan_of_Gerd' ,           '/resources/images/heroes/Crystol_and_Alan_of_Gerd.jpg',        'SWORD', 8, false, 4),
    (41,    'Dartteon,_Elf_Pyromancer' ,            '/resources/images/heroes/Dartteon,_Elf_Pyromancer.jpg',           'BOOK', 8, false, 2),
    (42,    'Delatorious,_Angel_of_Light' ,         '/resources/images/heroes/Delatorious,_Angel_of_Light.jpg',        'CROSS', 8, false, 2),
    (43,    'Fires_Breath,_Heroine_of_Arcadia' , '/resources/images/heroes/Fires_Breath,_Heroine_of_Arcadia.jpg','SWORD', 6, false, 2),
    (44,    'Frankov,_the_Envoy' ,                  '/resources/images/heroes/Frankov,_the_Envoy.jpg',                 'SWORD', 11, true, 2),
    (45,    'Hya,_Legendary_Shinobi' ,              '/resources/images/heroes/Hya,_Legendary_Shinobi.jpg',             'BAG', 11, true, 2),
    (46,    'Jarek,_Squire_to_the_Lion_Knights' ,   '/resources/images/heroes/Jarek,_Squire_to_the_Lion_Knights.jpg',  'SWORD', 4, false, 4),
    (47,    'Jejune_Everlea,_Holy_Sisters' ,    '/resources/images/heroes/Jejune_Everlea,_Holy_Sisters.jpg',   'CROSS', 13, true, 4),
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

INSERT INTO bosses(id, name, card_image, xp, treasure, effect) VALUES
    (72, 'Belladona',         '/resources/images/bosses/boss_00.jpg', 350, 'CROSS','CONVERT_A_WOUND_INTO_A_SOUL'),
    (73, 'The Brothers Wise', '/resources/images/bosses/boss_01.jpg', 775, 'BOOK','CHOOSE_SPELL_CARD_FROM_SPELL_PILE'),
    (74, 'Xyzax',             '/resources/images/bosses/boss_02.jpg', 750, 'CROSS','CHOOSE_2_CARDS_FROM_DISCARD_PILE'),
    (75, 'Cerebellus',        '/resources/images/bosses/boss_03.jpg', 650, 'BOOK','DRAW_3_SPELL_CARDS_AND_DISCARD_1_SPELL_CARD'),
    (76, 'King Croak',        '/resources/images/bosses/boss_04.jpg', 800, 'SWORD','BUILD_AN_ADVANCED_MONSTER_ROOM_CHOSEN_FROM_THE_ROOM_PILE_OR_DISCARD_PILE'),
    (77, 'Seducia',           '/resources/images/bosses/boss_05.jpg', 600, 'BOOK','LURE_A_CHOSEN_HERO_FROM_CITY_OR_HERO_PILE_TO_DUNGEON'),
    (78, 'Cleopatra',         '/resources/images/bosses/boss_06.jpg', 850, 'BAG','BUILD_AN_ADVANCED_TRAP_ROOM_CHOSEN_FROM_THE_ROOM_PILE_OR_DISCARD_PILE');

INSERT INTO lobbies(id, max_players, leader, game_id) VALUES
    (1, 2, 'ignarrman',null),
    (2, 4, 'eletomvel',null),
    (3, 2, 'admin1',null);

INSERT INTO lobby_users(lobby_id,user_id) VALUES
    (1, 'ignarrman'),
    (1, 'tadcabgom'),
    (2, 'frarosram'),
    (2, 'jessolort'),
    (2, 'eletomvel'),
    (2, 'tadcabgom'),
    -- Para comprobar el juego, unirse al lobby 3 como admin1 y "espectador"
    (3, 'eletomvel'),
    (3, 'admin1');

INSERT INTO game_result(id,winner,minutes,date,souls,healths,rounds) VALUES
    (1,'ignarrman', 26, '2018-08-12','2/3/4/10','0/2/2/1',14),
    (2,'ignarrman', 40, '2018-08-14','7/5/8','0/0/2',12),
    (3,'jessolort', 45, '2018-08-15','3/9/10','0/1/2',12),
    (4,'tadcabgom', 21, '2018-08-15','10/7','2/1',14),
    (5,'user1',     101, '2018-08-16','2/3/4/10','0/3/1/2',17),
    (6,'fralarmar', 57, '2018-08-20','7/10/5/8','0/2/2/1',18);

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
    (1, 'Tutorial Monster', 'You won your first game!', '/resources/images/bosses/boss_00.jpg', 1, 'VICTORIES'),
    (2, 'Room Monster', 'You won 5 games!', '/resources/images/bosses/boss_01.jpg', 5, 'VICTORIES'),
    (3, 'Midboss Monster', 'You won 10 games!', '/resources/images/bosses/boss_02.jpg', 10, 'VICTORIES'),
    (4, 'Boss Monster', 'You won 25 games!', '/resources/images/bosses/boss_03.jpg', 25, 'VICTORIES'),
    (5, 'New Monster', 'You played your first game!', '/resources/images/bosses/boss_04.jpg', 1, 'GAMES_PLAYED'),
    (6, 'Old Monster', 'You actually played 100 games... wow...', '/resources/images/bosses/boss_05.jpg', 100, 'GAMES_PLAYED'),
    (7, 'Locked Monster', 'You played games for three hours! Go touch some grass!', '/resources/images/bosses/boss_06.jpg', 180, 'TOTAL_PLAY_TIME');

INSERT INTO achievement_users(username, achievement_id) VALUES
    ('igngongon2', 1),
    ('jessolort', 2),
    ('fralarmar', 1),
    ('tadcabgom', 1),
    ('tadcabgom', 2),
    ('tadcabgom', 3),
    ('eletomvel', 3),
    ('eletomvel', 4),
    ('ignarrman', 1);

INSERT INTO friend_requests(id,accepted, requester, receiver) VALUES
    (0,TRUE, 'ignarrman', 'fralarmar'),
    (1,TRUE, 'ignarrman', 'eletomvel'),
    (2,TRUE, 'ignarrman', 'jessolort'),
    (3,TRUE, 'ignarrman', 'frarosram'),
    (4,TRUE, 'ignarrman', 'igngongon2'),
    (5,FALSE, 'user1', 'ignarrman');
    --(6,FALSE, 'admin1', 'ignarrman');--Fue para comprobar ua cosa

INSERT INTO chats(id) VALUES
    (0);
INSERT INTO messages(id,words,chat,sender) VALUES
    (0,'Esto es un mensaje de prueba en un chat de prueba que no pertenece a ningun juego',0,'jessolort'),
    (1,'2',0,'ignarrman'),
    (2,'3',0,'jessolort'),
    (3,'4',0,'ignarrman');

INSERT INTO invitations(id,user,lobby) VALUES
    (0,'ignarrman',3);
