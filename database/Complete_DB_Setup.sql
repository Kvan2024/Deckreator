

-- database m2_final_project
BEGIN TRANSACTION;

-- *************************************************************************************************
-- Drop all db objects in the proper order
-- *************************************************************************************************
DROP TABLE IF EXISTS users, decks, deck_cards, cards;

-- *************************************************************************************************
-- Create the tables and constraints
-- *************************************************************************************************
-- cards
DROP SEQUENCE IF EXISTS dbnumber;
CREATE SEQUENCE dbnumber;
CREATE TABLE cards (
		dbnumber serial PRIMARY KEY,
        name varchar(1000) NOT NULL,
        color_identity varchar(50) NULL,
        key_words varchar(1000) NULL,
        mana_value int NULL,
        power int NULL,
        toughness int NULL,
        type varchar(100) NULL,
        multiverseid int UNIQUE NOT NULL);

-- users (name is pluralized because 'user' is a SQL keyword)
CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

-- decks
DROP SEQUENCE IF EXISTS deck_number;
CREATE SEQUENCE deck_number;
CREATE TABLE decks(
	deck_number serial PRIMARY KEY,
        name varchar(1000) NOT NULL,
	description varchar(1000) NULL,
	owner varchar(200) NOT NULL,
FOREIGN KEY (owner) REFERENCES users(username) ON DELETE CASCADE);

-- deck_cards
CREATE TABLE deck_cards (
	deck_number INTEGER NOT NULL,
        card int NOT NULL,
		CONSTRAINT fk_deck_number FOREIGN KEY (deck_number) REFERENCES decks(deck_number) ON DELETE CASCADE,
		CONSTRAINT fk_card FOREIGN KEY (card) REFERENCES cards(multiverseid) ON DELETE CASCADE );

-- *************************************************************************************************
-- Insert some sample starting data
-- *************************************************************************************************

-- Users
-- Password for all users is password
INSERT INTO
    users (username, password_hash, role)
VALUES
    ('user', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem','ROLE_USER'),
    ('admin','$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem','ROLE_ADMIN');


-- cards

INSERT INTO cards (dbnumber, name, color_identity, key_words, mana_value, power, toughness, type, multiverseid)

VALUES
    (DEFAULT, 'Ancestor''s Chosen', 'W', '"First strike (This creature deals combat damage before creatures without first strike.) When this creature enters, you gain 1 life for each card in your graveyard."', 7, 4, 4, 'Creature - Human Cleric', 130550),
(DEFAULT, 'Angel of Mercy', 'W', '"Flying When this creature enters, you gain 3 life."', 5, 3, 3, 'Creature - Angel', 129465),
(DEFAULT, 'Angelic Blessing', 'W', 'Target creature gets +3/+3 and gains flying until end of turn. (It can''t be blocked except by creatures with flying or reach.)', 3, null, null, 'Sorcery', 129711),
(DEFAULT, 'Angelic Chorus', 'W', '"Whenever a creature you control enters, you gain life equal to its toughness."', 5, null, null, 'Enchantment', 129710),
(DEFAULT, 'Angelic Wall', 'W', 'Defender (This creature can''t attack.) Flying', 2, 0, 4, null, 0),
(DEFAULT, 'Aven Cloudchaser', 'W', '"Flying (This creature can''t be blocked except by creatures with flying or reach.) When this creature enters, destroy target enchantment."', 4, 2, 2, 'Creature - Bird Soldier', 129470),
(DEFAULT, 'Ballista Squad', 'W', '"{X}{W}, {T}: This creature deals X damage to target attacking or blocking creature."', 4, 2, 2, 'Creature - Human Rebel', 129477),
(DEFAULT, 'Beacon of Immortality', 'W', 'Double target player''s life total. Shuffle Beacon of Immortality into its owner''s library.', 6, null, null, 'Instant', 130553),
(DEFAULT, 'Condemn', 'W', 'Put target attacking creature on the bottom of its owner''s library. Its controller gains life equal to its toughness.', 1, null, null, 'Instant', 130528),
(DEFAULT, 'Field Marshal', 'W', 'Other Soldier creatures get +1/+1 and have first strike. (They deal combat damage before creatures without first strike.)', 3, 2, 2, 'Creature - Human Soldier', 135258),
(DEFAULT, 'Ghost Warden', 'W', '{T}: Target creature gets +1/+1 until end of turn.', 2, 1, 1, 'Creature - Spirit', 132105),
(DEFAULT, 'Hail of Arrows', 'W', 'Hail of Arrows deals X damage divided as you choose among any number of target attacking creatures.', 1, null, null, 'Instant', 132107),
(


-- decks

INSERT INTO decks (deck_number, name, description, owner)

VALUES
(DEFAULT, 'Deck 1', 'This is the first deck', 'user'),
(DEFAULT, 'Deck 2', 'This is the second deck', 'user'),
(DEFAULT, 'Deck 3', 'This is the first admin deck', 'admin'),
(DEFAULT, 'Deck 4', 'This is the first admin deck', 'admin');


COMMIT TRANSACTION;