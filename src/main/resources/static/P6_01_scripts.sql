
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Listage de la structure de la base pour pay_my_buddy
DROP DATABASE IF EXISTS `pay_my_buddy`;
CREATE DATABASE IF NOT EXISTS `pay_my_buddy` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `pay_my_buddy`;

-- Listage de la structure de la table pay_my_buddy. pmb_account
DROP TABLE IF EXISTS `pmb_account`;
CREATE TABLE IF NOT EXISTS `pmb_account` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `account_number` varchar(255) DEFAULT NULL,
  `balance` decimal(19,2) NOT NULL,
  `owner_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_hwyyocqkvvs9w4ayje26kpr4p` (`account_number`),
  KEY `FKcp887hg8arxaw3seaklp79k2j` (`owner_id`),
  CONSTRAINT `FKcp887hg8arxaw3seaklp79k2j` FOREIGN KEY (`owner_id`) REFERENCES `pmb_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Les données exportées n'étaient pas sélectionnées.

-- Listage de la structure de la table pay_my_buddy. pmb_transaction
DROP TABLE IF EXISTS `pmb_transaction`;
CREATE TABLE IF NOT EXISTS `pmb_transaction` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `account_number` varchar(255) DEFAULT NULL,
  `amount` decimal(19,2) NOT NULL,
  `comission` decimal(19,2) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `reference` varchar(255) DEFAULT NULL,
  `transaction_type` varchar(255) DEFAULT NULL,
  `initiator_id` bigint NOT NULL,
  `receiver_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_eat06qqpag5p1rgtlb58gldam` (`reference`),
  KEY `FKam2mau54eex6cmu10oickgwwj` (`initiator_id`),
  KEY `FKcaj2gum10ws2dn31om91s3cx` (`receiver_id`),
  CONSTRAINT `FKam2mau54eex6cmu10oickgwwj` FOREIGN KEY (`initiator_id`) REFERENCES `pmb_user` (`id`),
  CONSTRAINT `FKcaj2gum10ws2dn31om91s3cx` FOREIGN KEY (`receiver_id`) REFERENCES `pmb_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Les données exportées n'étaient pas sélectionnées.

-- Listage de la structure de la table pay_my_buddy. pmb_user
DROP TABLE IF EXISTS `pmb_user`;
CREATE TABLE IF NOT EXISTS `pmb_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Les données exportées n'étaient pas sélectionnées.

-- Listage de la structure de la table pay_my_buddy. pmb_user_contact
DROP TABLE IF EXISTS `pmb_user_contact`;
CREATE TABLE IF NOT EXISTS `pmb_user_contact` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `added` varchar(255) DEFAULT NULL,
  `owner` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Les données exportées n'étaient pas sélectionnées.

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
