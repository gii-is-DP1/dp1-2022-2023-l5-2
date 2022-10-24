-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (2,'owner1','owner');
-- One vet user, named vet1 with passwor v3t
INSERT INTO users(username,password,enabled) VALUES ('vet1','v3t',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (3,'vet1','veterinarian');

INSERT INTO users(username, password, enabled) VALUES ('eletomvel','EleTomas2002', TRUE);
INSERT INTO authorities(id,username,authority) VALUES (4,'eletomvel','owner');

INSERT INTO users(username, password, enabled) VALUES ('tadcabgom','helloimapassword', TRUE);
INSERT INTO authorities(id,username,authority) VALUES (5,'tadcabgom','owner');

INSERT INTO users(username, password, enabled) VALUES ('igngongon2','owner', TRUE);
INSERT INTO authorities(id,username,authority) VALUES (6,'igngongon2','owner');

INSERT INTO users(username,password,enabled) VALUES ('Ignacio', 'password',TRUE);
INSERT INTO authorities(id,username,authority) VALUES(7 ,'Ignacio','owner');

INSERT INTO users(username,password,enabled) VALUES ('jessolort', 'jessolort',TRUE);
INSERT INTO authorities(id,username,authority) VALUES(8,'jessolort','owner');

INSERT INTO users(username,password,enabled) VALUES ('frarosram', 'contra5ena',TRUE);
INSERT INTO authorities(id,username,authority) VALUES(9,'frarosram','owner');

INSERT INTO vets(id, first_name,last_name) VALUES (1, 'James', 'Carter');
INSERT INTO vets(id, first_name,last_name) VALUES (2, 'Helen', 'Leary');
INSERT INTO vets(id, first_name,last_name) VALUES (3, 'Linda', 'Douglas');
INSERT INTO vets(id, first_name,last_name) VALUES (4, 'Rafael', 'Ortega');
INSERT INTO vets(id, first_name,last_name) VALUES (5, 'Henry', 'Stevens');
INSERT INTO vets(id, first_name,last_name) VALUES (6, 'Sharon', 'Jenkins');


INSERT INTO specialties VALUES (1, 'radiology');
INSERT INTO specialties VALUES (2, 'surgery');
INSERT INTO specialties VALUES (3, 'dentistry');

INSERT INTO vet_specialties VALUES (2, 1);
INSERT INTO vet_specialties VALUES (3, 2);
INSERT INTO vet_specialties VALUES (3, 3);
INSERT INTO vet_specialties VALUES (4, 2);
INSERT INTO vet_specialties VALUES (5, 1);

INSERT INTO types VALUES (1, 'cat');
INSERT INTO types VALUES (2, 'dog');
INSERT INTO types VALUES (3, 'lizard');
INSERT INTO types VALUES (4, 'snake');
INSERT INTO types VALUES (5, 'bird');
INSERT INTO types VALUES (6, 'hamster');
INSERT INTO types VALUES (7, 'turtle');

INSERT INTO owners VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023', 'owner1');
INSERT INTO owners VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749', 'owner1');
INSERT INTO owners VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763', 'owner1');
INSERT INTO owners VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198', 'owner1');
INSERT INTO owners VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765', 'owner1');
INSERT INTO owners VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654', 'owner1');
INSERT INTO owners VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387', 'owner1');
INSERT INTO owners VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683', 'owner1');
INSERT INTO owners VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435', 'owner1');
INSERT INTO owners VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487', 'owner1');
INSERT INTO owners VALUES (11, 'Elena', 'Tomas Vela', 'C. Virgen Santisima', 'Enorme sorpresa', '646987746', 'eletomvel');
INSERT INTO owners VALUES (12, 'Tadeo', 'Cabrera', 'C. Sierra Games', 'Ortera', '634865763', 'tadcabgom');
INSERT INTO owners VALUES (13, 'Ignacio', 'González', 'C. Peña Wena', 'Cefirot', '646578964', 'igngongon2');
INSERT INTO owners VALUES (14, 'Ignacio', 'Arroyo', 'Mercedes Benz', 'Bikini Bottom', '112', 'Ignacio');
INSERT INTO owners VALUES (15, 'Jesus', 'Solis Ortega', 'C. de la piruleta 28C', 'Pueblo Paleta', '666777888', 'jessolort');
INSERT INTO owners VALUES (16, 'Francisco', 'Rosso Ramírez', '888 Avenue St.', 'Tarkir', '123456789', 'frarosram');

INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (1, 'Leo', '2010-09-07', 1, 1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (2, 'Basil', '2012-08-06', 6, 2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (3, 'Rosy', '2011-04-17', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (4, 'Jewel', '2010-03-07', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (5, 'Iggy', '2010-11-30', 3, 4);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (6, 'George', '2010-01-20', 4, 5);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (7, 'Samantha', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (8, 'Max', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (9, 'Lucky', '2011-08-06', 5, 7);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (10, 'Mulligan', '2007-02-24', 2, 8);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (11, 'Freddy', '2010-03-09', 5, 9);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (12, 'Lucky', '2010-06-24', 2, 10);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (13, 'Sly', '2012-06-08', 1, 10);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (14, 'Cloti', '2005-02-10', 1, 11);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (15, 'India', '2014-08-08', 2, 12);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (16, 'Andhra', '2019-09-09', 2, 12);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (17, 'Scruffy', '2020-01-28', 2, 12);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (18, 'Haku', '2021-01-10', 2, 12);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (19, 'Enamorado', '2010-05-12', 2, 13);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (20, 'Rocinante','1605-08-12',5, 14);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (21, 'Ambrosio','2020-11-01',3, 16);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (22, 'Splinter','2000-06-15',7, 16);


INSERT INTO visits(id,pet_id,visit_date,description) VALUES (1, 7, '2013-01-01', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (2, 8, '2013-01-02', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (3, 8, '2013-01-03', 'neutered');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (4, 7, '2013-01-04', 'spayed');

INSERT INTO heroes(id, name, cardImage, treassure, health, isEpic, necessaryPlayers) VALUES (1, 'Acacia,_Warrior_of_Light' , 'resources\static\resources\images\Acacia,_Warrior_of_Light.jpg', 'cleric', 6 , false, 2);
INSERT INTO heroes(id, name, cardImage, treassure, health, isEpic, necessaryPlayers) VALUES (2, 'Antonius,_the_Rune_Knight' , 'resources\static\resources\images\Antonius,_the_Rune_Knight.jpg', 'fighter', 13 , true, 2);
INSERT INTO heroes(id, name, cardImage, treassure, health, isEpic, necessaryPlayers) VALUES (3, 'Asmor_the_Aweless' , 'resources\static\resources\images\Asmor_the_Aweless.jpg', 'fighter', 13 , true, 4);
INSERT INTO heroes(id, name, cardImage, treassure, health, isEpic, necessaryPlayers) VALUES (4, 'Blackbeard_Jake' , 'resources\static\resources\images\Blackbeard_Jake.jpg', 'thief', 11 , true, 3);
INSERT INTO heroes(id, name, cardImage, treassure, health, isEpic, necessaryPlayers) VALUES (5, 'Boden_the_Pantless' , 'resources\static\resources\images\Boden_the_Pantless.jpg', 'fighter', 4 , false, 2);
INSERT INTO heroes(id, name, cardImage, treassure, health, isEpic, necessaryPlayers) VALUES (6, 'Brandork_the_Neverwrong' , 'resources\static\resources\images\Brandork_the_Neverwrong.jpg', 'mage', 4 , false, 4);
INSERT INTO heroes(id, name, cardImage, treassure, health, isEpic, necessaryPlayers) VALUES (7, 'Cecil_Leoran,_Master_Factotum' , 'resources\static\resources\images\Cecil_Leoran,_Master_Factotum.jpg', 'thief', 13 , true, 2);
INSERT INTO heroes(id, name, cardImage, treassure, health, isEpic, necessaryPlayers) VALUES (8, 'Charles_the_Young' , 'resources\static\resources\images\Charles_the_Young.jpg', 'cleric', 6 , false, 3);
INSERT INTO heroes(id, name, cardImage, treassure, health, isEpic, necessaryPlayers) VALUES (9, 'Chia_Kang,_Mystical_Warlock_of_Yu' , 'resources\static\resources\images\Chia_Kang,_Mystical_Warlock_of_Yu.jpg', 'mage', 11 , true, 3);
