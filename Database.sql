-- --------------------------------------------------------
-- Hôte :                        127.0.0.1
-- Version du serveur:           5.7.10-log - MySQL Community Server (GPL)
-- SE du serveur:                Win64
-- HeidiSQL Version:             9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Export de la structure de la base pour bdd_jweb
CREATE DATABASE IF NOT EXISTS `bdd_jweb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `bdd_jweb`;


-- Export de la structure de table bdd_jweb. news
CREATE TABLE IF NOT EXISTS `news` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `content` text NOT NULL,
  `author` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK__user` (`author`),
  CONSTRAINT `FK__user` FOREIGN KEY (`author`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Export de données de la table bdd_jweb.news : ~2 rows (environ)
DELETE FROM `news`;
/*!40000 ALTER TABLE `news` DISABLE KEYS */;
INSERT INTO `news` (`id`, `title`, `content`, `author`) VALUES
	(1, 'First news', 'This is the first news.', 4),
	(2, 'Second news', 'This is the second news.', 4),
	(3, 'Bonjour', 'Salut billy', 4);
/*!40000 ALTER TABLE `news` ENABLE KEYS */;


-- Export de la structure de table bdd_jweb. user
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(300) NOT NULL,
  `login` varchar(300) NOT NULL,
  `password` varchar(300) NOT NULL,
  `isadm` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Index 2` (`email`,`login`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Export de données de la table bdd_jweb.user : ~5 rows (environ)
DELETE FROM `user`;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `email`, `login`, `password`, `isadm`) VALUES
	(1, 'lol@mdr.fr', 'frrr', 'lolilol', 0),
	(2, 'mdr@lol.fr', 'btrrrrrr', 'krere', 0),
	(3, 'moi@ptdr.com', 'YOLO', 'popopo', 0),
	(4, 'alaric.degand@epitech.eu', 'Admin', '123456789', 1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
