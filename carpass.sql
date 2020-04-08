-- ************************************** `client`

CREATE TABLE `client`
(
 `id`               int unsigned NOT NULL AUTO_INCREMENT ,
 `name`             varchar(45) NOT NULL ,
 `email`            varchar(60) NOT NULL ,
 `password`         varchar(100) NOT NULL ,
 `token`            varchar(255) NULL ,
 `token_updated_at` datetime NULL ,

PRIMARY KEY (`id`)
);

-- ************************************** `brand`

CREATE TABLE `brand`
(
 `id`   int unsigned NOT NULL AUTO_INCREMENT ,
 `name` varchar(45) NOT NULL ,

PRIMARY KEY (`id`)
);

-- ************************************** `model`

CREATE TABLE `model`
(
 `id`       int unsigned NOT NULL AUTO_INCREMENT ,
 `id_brand` int unsigned NOT NULL ,
 `name`     varchar(60) NOT NULL ,

PRIMARY KEY (`id`),
KEY `fkIdx_108` (`id_brand`),
CONSTRAINT `FK_108` FOREIGN KEY `fkIdx_108` (`id_brand`) REFERENCES `brand` (`id`)
);

-- ************************************** `vehicle`

CREATE TABLE `vehicle`
(
 `id`        int unsigned NOT NULL AUTO_INCREMENT ,
 `plate`     varchar(7) NOT NULL ,
 `id_client` int unsigned NOT NULL ,
 `id_brand`  int unsigned NOT NULL ,
 `id_model`  int unsigned NOT NULL ,
 `year`      int NOT NULL ,
 `renavam`   varchar(11) NOT NULL ,
 `color`     varchar(45) NOT NULL ,

PRIMARY KEY (`id`, `plate`),
KEY `fkIdx_102` (`id_brand`),
CONSTRAINT `FK_102` FOREIGN KEY `fkIdx_102` (`id_brand`) REFERENCES `brand` (`id`),
KEY `fkIdx_112` (`id_model`),
CONSTRAINT `FK_112` FOREIGN KEY `fkIdx_112` (`id_model`) REFERENCES `model` (`id`),
KEY `fkIdx_15` (`id_client`),
CONSTRAINT `FK_15` FOREIGN KEY `fkIdx_15` (`id_client`) REFERENCES `client` (`id`)
);

-- ************************************** `plan`

CREATE TABLE `plan`
(
 `id`    int unsigned NOT NULL AUTO_INCREMENT ,
 `name`  varchar(45) NOT NULL ,
 `value` decimal(9, 2) unsigned NOT NULL ,

PRIMARY KEY (`id`)
);

-- ************************************** `provider`

CREATE TABLE `provider`
(
 `id`               int unsigned NOT NULL AUTO_INCREMENT ,
 `id_plan`          int unsigned NOT NULL ,
 `name`             varchar(100) NOT NULL ,
 `email`            varchar(100) NOT NULL ,
 `password`         varchar(100) NOT NULL ,
 `token`            varchar(255) NULL ,
 `token_updated_at` datetime NULL ,

PRIMARY KEY (`id`),
KEY `fkIdx_82` (`id_plan`),
CONSTRAINT `FK_82` FOREIGN KEY `fkIdx_82` (`id_plan`) REFERENCES `plan` (`id`)
);

-- ************************************** `specialty`

CREATE TABLE `specialty`
(
 `id`   int unsigned NOT NULL AUTO_INCREMENT ,
 `name` varchar(60) NOT NULL ,

PRIMARY KEY (`id`)
);

-- ************************************** `provider_specialty`

CREATE TABLE `provider_specialty`
(
 `id`           int NOT NULL AUTO_INCREMENT ,
 `id_provider`  int unsigned NOT NULL ,
 `id_specialty` int unsigned NOT NULL ,

PRIMARY KEY (`id`),
KEY `fkIdx_72` (`id_specialty`),
CONSTRAINT `FK_72` FOREIGN KEY `fkIdx_72` (`id_specialty`) REFERENCES `specialty` (`id`),
KEY `fkIdx_75` (`id_provider`),
CONSTRAINT `FK_75` FOREIGN KEY `fkIdx_75` (`id_provider`) REFERENCES `provider` (`id`)
);

