-- You can use this file to load seed data into the database using SQL statements
insert into Room(id, name, capacity) values (1, 'Raum 1', 50) 
insert into Room(id, name, capacity) values (2, 'Raum 2', 100) 
insert into Room(id, name, capacity) values (3, 'Raum 3', 75) 
insert into Room(id, name, capacity) values (4, 'Raum 4', 75) 

insert into speaker(id, firstname, lastname) values (1, 'Max', 'Mustermann') 
insert into speaker(id, firstname, lastname) values (2, 'Susi', 'Sonnenschein') 
insert into speaker(id, firstname, lastname) values (3, 'Leo', 'Laberbacke') 

insert into Conference(id, name, start, end, archived, location) values (1, 'Konferenz 1', '2013-11-01 00:00:00', '2013-11-14 00:00:00', 0, 'Eschborn')
insert into Conference(id, name, start, end, archived, location) values (2, 'Konferenz 2', '2013-10-03 00:00:00' , '2013-10-08 00:00:00', 0, 'Wolfsburg') 
insert into Conference(id, name, start, end, archived, location) values (3, 'Konferenz 3', '2013-10-06 00:00:00', '2013-10-11 00:00:00', 0, 'München') 

insert into Talk(id, name, duration, room_id, conference_id, description, start, end) values (1, 'Talk 1', 60, 1, 1, 'Description 1', '2013-10-01 09:00:00', '2013-10-01 09:00:00') 
insert into Talk(id, name, duration, room_id, conference_id, description, start, end) values (2, 'Talk 2', 120, 2, 1, 'Description 2', '2013-10-01 09:00:00', '2013-10-01 12:00:00') 
insert into Talk(id, name, duration, room_id, conference_id, description, start, end) values (3, 'Talk 3', 180, 3, 1, 'Description 3', '2013-10-01 10:00:00', '2013-10-01 12:00:00') 
insert into Talk(id, name, duration, room_id, conference_id, description, start, end) values (4, 'Talk 4', 180, 3, 1, 'Description 3', '2013-10-02 09:00:00', '2013-10-01 12:00:00') 
insert into Talk(id, name, duration, room_id, conference_id, description, start, end) values (5, 'Talk 5', 180, 1, 1, 'Description 3', '2013-10-02 11:00:00', '2013-10-01 12:00:00')

insert into talkspeaker (speaker_id, talk_id) values (1, 1);
insert into talkspeaker (speaker_id, talk_id) values (2, 1);
insert into talkspeaker (speaker_id, talk_id) values (2, 2);
insert into talkspeaker (speaker_id, talk_id) values (3, 3);
insert into talkspeaker (speaker_id, talk_id) values (3, 4);

