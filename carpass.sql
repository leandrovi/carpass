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

-- ************************************** `vehicle`

CREATE TABLE `vehicle`
(
 `id`        int unsigned NOT NULL AUTO_INCREMENT ,
 `plate`     varchar(7) NOT NULL ,
 `id_client` int unsigned NOT NULL ,
 `year`      int unsigned NOT NULL ,
 `renavam`   varchar(11) NOT NULL ,
 `brand`     varchar(45) NOT NULL ,
 `model`     varchar(45) NOT NULL ,
 `color`     varchar(45) NOT NULL ,

PRIMARY KEY (`id`, `plate`),
KEY `fkIdx_15` (`id_client`),
CONSTRAINT `FK_15` FOREIGN KEY `fkIdx_15` (`id_client`) REFERENCES `client` (`id`)
);

-- ************************************** `plan`

CREATE TABLE `plan`
(
 `id`    int unsigned NOT NULL AUTO_INCREMENT ,
 `name`  varchar(45) NOT NULL ,
 `value` decimal unsigned NOT NULL ,

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