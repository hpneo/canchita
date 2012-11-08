-- MySQL dump 10.13  Distrib 5.5.28, for debian-linux-gnu (i686)
--
-- Host: localhost    Database: canchita
-- ------------------------------------------------------
-- Server version	5.5.28-0ubuntu0.12.04.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin_users`
--

DROP TABLE IF EXISTS `admin_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin_users` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `EMAIL` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_users`
--

LOCK TABLES `admin_users` WRITE;
/*!40000 ALTER TABLE `admin_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `admin_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genres`
--

DROP TABLE IF EXISTS `genres`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `genres` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genres`
--

LOCK TABLES `genres` WRITE;
/*!40000 ALTER TABLE `genres` DISABLE KEYS */;
INSERT INTO `genres` VALUES (1,'Acción'),(2,'Terror'),(3,'Drama'),(4,'Comedia');
/*!40000 ALTER TABLE `genres` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movies`
--

DROP TABLE IF EXISTS `movies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movies` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `POSTER` varchar(255) DEFAULT NULL,
  `RATING` float DEFAULT NULL,
  `genre_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_movies_genre_id` (`genre_id`),
  CONSTRAINT `FK_movies_genre_id` FOREIGN KEY (`genre_id`) REFERENCES `genres` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movies`
--

LOCK TABLES `movies` WRITE;
/*!40000 ALTER TABLE `movies` DISABLE KEYS */;
INSERT INTO `movies` VALUES (2,'In Istanbul, retired CIA operative Bryan Mills and his wife are taken hostage by the father of a kidnapper Mills killed while rescuing his daughter.','Taken 2','taken-2.jpg',3.5,1),(3,'Secuela','Paranormal Activity 4','paranormal-activity-4.jpg',2.5,2),(4,'Basado en hecho reales','Argo','argo.jpg',5,3),(5,'Del creador de Family Guy','Ted','ted.jpg',3.5,4),(6,'De los creadores de Paranormal Activity','Chernobyl Diaries','chernobyl-diaries.jpg',2.5,2),(7,'Homenaje a los 80s','The Expendables 2','the-expendables-2.jpg',3.5,1),(8,'Película sobrenatural','The Apparition','the-apparition.jpg',2.5,2),(12,'Nick Fury of S.H.I.E.L.D. brings together a team of super humans to form The Avengers to help save the Earth from Loki and his army.','The Avengers','the-avengers.jpg',0,1),(13,'An offbeat romantic comedy about a woman who doesn\'t believe true love exists, and the young man who falls for her.','(500) Days of Summer','500-days-of-summer.jpg',0,3),(14,'Dave Lizewski is an unnoticed high school student and comic book fan who one day decides to become a super-hero, even though he has no powers, training...','Kick-Ass','kick-ass.jpg',0,1);
/*!40000 ALTER TABLE `movies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule_items`
--

DROP TABLE IF EXISTS `schedule_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schedule_items` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `DAY` int(11) DEFAULT NULL,
  `DURATION` int(11) DEFAULT NULL,
  `PRICE` float DEFAULT NULL,
  `start_at` datetime DEFAULT NULL,
  `schedule_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_schedule_items_schedule_id` (`schedule_id`),
  CONSTRAINT `FK_schedule_items_schedule_id` FOREIGN KEY (`schedule_id`) REFERENCES `schedules` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule_items`
--

LOCK TABLES `schedule_items` WRITE;
/*!40000 ALTER TABLE `schedule_items` DISABLE KEYS */;
INSERT INTO `schedule_items` VALUES (1,1,115,16,'2012-11-03 16:00:00',1),(2,2,115,18,'2012-11-03 16:30:00',1);
/*!40000 ALTER TABLE `schedule_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedules`
--

DROP TABLE IF EXISTS `schedules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schedules` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `MOVIE_ID` int(11) DEFAULT NULL,
  `start_at` date DEFAULT NULL,
  `end_at` date DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_schedules_MOVIE_ID` (`MOVIE_ID`),
  CONSTRAINT `FK_schedules_MOVIE_ID` FOREIGN KEY (`MOVIE_ID`) REFERENCES `movies` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedules`
--

LOCK TABLES `schedules` WRITE;
/*!40000 ALTER TABLE `schedules` DISABLE KEYS */;
INSERT INTO `schedules` VALUES (1,2,'2012-11-05','2012-11-11'),(2,14,'2012-11-05','2012-11-11');
/*!40000 ALTER TABLE `schedules` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tickets`
--

DROP TABLE IF EXISTS `tickets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tickets` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `QUANTITY` int(11) DEFAULT NULL,
  `schedule_item_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `bought_at` date DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_tickets_schedule_item_id` (`schedule_item_id`),
  KEY `FK_tickets_user_id` (`user_id`),
  CONSTRAINT `FK_tickets_schedule_item_id` FOREIGN KEY (`schedule_item_id`) REFERENCES `schedule_items` (`ID`),
  CONSTRAINT `FK_tickets_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tickets`
--

LOCK TABLES `tickets` WRITE;
/*!40000 ALTER TABLE `tickets` DISABLE KEYS */;
INSERT INTO `tickets` VALUES (34,1,1,1,'2012-11-05'),(40,1,1,1,'2012-11-07'),(41,1,1,1,'2012-11-07'),(42,1,1,1,'2012-11-07'),(43,1,1,1,'2012-11-07'),(44,1,1,1,'2012-11-07'),(45,1,1,1,'2012-11-07'),(46,1,1,1,'2012-11-07'),(48,1,1,1,'2012-11-08'),(51,1,1,1,'2012-11-08'),(52,1,1,1,'2012-11-08'),(53,1,1,1,'2012-11-08'),(54,1,1,1,'2012-11-08'),(55,1,1,1,'2012-11-08'),(56,1,1,1,'2012-11-08');
/*!40000 ALTER TABLE `tickets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `DNI` varchar(255) DEFAULT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `POINTS` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,NULL,'gustavo@xenda.pe','Gustavo Leon','54ac481a4f493b020de196fa4e4a945e',6);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-11-08 14:50:37
