

INSERT INTO `conference` (`id`, `archived`, `description`, `end`, `location`, `name`, `start`) VALUES
(1, 0, 'Konferenz 1 Beschreibung', '2013-11-29 00:00:00', 'Eschborn', 'Konferenz 1', '2013-11-24 00:00:00'),
(2, 0, 'Konferenz 2 - Beschreibung', '2013-11-22 00:00:00', 'Frankfurt', 'Konferenz 2', '2013-11-17 00:00:00');


INSERT INTO `room` (`id`, `capacity`, `name`) VALUES
(1, 100, 'Raum 1'),
(2, 50, 'Raum 2'),
(3, 25, 'Raum 3');


INSERT INTO `speaker` (`id`, `description`, `firstname`, `lastname`, `profileImage`) VALUES
(1, '', 'Susi', 'Sonnenschein', NULL),
(2, '', 'Leo', 'Laberbacke', NULL),
(3, '', 'Max', 'Mustermann', NULL);


INSERT INTO `talk` (`id`, `description`, `duration`, `start`, `name`, `end`, `conference_id`, `room_id`) VALUES
(1, 'Vortrag 1 - Beschreibung', 60, '2013-11-24 09:00:00', 'Vortrag 1', '2013-11-24 10:00:00', 1, 1),
(2, 'Vortrag 2 - Beschreibung', 60, '2013-11-24 10:15:00', 'Vortrag 2', '2013-11-24 11:15:00', 1, 2),
(3, 'Vortrag 3 - Beschreibung', 60, '2013-11-24 11:30:00', 'Vortrag 2', '2013-11-24 12:30:00', 1, 3),
(4, 'Vortrag 4 - Beschreibung', 60, '2013-11-25 09:00:00', 'Vortrag 3', '2013-11-25 10:00:00', 1, 1),
(5, 'Vortrag 5 - Beschreibung', 60, '2013-11-25 10:15:00', 'Vortrag 4', '2013-11-25 11:15:00', 1, 2),
(6, 'Vortrag 6 - Beschreibung', 60, '2013-11-25 11:30:00', 'Vortrag 5', '2013-11-25 12:30:00', 1, 3);

INSERT INTO `talkspeaker` (`id`, `speaker_id`, `talk_id`) VALUES
(1, 1, 1),
(2, 2, 2),
(3, 3, 2),
(4, 1, 4),
(5, 2, 5),
(6, 2, 6),
(7, 3, 6);
