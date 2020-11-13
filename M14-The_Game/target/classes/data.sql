CREATE DATABASE  IF NOT EXISTS `dicegame`;
USE `dicegame`;

DROP table IF EXISTS `dices`;
DROP TABLE IF EXISTS `players`;

CREATE TABLE `players` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `account_time` datetime NOT NULL,
  `user_name` varchar(250) NOT NULL,
  `password` varchar(250) NOT NULL,
  `succes_rate` double(19,4) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `dices` (
  `id` int(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `roll_time` datetime NOT NULL,
  `dice_roll_1` int(2) NOT NULL,
  `dice_roll_2` int(2) NOT NULL,
  `win` tinyint(1) DEFAULT NULL,

  `player_id` int(50) NOT NULL,
   FOREIGN KEY (`player_id`) REFERENCES `players` (`id`)
);
