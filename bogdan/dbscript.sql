create database shishkin_bogdan_db
character set 'utf8mb4'
collate 'utf8_general_ci';

CREATE USER 'vasya'@'localhost' IDENTIFIED BY '12345';
GRANT ALL PRIVILEGES ON shishkin_bogdan_db.* TO 'vasya'@'localhost'

use shishkin_bogdan_db;

CREATE TABLE `contact_book` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `patronymic` varchar(45) DEFAULT NULL,
  `gender` varchar(45) DEFAULT NULL,
  `marital_status` varchar(45) NOT NULL,
  `website_url` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `job_place` varchar(45) DEFAULT NULL,
  `postal_code` varchar(15) DEFAULT NULL,
  `date_of_birth` date NOT NULL,
  `state` varchar(30) DEFAULT NULL,
  `city` varchar(30) DEFAULT NULL,
  `street` varchar(40) DEFAULT NULL,
  `house_number` varchar(5) DEFAULT NULL,
  `photo_url` varchar(100) DEFAULT NULL,
  `comment` varchar(300) DEFAULT NULL,
  `deleted` bit(1) DEFAULT b'0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_contact_UNIQUE` (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4

CREATE TABLE `phone_book` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `state_code` varchar(5) NOT NULL,
  `operator_code` varchar(5) NOT NULL,
  `number` varchar(10) NOT NULL,
  `phone_type` varchar(20) DEFAULT NULL,
  `comment` varchar(300) DEFAULT NULL,
  `contact_id` int(11) unsigned NOT NULL,
  `deleted` bit(1) DEFAULT b'0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_phone_UNIQUE` (`id`),
  KEY `contact_id_idx` (`contact_id`),
  CONSTRAINT `contact_phone_fk` FOREIGN KEY (`contact_id`) REFERENCES `contact_book` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4

CREATE TABLE `attached_files` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `relative_path` varchar(100) NOT NULL,
  `description` varchar(300) DEFAULT NULL,
  `contact_id` int(11) unsigned NOT NULL,
  `real_path` varchar(100) NOT NULL,
  `deleted` bit(1) DEFAULT b'0',
  `name` varchar(45) DEFAULT NULL,
  `file_type` varchar(30) DEFAULT NULL,
  `add_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `contact_fk_idx` (`contact_id`),
  CONSTRAINT `contact_afile_fk` FOREIGN KEY (`contact_id`) REFERENCES `contact_book` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4

