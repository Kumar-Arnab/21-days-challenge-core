-- create user
DROP USER if exists 'appuser'@'localhost' ;

CREATE USER 'appuser'@'localhost' IDENTIFIED BY 'appuser';

GRANT ALL PRIVILEGES ON * . * TO 'appuser'@'localhost';

-- configure user 'appuser' in your local mysql workbench

-- create schema
CREATE DATABASE  IF NOT EXISTS `app_schema`;
USE `app_schema`;

-- Table structure for table `activity`
DROP TABLE IF EXISTS `activity`;

CREATE TABLE `activity` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `activity_id` VARCHAR(5) NOT NULL,
  `activity_description` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `idx_activity_id` (`activity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf16;

-- insert values for activity
INSERT INTO `activity`(`activity_id`, `activity_description`) VALUES
	('A1', 'Getting up at 5 and practice yoga for 30 minutes'),
    ('A2', 'Jogging for 30 minutes'),
    ('A3', 'House cleaning for 15 minutes'),
    ('A4', 'Take a cold water bath'),
    ('A5', 'Having breakfast at 8'),
    ('A6', 'Having lunch between 1pm-2pm'),
    ('A7', 'Evening Walk for 30 minutes'),
    ('A8', 'Having dinner at 8pm'),
    ('A9', 'Going to bed by 10pm');

-- Table structure for table `contacts`
DROP TABLE IF EXISTS `contacts`;

CREATE TABLE `contacts` (
  `contactid` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(50) NOT NULL,
  `last_name` VARCHAR(50) NOT NULL,
  `accessname` VARCHAR(70) NOT NULL,
  `password`  VARCHAR(70) NOT NULL,
  `problem_description` VARCHAR(500) DEFAULT NULL,
  `active` tinyint NOT NULL DEFAULT 0,
  PRIMARY KEY (`contactid`),
) ENGINE=InnoDB DEFAULT CHARSET=utf16;


-- Table structure for table `contact_activity`
DROP TABLE IF EXISTS `contact_activity`;

CREATE TABLE `contact_activity` (
	`contact_activity_id` INT NOT NULL AUTO_INCREMENT,
    `contactid` INT NOT NULL,
    `activity_ids` VARCHAR(100) DEFAULT NULL,
     PRIMARY KEY (`contact_activity_id`),
     CONSTRAINT `contact_activity_ibfk_1` FOREIGN KEY (`contactid`) REFERENCES `contacts` (`contactid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf16;

-- Table structure for table `contact_daily_activities`
DROP TABLE IF EXISTS `contact_daily_activities`;

CREATE TABLE `contact_daily_activities` (
	`dailyactivityid` INT NOT NULL AUTO_INCREMENT,
    `contactid` INT NOT NULL,
    `date` DATETIME NOT NULL,
    `dayofweek` VARCHAR(10) DEFAULT NULL,
    `month` VARCHAR(10) DEFAULT NULL,
    `day` INT DEFAULT NULL,
    `activitytime` VARCHAR(50) DEFAULT NULL,
    `checkedactivityid` VARCHAR(100) DEFAULT NULL,
    PRIMARY KEY (`dailyactivityid`),
	CONSTRAINT `contact_daily_activities_ibfk_1` FOREIGN KEY (`contactid`) REFERENCES `contacts` (`contactid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf16;

-- Add 2 extra columns to contacts
ALTER TABLE `contacts` ADD COLUMN `user_name` VARCHAR(255) NOT NULL AFTER `contactid`;

ALTER TABLE `contacts` ADD COLUMN `email_verified` TINYINT DEFAULT 0;

ALTER TABLE `contacts` ADD COLUMN `mobile` VARCHAR(50) AFTER `password`;

-- Alter column names
ALTER TABLE `contacts` RENAME COLUMN `accessname` to `access_email`;

ALTER TABLE `contacts` RENAME COLUMN `user_name` to `access_name`;

--
-- default spring security
--
use `app_schema`;

DROP TABLE IF EXISTS `roles`;
DROP TABLE IF EXISTS `members`;

-- Table structure for table `members`
CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(68) NOT NULL,
  `enabled` tinyint NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `authorities` (
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  UNIQUE KEY `authorities_idx_1` (`username`,`authority`),
  CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- insert values for users
INSERT INTO `users` 
VALUES 
('admin','$2a$12$NZ/uppoquEqoVDgnXi911ekZTDmLN9zhiAefODIps9TJ0DjRDWOn2',1),
('test','$2a$12$Jkun5kaz2RAb/TAfhrYVG.XG8zpCk8k7PbKFLrrijQLrEvmkc7rvy',1);

-- insert into authorities
INSERT INTO `authorities` 
VALUES 
('admin','ROLE_ADMIN'),
('test','ROLE_GENERIC');