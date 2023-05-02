-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: cicmse
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `asana`
--

DROP TABLE IF EXISTS `asana`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asana` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `token_expiration_time` datetime(6) DEFAULT NULL,
  `token_last_time_used` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnjo9y0frxikkehuo90nca6m6t` (`user_id`),
  CONSTRAINT `FKnjo9y0frxikkehuo90nca6m6t` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asana`
--

LOCK TABLES `asana` WRITE;
/*!40000 ALTER TABLE `asana` DISABLE KEYS */;
INSERT INTO `asana` VALUES (50,1,'2023-05-01 23:07:25.000000','2023-05-01 23:06:25.624007');
/*!40000 ALTER TABLE `asana` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `asana_projects`
--

DROP TABLE IF EXISTS `asana_projects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asana_projects` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `gid` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `resource_type` varchar(255) DEFAULT NULL,
  `asana_workspaces_id` bigint DEFAULT NULL,
  `color` varchar(255) DEFAULT NULL,
  `created_at` tinyblob,
  `current_status` varchar(255) DEFAULT NULL,
  `due_date` tinyblob,
  `is_public` bit(1) NOT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `owner` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1w3g34alx3ymn3exo6nqewo9b` (`asana_workspaces_id`),
  CONSTRAINT `FK1w3g34alx3ymn3exo6nqewo9b` FOREIGN KEY (`asana_workspaces_id`) REFERENCES `asana_workspaces` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asana_projects`
--

LOCK TABLES `asana_projects` WRITE;
/*!40000 ALTER TABLE `asana_projects` DISABLE KEYS */;
INSERT INTO `asana_projects` VALUES (58,'1203195684040604','test2','project',107,'light-green',_binary '2022-10-18 23:57:36.794',NULL,_binary '2022-10-31 23:57:42.528565',_binary '','This is a test','illbal97@gmail.com'),(59,'1203195706343440','TESt','project',107,'light-green',_binary '2022-10-19 00:13:38.447',NULL,_binary '2022-10-25 00:13:43.983232',_binary '','sfafasdf','illbal97@gmail.com'),(60,'1203199540228304','asdfsdf','project',107,'light-green',_binary '2022-10-19 14:37:13.562',NULL,_binary '2022-10-31 14:37:13.323873',_binary '','this is a test','illbal97@gmail.com'),(63,'1203198041896395','test55','project',110,'light-blue',_binary '2022-10-19 15:49:46.152',NULL,_binary '2022-10-24 15:49:45.489986',_binary '','On track was deleted','illbal97@gmail.com'),(64,'1203199938461041','test2','project',110,NULL,NULL,NULL,NULL,_binary '\0',NULL,NULL),(65,'1203199544175804','asd','project',107,NULL,NULL,NULL,NULL,_binary '\0',NULL,NULL),(66,'1203207104768833','illbal9test','project',110,'light-blue',_binary '2022-10-20 13:09:34.288',NULL,_binary '2022-10-24 13:09:34.681565',_binary '','On track','illbal97@gmail.com'),(67,'1203282477419148','saw-12','project',110,'light-green',_binary '2022-11-02 03:05:02.931',NULL,_binary '2022-11-23 03:05:02.380256',_binary '','This is important','illbal97@gmail.com'),(68,'1203295677509231','test','project',110,'light-green',_binary '2022-11-03 17:34:28.275',NULL,_binary '2022-11-29 17:34:27.012786',_binary '','test','illbal97@gmail.com'),(69,'1203297642070668','Test 3','project',110,'light-blue',_binary '2022-11-03 22:25:53.355',NULL,_binary '2022-11-30 22:25:51.014615',_binary '','Test 3','illbal97@gmail.com'),(70,'1204428536929471','tert32w','project',110,'light-green',_binary '2023-04-19 03:04:04.516',NULL,_binary '2023-04-13 03:04:04.649461',_binary '','sdfsfs','illbal97@gmail.com'),(71,'1204428825414259','cvdvsvv12','project',110,'light-blue',_binary '2023-04-19 03:29:51.402',NULL,_binary '2023-05-02 03:29:51.459232',_binary '','afsdfsdf','illbal97@gmail.com'),(72,'1204466016785885','CMSI','project',110,'light-blue',_binary '2023-04-25 00:18:38.52',NULL,_binary '2025-07-03 00:18:38.623629',_binary '','Soo good','illbal97@gmail.com'),(73,'1204466022431763','CMSI_Asana','project',107,'light-green',_binary '2023-04-25 00:21:23.866',NULL,_binary '2023-05-20 00:21:23.991798',_binary '','wefeef','illbal97@gmail.com'),(74,'1204465687450183','FLY-SHT','project',110,'light-blue',_binary '2023-04-25 00:23:28.321',NULL,_binary '2023-04-29 00:23:28.427363',_binary '','erterger','illbal97@gmail.com'),(75,'1204501493690882','AWS-T3','project',107,'light-blue',_binary '2023-04-29 21:27:54.039',NULL,_binary '2023-07-10 21:27:53.397042',_binary '','AWS test project','illbal97@gmail.com');
/*!40000 ALTER TABLE `asana_projects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `asana_section`
--