-- ************************************** `appointment`

CREATE TABLE `appointment`
(
 `id`            int unsigned NOT NULL AUTO_INCREMENT ,
 `id_provider`   int unsigned NOT NULL ,
 `id_vehicle`    int unsigned NOT NULL ,
 `vehicle_plate` varchar(7) NOT NULL ,
 `id_specialty`  int unsigned NOT NULL ,
 `date`          datetime NOT NULL ,
 `cancelled_at`  datetime NULL ,

PRIMARY KEY (`id`),
KEY `fkIdx_30` (`id_vehicle`, `vehicle_plate`),
CONSTRAINT `FK_30` FOREIGN KEY `fkIdx_30` (`id_vehicle`, `vehicle_plate`) REFERENCES `vehicle` (`id`, `plate`),
KEY `fkIdx_40` (`id_provider`),
CONSTRAINT `FK_40` FOREIGN KEY `fkIdx_40` (`id_provider`) REFERENCES `provider` (`id`),
KEY `fkIdx_86` (`id_specialty`),
CONSTRAINT `FK_86` FOREIGN KEY `fkIdx_86` (`id_specialty`) REFERENCES `specialty` (`id`)
);

-- ************************************** `rating`

CREATE TABLE `rating`
(
 `id`          int unsigned NOT NULL AUTO_INCREMENT ,
 `id_provider` int unsigned NOT NULL ,
 `id_client`   int unsigned NOT NULL ,
 `score`       int NOT NULL ,
 `comment`     varchar(140) NULL ,
 `created_at`  datetime NOT NULL ,

PRIMARY KEY (`id`),
KEY `fkIdx_49` (`id_provider`),
CONSTRAINT `FK_49` FOREIGN KEY `fkIdx_49` (`id_provider`) REFERENCES `provider` (`id`),
KEY `fkIdx_52` (`id_client`),
CONSTRAINT `FK_52` FOREIGN KEY `fkIdx_52` (`id_client`) REFERENCES `client` (`id`)
);

-- *********************************************************************************

-- Default values for table `specialty`
INSERT INTO specialty(name)
VALUES
	('Funilaria Geral'),
    ('Pintura'),
    ('Mecânica Geral'),
    ('Borracharia'),
    ('Peças');

-- Default values for table `brand`
INSERT INTO brand(name)
VALUES
	('Chevrolet'),		-- 1
	('Volkswagen'),		-- 2
	('Fiat'),			-- 3
	('Renault'),		-- 4
	('Ford'),			-- 5
	('Toyota'),			-- 6
	('Hyundai'),		-- 7
	('Jeep'),			-- 8
	('Honda'),			-- 9
	('Nissan'),			-- 10
	('Citroen'),		-- 11
	('Mitsubishi'),		-- 12
	('Peugeot'),		-- 13
	('Chery'),			-- 14
	('BMW'),			-- 15
	('Mercedes-Benz'),	-- 16
	('Kia'),			-- 17
	('Audi'),			-- 18
	('Volvo'),			-- 19
	('Land Rover');		-- 20

