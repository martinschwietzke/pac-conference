CREATE TABLE IF NOT EXISTS `conference` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `archived` tinyint(1) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end` datetime NOT NULL,
  `location` varchar(75) NOT NULL,
  `name` varchar(75) NOT NULL,
  `start` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8_general_ci AUTO_INCREMENT=0 ;

CREATE TABLE IF NOT EXISTS `room` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `capacity` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8_general_ci AUTO_INCREMENT=0 ;


CREATE TABLE IF NOT EXISTS `speaker` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `firstname` varchar(30) NOT NULL,
  `lastname` varchar(30) NOT NULL,
  `profileImage` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8_general_ci AUTO_INCREMENT=0 ;


CREATE TABLE IF NOT EXISTS `talk` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `duration` int(11) NOT NULL,
  `end` datetime NOT NULL,
  `name` varchar(75) NOT NULL,
  `start` datetime NOT NULL,
  `conference_id` bigint(20) NOT NULL,
  `room_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3634AC8CF3B856` (`room_id`),
  KEY `FK3634ACB72A215` (`conference_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8_general_ci AUTO_INCREMENT=0 ;

CREATE TABLE IF NOT EXISTS `talkspeaker` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `speaker_id` bigint(20) NOT NULL,
  `talk_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK87B9D1D38C2AF162` (`speaker_id`),
  KEY `FK87B9D1D34EAEAC5` (`talk_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8_general_ci AUTO_INCREMENT=0 ;

ALTER TABLE `talk`
  ADD CONSTRAINT `FK3634ACB72A215` FOREIGN KEY (`conference_id`) REFERENCES `conference` (`id`),
  ADD CONSTRAINT `FK3634AC8CF3B856` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`);

ALTER TABLE `talkspeaker`
  ADD CONSTRAINT `FK87B9D1D34EAEAC5` FOREIGN KEY (`talk_id`) REFERENCES `talk` (`id`),
  ADD CONSTRAINT `FK87B9D1D38C2AF162` FOREIGN KEY (`speaker_id`) REFERENCES `speaker` (`id`);