DROP TABLE IF EXISTS `asana_section`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asana_section` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `gid` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `resource_type` varchar(255) DEFAULT NULL,
  `asana_project_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgij77qxhhkugm2bkebd2wbn85` (`asana_project_id`),
  CONSTRAINT `FKgij77qxhhkugm2bkebd2wbn85` FOREIGN KEY (`asana_project_id`) REFERENCES `asana_projects` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asana_section`
--

LOCK TABLES `asana_section` WRITE;
/*!40000 ALTER TABLE `asana_section` DISABLE KEYS */;
INSERT INTO `asana_section` VALUES (47,'2022-10-29 02:35:10.793000','1203262885331675','To Do','section',65),(48,'2022-10-29 02:35:11.752000','1203262814078229','In Progress','section',65),(49,'2022-10-29 02:35:13.593000','1203262813602015','Done','section',65),(50,'2022-10-29 02:50:58.580000','1203262891304035','To Do','section',59),(51,'2022-10-29 02:50:59.394000','1203262924173212','In Progress','section',59),(52,'2022-10-29 02:51:00.098000','1203262890056898','Done','section',59),(53,'2022-10-29 02:52:49.730000','1203262924627091','To Do','section',63),(54,'2022-10-29 02:52:50.467000','1203262891332620','In Progress','section',63),(55,'2022-10-29 02:52:51.286000','1203262892107101','Done','section',63),(56,'2022-10-29 03:05:54.184000','1203262929731126','To Do','section',66),(57,'2022-10-29 03:05:56.536000','1203262896953141','In Progress','section',66),(58,'2022-10-29 03:05:57.331000','1203262896910345','Done','section',66),(68,'2022-10-30 22:57:42.177000','1203266057857731','To Do','section',64),(69,'2022-10-30 22:57:43.131000','1203266090663649','In Progress','section',64),(70,'2022-10-30 22:57:43.828000','1203266057874834','Done','section',64),(71,'2022-10-30 23:10:27.058000','1203266099649414','To Do','section',58),(72,'2022-10-30 23:10:27.785000','1203266067466156','In Progress','section',58),(73,'2022-10-30 23:10:28.517000','1203266002968368','Done','section',58),(74,'2022-11-01 19:13:43.794000','1203280502656918','To Do','section',60),(75,'2022-11-01 19:13:44.493000','1203280503150615','In Progress','section',60),(76,'2022-11-01 19:13:45.347000','1203280379361180','Done','section',60),(80,'2022-11-02 03:05:09.560000','1203282563153912','To Do','section',67),(81,'2022-11-02 03:05:10.415000','1203282562530028','In Progress','section',67),(82,'2022-11-02 03:05:11.050000','1203282438198387','Done','section',67),(83,'2022-11-03 22:26:04.760000','1203297705205663','To Do','section',69),(84,'2022-11-03 22:26:05.739000','1203297642425130','In Progress','section',69),(85,'2022-11-03 22:26:06.731000','1203297748221451','Done','section',69),(86,'2022-11-29 22:06:47.364000','1203460919472101','To Do','section',68),(87,'2022-11-29 22:06:48.235000','1203460919167498','In Progress','section',68),(88,'2022-11-29 22:06:48.895000','1203460919535263','Done','section',68),(89,'2023-04-24 02:50:28.984000','1204456725193895','To Do','section',71),(90,'2023-04-24 02:50:29.452000','1204456404014629','In Progress','section',71),(91,'2023-04-24 02:50:30.428000','1204456561716879','Done','section',71),(92,'2023-04-25 00:18:47.210000','1204465538174591','To Do','section',72),(93,'2023-04-25 00:18:48.762000','1204465680878697','In Progress','section',72),(94,'2023-04-25 00:18:49.204000','1204466357628734','Done','section',72),(95,'2023-04-25 00:21:27.939000','1204465684951370','To Do','section',73),(96,'2023-04-25 00:21:28.413000','1204466021258468','In Progress','section',73),(97,'2023-04-25 00:21:29.035000','1204466017771126','Done','section',73),(98,'2023-04-25 00:23:32.701000','1204466024104974','To Do','section',74),(99,'2023-04-25 00:23:33.338000','1204465687536796','In Progress','section',74),(100,'2023-04-25 00:23:33.847000','1204465687434837','Done','section',74),(101,'2023-04-29 21:32:21.779000','1204501544598986','To Do','section',75),(102,'2023-04-29 21:32:22.338000','1204501177242398','In Progress','section',75),(103,'2023-04-29 21:32:23.059000','1204501544201509','Done','section',75),(104,'2023-04-30 01:18:44.813000','1204501640828644','To Do','section',70),(105,'2023-04-30 01:18:45.211000','1204501759019226','In Progress','section',70),(106,'2023-04-30 01:18:45.598000','1204501640603255','Done','section',70);
/*!40000 ALTER TABLE `asana_section` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `asana_tasks`
--

DROP TABLE IF EXISTS `asana_tasks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asana_tasks` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `due_date` datetime(6) DEFAULT NULL,
  `gid` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `resource_sub_type` varchar(255) DEFAULT NULL,
  `resource_type` varchar(255) DEFAULT NULL,
  `asana_section_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjekt7lodhi47ev19bnsudt4uo` (`asana_section_id`),
  CONSTRAINT `FKjekt7lodhi47ev19bnsudt4uo` FOREIGN KEY (`asana_section_id`) REFERENCES `asana_section` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asana_tasks`
--

LOCK TABLES `asana_tasks` WRITE;
/*!40000 ALTER TABLE `asana_tasks` DISABLE KEYS */;
INSERT INTO `asana_tasks` VALUES (18,NULL,NULL,'1203266100874365','Reading',NULL,'task',72),(19,NULL,NULL,'1203266100874367','Writting',NULL,'task',73),(22,NULL,NULL,'1203266570340438','sdfg',NULL,'task',71),(26,NULL,NULL,'1203282479130290','reading documentations',NULL,'task',82),(27,NULL,NULL,'1203285923451703','List people',NULL,'task',53),(28,NULL,NULL,'1203295666108726','debug feature',NULL,'task',81);
/*!40000 ALTER TABLE `asana_tasks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `asana_workspaces`
--

DROP TABLE IF EXISTS `asana_workspaces`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asana_workspaces` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email_domains` varchar(255) DEFAULT NULL,
  `is_organization` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `resource_type` varchar(255) DEFAULT NULL,
  `asana_id` bigint DEFAULT NULL,
  `gid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsbr6ly4ujdqlu9ixh8uxnolxw` (`asana_id`),
  CONSTRAINT `FKsbr6ly4ujdqlu9ixh8uxnolxw` FOREIGN KEY (`asana_id`) REFERENCES `asana` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asana_workspaces`
--

LOCK TABLES `asana_workspaces` WRITE;
/*!40000 ALTER TABLE `asana_workspaces` DISABLE KEYS */;
INSERT INTO `asana_workspaces` VALUES (107,'null',_binary '\0','Asana test','workspace',50,'1202213699892899'),(110,'null',_binary '\0','Production','workspace',50,'1203151506851890');
/*!40000 ALTER TABLE `asana_workspaces` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `aws`
--

DROP TABLE IF EXISTS `aws`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `aws` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `token_expiration_time` datetime(6) DEFAULT NULL,
  `token_last_time_used` datetime(6) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6vogtit8ek7vws40q01y8roqi` (`user_id`),
  CONSTRAINT `FK6vogtit8ek7vws40q01y8roqi` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aws`
--

LOCK TABLES `aws` WRITE;
/*!40000 ALTER TABLE `aws` DISABLE KEYS */;
INSERT INTO `aws` VALUES (1,'2023-05-01 23:12:44.000000','2023-05-01 23:11:44.172364',1);
/*!40000 ALTER TABLE `aws` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `aws_ec2_instance`
--

DROP TABLE IF EXISTS `aws_ec2_instance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `aws_ec2_instance` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `instance_id` varchar(255) DEFAULT NULL,
  `instance_type` varchar(255) DEFAULT NULL,
  `security_group_id` varchar(255) DEFAULT NULL,
  `security_group_name` varchar(255) DEFAULT NULL,
  `tag_name` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `aws_account_id` bigint DEFAULT NULL,
  `key_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKecow0teccwvqq43pb8g5rpppb` (`aws_account_id`),
  CONSTRAINT `FKecow0teccwvqq43pb8g5rpppb` FOREIGN KEY (`aws_account_id`) REFERENCES `aws` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aws_ec2_instance`
--

LOCK TABLES `aws_ec2_instance` WRITE;
/*!40000 ALTER TABLE `aws_ec2_instance` DISABLE KEYS */;
INSERT INTO `aws_ec2_instance` VALUES (16,'i-0cdf548abfa734ba5','t2.micro','sg-07efe38ae679ad3ca','default','test2','stopped',1,'QWESDay-test3'),(17,'i-005021a08a359e815','t2.micro','sg-07efe38ae679ad3ca','default','test-ec2','stopped',1,'ASFSEFE-test-2'),(18,'i-075379b8bda87af93','t2.micro','sg-07efe38ae679ad3ca','default','test-ec3','stopped',1,'-asd'),(19,'i-07180861fd37d9d92','t2.micro','sg-07efe38ae679ad3ca','default','test-ec2-3','running',1,'-aws'),(20,'i-0f79d477bd1c5d32a','t2.micro','sg-07efe38ae679ad3ca','default','test-ec2-4','stopped',1,'function getMilliseconds() { [native code] }-love'),(21,'i-0708d533a6a0f4ce0','t2.micro','sg-07efe38ae679ad3ca','default','test-ec2-5','running',1,'741-one');
/*!40000 ALTER TABLE `aws_ec2_instance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gitlab`
--

DROP TABLE IF EXISTS `gitlab`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gitlab` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `token_expiration_time` datetime(6) DEFAULT NULL,
  `token_last_time_used` datetime(6) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKryrrc6rjkryv4qms731hmfhue` (`user_id`),
  CONSTRAINT `FKryrrc6rjkryv4qms731hmfhue` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gitlab`
--

LOCK TABLES `gitlab` WRITE;
/*!40000 ALTER TABLE `gitlab` DISABLE KEYS */;
INSERT INTO `gitlab` VALUES (1,'2023-05-01 23:02:35.000000','2023-05-01 23:01:35.881568',1);
/*!40000 ALTER TABLE `gitlab` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gitlab_branch`
--

DROP TABLE IF EXISTS `gitlab_branch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gitlab_branch` (
  `id` bigint NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `gitlab_project_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKl7scnf5fh061uk0ygpvapxur0` (`gitlab_project_id`),
  CONSTRAINT `FKl7scnf5fh061uk0ygpvapxur0` FOREIGN KEY (`gitlab_project_id`) REFERENCES `gitlab_project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gitlab_branch`
--

LOCK TABLES `gitlab_branch` WRITE;
/*!40000 ALTER TABLE `gitlab_branch` DISABLE KEYS */;
/*!40000 ALTER TABLE `gitlab_branch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gitlab_commit`
--

DROP TABLE IF EXISTS `gitlab_commit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gitlab_commit` (
  `id` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `gitlab_branch_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKayao7j0ckwn93hvnaqvu07g8n` (`gitlab_branch_id`),
  CONSTRAINT `FKayao7j0ckwn93hvnaqvu07g8n` FOREIGN KEY (`gitlab_branch_id`) REFERENCES `gitlab_branch` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gitlab_commit`
--

LOCK TABLES `gitlab_commit` WRITE;
/*!40000 ALTER TABLE `gitlab_commit` DISABLE KEYS */;
/*!40000 ALTER TABLE `gitlab_commit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gitlab_project`
--

DROP TABLE IF EXISTS `gitlab_project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gitlab_project` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `gitlab_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsbbxlgesv2gk9k682vlutlfht` (`gitlab_id`),
  CONSTRAINT `FKsbbxlgesv2gk9k682vlutlfht` FOREIGN KEY (`gitlab_id`) REFERENCES `gitlab` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45608651 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gitlab_project`
--

LOCK TABLES `gitlab_project` WRITE;
/*!40000 ALTER TABLE `gitlab_project` DISABLE KEYS */;
INSERT INTO `gitlab_project` VALUES (29282541,'2021-09-01 10:51:05.974000',NULL,'test',NULL),(29282542,'2021-09-01 10:51:06.622000','Learn how to use GitLab to support your software development life cycle.','Learn GitLab',NULL),(40839370,'2022-11-07 13:16:26.372000',NULL,'test2',NULL),(40844943,'2022-11-07 15:52:01.500000',NULL,'test3',NULL),(41160513,'2022-11-17 19:11:15.390000','this is cool','test5',NULL),(41184219,'2022-11-18 16:13:05.920000','This is important','test777',NULL),(41187899,'2022-11-18 19:21:08.403000','This is a test','test333',NULL),(41187972,'2022-11-18 19:24:33.020000','test is fine','test755',NULL),(41188152,'2022-11-18 19:35:33.700000','test is fine','test5555',NULL),(41254840,'2022-11-22 00:02:35.106000','Cool','test2000',NULL),(45447681,'2023-04-24 22:29:21.334000','asdsa','test77777',NULL);
/*!40000 ALTER TABLE `gitlab_project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (39);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jwt_refresh_token`
--

DROP TABLE IF EXISTS `jwt_refresh_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jwt_refresh_token` (
  `token_id` varchar(255) NOT NULL,
  `creation_date` datetime(6) NOT NULL,
  `expiration_date` datetime(6) NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`token_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jwt_refresh_token`
--

LOCK TABLES `jwt_refresh_token` WRITE;
/*!40000 ALTER TABLE `jwt_refresh_token` DISABLE KEYS */;
INSERT INTO `jwt_refresh_token` VALUES ('c51ab731-3324-4e9d-a0fb-2d739408b4dc','2023-05-01 23:06:11.196743','2023-05-02 00:06:11.196743',1);
/*!40000 ALTER TABLE `jwt_refresh_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `asana_personal_access_token` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  `username` varchar(50) NOT NULL,
  `gitlab_personal_access_token` varchar(255) DEFAULT NULL,
  `aws_access_key` varchar(255) DEFAULT NULL,
  `aws_access_secret_key` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_r43af9ap4edm43mmtq01oddj6` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'46G1pZO937x+Gw5IBun5SbWJNU+Xvim/UYUqQLn4uTGD6YMmbb2fRyqJI8Q6qwoUjZf+HOD96Rn3+DmBtX5wlg==','Illés Bálint','$2a$10$i1C9x2es.307QCXC1egxJOI6w0j8NrT3qXfUIyPcrbslV1Z0eQDQq','ADMIN','illbal9','z+W49mbs1PuBlxBgO5/Utftq+X4k0Q0wwQmrvH4mROA=','npnK1iS9F1WIn8J14zDdzNpPm8jF5VV7AamkyT0FDBg=','yFfFV+6AvUz0S1Ks47jYXULB7/+mcRGMVrlFyli3Hz1XMhZB53UocCXkwU/zUy7k');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'cicmse'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-02  2:43:05