-- Default values for table `model`
INSERT INTO model(id_brand, name)
VALUES
	(1, 'Agile'),
	(1, 'Astro'),
	(1, 'Blazer'),
	(1, 'Bolt'),
	(1, 'Camaro'),
	(1, 'Captiva'),
	(1, 'Celta'),
	(1, 'Chevette'),
	(1, 'Classic'),
	(1, 'Cobalt'),
	(1, 'Corsa'),
	(1, 'Corvette'),
	(1, 'Equinox'),
	(1, 'HHR'),
	(1, 'Impala'),
	(1, 'Meriva'),
	(1, 'Montana'),
	(1, 'Monza'),
	(1, 'Onix'),
	(1, 'Prisma'),
	(1, 'Spin'),
	(1, 'S10'),
	(1, 'Tracker'),
	(1, 'Vectra'),
	(1, 'Zafira'),
	(2, 'Amarok'),
	(2, 'Brasília'),
	(2, 'Cross Fox'),
	(2, 'Cross Golf'),
	(2, 'Fox'),
	(2, 'Fusca'),
	(2, 'Gol'),
	(2, 'Golf'),
	(2, 'Jetta'),
	(2, 'Kombi'),
	(2, 'Logus'),
	(2, 'New Beetle'),
	(2, 'Parati'),
	(2, 'Passat'),
	(2, 'Polo'),
	(2, 'Santana'),
	(2, 'Saveiro'),
	(2, 'Space Fox'),
	(2, 'T-Cross'),
	(2, 'Taro'),
	(2, 'Tiguan'),
	(2, 'Touareg'),
	(2, 'Touran'),
	(2, 'Trekker'),
	(2, 'Virtus'),
	(2, 'Voyage'),
	(3, '500'),
	(3, 'Argo'),
	(3, 'Brava'),
	(3, 'Bravo'),
	(3, 'Cronos'),
	(3, 'Doblo'),
	(3, 'Elba'),
	(3, 'Fiorino'),
	(3, 'Grand Siena'),
	(3, 'Idea'),
	(3, 'Linea'),
	(3, 'Marea'),
	(3, 'Mobi'),
	(3, 'Palio'),
	(3, 'Palio Weekend'),
	(3, 'Punto'),
	(3, 'Siena'),
	(3, 'Stilo'),
	(3, 'Strada'),
	(3, 'Tempra'),
	(3, 'Toro'),
	(3, 'Uno'),
	(4, 'Captur'),
	(4, 'Clio'),
	(4, 'Duster'),
	(4, 'Fluence'),
	(4, 'Grand Scenic'),
	(4, 'Kwid'),
	(4, 'Latitude'),
	(4, 'Logan'),
	(4, 'Mégane'),
	(4, 'Racoon'),
	(4, 'Sandero'),
	(4, 'Scala'),
	(4, 'Scénic'),
	(4, 'Torino'),
	(5, 'Belina'),
	(5, 'Cargo'),
	(5, 'Del Rey'),
	(5, 'EcoSport'),
	(5, 'Edge'),
	(5, 'Escort'),
	(5, 'Fiesta'),
	(5, 'Fiesta Sedan'),
	(5, 'Focus'),
	(5, 'Fusion'),
	(5, 'Fusion Hybrid'),
	(5, 'Indigo'),
	(5, 'Ka'),
	(5, 'Mustang'),
	(5, 'Ranger'),
	(5, 'Shelby GR-1'),
	(5, 'Sierra'),
	(5, 'Taurus'),
	(5, 'Torino'),
	(6, 'Avalon'),
	(6, 'Corolla'),
	(6, 'Etios'),
	(6, 'Hilux'),
	(6, 'Prius'),
	(6, 'Sienna'),
	(7, 'Atos'),
	(7, 'Creta'),
	(7, 'Elantra'),
	(7, 'HB20'),
	(7, 'HB20S'),
	(7, 'HR'),
	(7, 'i30'),
	(7, 'i800'),
	(7, 'ix20'),
	(7, 'ix35'),
	(7, 'ix55'),
	(7, 'Santa Fe'),
	(7, 'Sonata'),
	(7, 'Tucson'),
	(8, 'Cherokee'),
	(8, 'Commander'),
	(8, 'Compass'),
	(8, 'Grand Cherokee'),
	(8, 'Liberty'),
	(8, 'Renegade'),
	(8, 'Trailhawk'),
	(9, 'City'),
	(9, 'Civic'),
	(9, 'CR-X'),
	(9, 'CR-Z'),
	(9, 'Crosstour'),
	(9, 'Fit'),
	(9, 'HR-V'),
	(9, 'WR-V'),
	(9, 'Zest'),
	(10, '350Z'),
	(10, 'Caravan'),
	(10, 'Frontier'),
	(10, 'Grand Livina'),
	(10, 'GT-R'),
	(10, 'Kicks'),
	(10, 'Livina'),
	(10, 'March'),
	(10, 'Sentra'),
	(10, 'Tiida'),
	(10, 'Titan'),
	(10, 'Versa'),
	(11, 'Aircross'),
	(11, 'C1'),
	(11, 'C3'),
	(11, 'C3 Aircross'),
	(11, 'C4'),
	(11, 'C4 Cactus'),
	(11, 'C4 Lounge'),
	(11, 'C4 SpaceTourer'),
	(11, 'C5'),
	(11, 'Jumper'),
	(12, 'Airtrek'),
	(12, 'Challenger'),
	(12, 'Eclipse'),
	(12, 'Lancer'),
	(12, 'Magna'),
	(12, 'Galloper'),
	(12, 'Outlander'),
	(12, 'Pajero'),
	(12, 'Strada'),
	(12, 'Triton'),
	(13, '106'),
	(13, '107'),
	(13, '204'),
	(13, '205'),
	(13, '206'),
	(13, '207'),
	(13, '208'),
	(13, '304'),
	(13, '305'),
	(13, '306'),
	(13, '307'),
	(13, '308'),
	(13, '403'),
	(13, '404'),
	(13, '405'),
	(13, '406'),
	(13, '407'),
	(13, '408'),
	(13, '504'),
	(13, '505'),
	(13, '508'),
	(13, '604'),
	(13, '605'),
	(13, '607'),
	(13, '2008'),
	(13, '3008'),
	(13, '4007'),
	(13, '4008'),
	(13, '5008'),
	(14, 'Celer'),
	(14, 'Cielo'),
	(14, 'Face'),
	(14, 'QQ'),
	(14, 'S-18'),
	(14, 'Tiggo'),
	(15, '328'),
	(15, '600'),
	(15, 'Compact'),
	(15, 'H2R'),
	(15, 'i3'),
	(15, 'M1'),
	(15, 'M3'),
	(15, 'M5'),
	(15, 'M6'),
	(15, 'Nazca C2'),
	(15, 'Series 3'),
	(15, 'X2'),
	(15, 'X3'),
	(15, 'Z4'),
	(16, 'Classe A Hatch'),
	(16, 'Classe A Sedan'),
	(16, 'Classe C Sedan'),
	(16, 'Classe E Sedan'),
	(16, 'Classe S Sedan'),
	(16, 'Classe C Coupé'),
	(16, 'CLA Coupé'),
	(16, 'CLS Coupé'),
	(16, 'GLC Coupé'),
	(16, 'GLE Coupé'),
	(16, 'GLA SUV'),
	(16, 'GLC SUV'),
	(16, 'SLC Roadster'),
	(17, 'Sportage'),
	(17, 'Sorento'),
	(17, 'Soul'),
	(17, 'Cerato'),
	(17, 'Picanto'),
	(17, 'Mohave'),
	(17, 'Grand Carnival'),
	(17, 'Stinger'),
	(18, 'A3'),
	(18, 'Q3'),
	(18, 'A3 Sedan'),
	(18, 'A4'),
	(18, 'A1'),
	(18, 'A5'),
	(18, 'A4 Avant'),
	(18, 'Q5'),
	(18, 'TT'),
	(18, 'Q7'),
	(18, 'Q8'),
	(18, 'R8'),
	(18, 'A7'),
	(18, 'A6'),
	(19, '440'),
	(19, '460'),
	(19, 'C60'),
	(19, 'ECC'),
	(19, 'V40'),
	(19, 'XC60'),
	(19, 'XC90'),
	(20, '101 Forward Control'),
	(20, 'Defender'),
	(20, 'Discovery'),
	(20, 'Freelander'),
	(20, 'LRX'),
	(20, 'Range Rover'),
	(20, 'Range Rover Autobiography'),
	(20, 'Range Rover Classic'),
	(20, 'Range Rover Evoque'),
	(20, 'Range Rover Sport');

-- Default values for table `plan`
INSERT INTO plan(name, value)
VALUES
	('CarPass 50', 79.90),
	('CarPass 120', 149.90),
	('CarPass 250', 259.90),
	('CarPass Ilimitado', 350.00);



