# ************************************************************
# Sequel Ace SQL dump
# Version 20062
#
# https://sequel-ace.com/
# https://github.com/Sequel-Ace/Sequel-Ace
#
# Host: localhost (MySQL 8.0.28)
# Database: chess
# Generation Time: 2024-04-01 05:43:10 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
SET NAMES utf8mb4;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE='NO_AUTO_VALUE_ON_ZERO', SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table game
# ------------------------------------------------------------

DROP TABLE IF EXISTS `game`;

CREATE TABLE `game` (
  `current_team_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  KEY `fj_team_game` (`current_team_name`),
  CONSTRAINT `fj_team_game` FOREIGN KEY (`current_team_name`) REFERENCES `team` (`team_name`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



# Dump of table piece
# ------------------------------------------------------------

DROP TABLE IF EXISTS `piece`;

CREATE TABLE `piece` (
  `piece_type` varchar(16) NOT NULL,
  PRIMARY KEY (`piece_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `piece` WRITE;
/*!40000 ALTER TABLE `piece` DISABLE KEYS */;

INSERT INTO `piece` (`piece_type`)
VALUES
	('BISHOP'),
	('KING'),
	('KNIGHT'),
	('PAWN'),
	('QUEEN'),
	('ROOK');

/*!40000 ALTER TABLE `piece` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table pieces_on_board
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pieces_on_board`;

CREATE TABLE `pieces_on_board` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `piece_type` varchar(16) NOT NULL,
  `team_name` varchar(16) NOT NULL,
  `position_name` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk-piece-pieces_on_board` (`piece_type`),
  KEY `fk-position-pieces_on_board` (`position_name`),
  KEY `fk-team-pieces_on_board` (`team_name`),
  CONSTRAINT `fk-piece-pieces_on_board` FOREIGN KEY (`piece_type`) REFERENCES `piece` (`piece_type`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk-position-pieces_on_board` FOREIGN KEY (`position_name`) REFERENCES `position` (`name`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk-team-pieces_on_board` FOREIGN KEY (`team_name`) REFERENCES `team` (`team_name`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



# Dump of table position
# ------------------------------------------------------------

DROP TABLE IF EXISTS `position`;

CREATE TABLE `position` (
  `name` char(2) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `position` WRITE;
/*!40000 ALTER TABLE `position` DISABLE KEYS */;

INSERT INTO `position` (`name`)
VALUES
	('A1'),
	('A2'),
	('A3'),
	('A4'),
	('A5'),
	('A6'),
	('A7'),
	('A8'),
	('B1'),
	('B2'),
	('B3'),
	('B4'),
	('B5'),
	('B6'),
	('B7'),
	('B8'),
	('C1'),
	('C2'),
	('C3'),
	('C4'),
	('C5'),
	('C6'),
	('C7'),
	('C8'),
	('D1'),
	('D2'),
	('D3'),
	('D4'),
	('D5'),
	('D6'),
	('D7'),
	('D8'),
	('E1'),
	('E2'),
	('E3'),
	('E4'),
	('E5'),
	('E6'),
	('E7'),
	('E8'),
	('F1'),
	('F2'),
	('F3'),
	('F4'),
	('F5'),
	('F6'),
	('F7'),
	('F8'),
	('G1'),
	('G2'),
	('G3'),
	('G4'),
	('G5'),
	('G6'),
	('G7'),
	('G8'),
	('H1'),
	('H2'),
	('H3'),
	('H4'),
	('H5'),
	('H6'),
	('H7'),
	('H8');

/*!40000 ALTER TABLE `position` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table team
# ------------------------------------------------------------

DROP TABLE IF EXISTS `team`;

CREATE TABLE `team` (
  `team_name` varchar(16) NOT NULL,
  PRIMARY KEY (`team_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `team` WRITE;
/*!40000 ALTER TABLE `team` DISABLE KEYS */;

INSERT INTO `team` (`team_name`)
VALUES
	('black'),
	('white');

/*!40000 ALTER TABLE `team` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
