CREATE DATABASE IF NOT EXISTS private_ads_placement_system COLLATE = utf8_general_ci;

USE private_ads_placement_system;

CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `surname` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `balance` decimal(12,2) DEFAULT '0.00',
  `rating` decimal(12,2) DEFAULT '0.00',
  `reset_password_token` varchar(30) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `user_role` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `fk_users_has_role_role1_idx` (`role_id`),
  KEY `fk_users_has_role_users1_idx` (`user_id`),
  CONSTRAINT `fk_users_has_role_role1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `fk_users_has_role_users1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `advertisement` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `seller_id` bigint NOT NULL,
  `customer_id` bigint DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `price` decimal(12,2) DEFAULT '0.00',
  `status` enum('OPEN','CLOSED') DEFAULT 'OPEN',
  `created_at` datetime DEFAULT NULL,
  `is_paid` tinyint(1) DEFAULT '0',
  `paid_till` datetime DEFAULT NULL,
  `paid_sum` decimal(12,2) DEFAULT '0.00',
  `sold_at` datetime DEFAULT NULL,
  `sold_sum` decimal(12,2) DEFAULT '0.00',
  PRIMARY KEY (`id`),
  KEY `fk_advertisments_users_idx` (`seller_id`),
  KEY `fk_advertisments_users1_idx` (`customer_id`),
  CONSTRAINT `fk_advertisments_users` FOREIGN KEY (`seller_id`) REFERENCES `users` (`id`),
  CONSTRAINT `fk_advertisments_users1` FOREIGN KEY (`customer_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `comments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `advertisement_id` bigint NOT NULL,
  `comment` varchar(2000) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_comments_users1_idx` (`user_id`),
  KEY `fk_comments_advertisments1_idx` (`advertisement_id`),
  CONSTRAINT `fk_comments_advertisments1` FOREIGN KEY (`advertisement_id`) REFERENCES `advertisement` (`id`),
  CONSTRAINT `fk_comments_users1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `message` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `sender_id` bigint NOT NULL,
  `receiver_id` bigint NOT NULL,
  `message` varchar(1000) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_messages_users1_idx` (`sender_id`),
  KEY `fk_message_users1_idx` (`receiver_id`),
  CONSTRAINT `fk_message_users1` FOREIGN KEY (`receiver_id`) REFERENCES `users` (`id`),
  CONSTRAINT `fk_messages_users1` FOREIGN KEY (`sender_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `seller_evaluation` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `customer_id` bigint NOT NULL,
  `advertisement_id` bigint NOT NULL,
  `evaluation` tinyint(1) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_seller_evaluations_users1_idx` (`customer_id`),
  KEY `fk_seller_evaluations_advertisments1_idx` (`advertisement_id`),
  CONSTRAINT `fk_seller_evaluations_advertisments1` FOREIGN KEY (`advertisement_id`) REFERENCES `advertisement` (`id`),
  CONSTRAINT `fk_seller_evaluations_users1` FOREIGN KEY (`customer_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

