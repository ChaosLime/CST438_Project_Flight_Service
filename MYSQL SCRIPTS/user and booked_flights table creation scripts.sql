-- Create statement for the user table
CREATE TABLE user (
  id  INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  email     VARCHAR(255)	NOT NULL,
  create_date   DATETIME DEFAULT NOW() NOT NULL,
  password      VARCHAR(255)   NOT NULL,
  last_name     VARCHAR(255),
  first_name    VARCHAR(255),
  zip           CHAR(10),
  UNIQUE INDEX `user_email_UNIQUE` (`email` ASC)
);

-- Create statement for the booked_flights table
CREATE TABLE `booked_flights` (
  `booking_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned DEFAULT NULL,
  `seat_id` int(10) unsigned NOT NULL,
  `canceled` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '0 is false\n1 is true',
  PRIMARY KEY (`booking_id`),
  KEY `user_id_idx` (`user_id`),
  KEY `seat_id_idx` (`seat_id`),
  CONSTRAINT `seat_id` FOREIGN KEY (`seat_id`) REFERENCES `dummy_flight_seat_information` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
