insert into authority (id, role) values (1,'ROLE_STUDENT'), (2,'ROLE_TEACHER'), (3,'ROLE_ADMIN');

insert into users (user_type,first_name,last_name,username,password,enabled,index_number)
values
('student','Marko','Markovic','mare021','$2a$10$kMLJl/fG/j6OHIPLKUC8fetWtt1cXv8fgJTOpOLRSQcotxsmyCDJy',TRUE, 'R2-123-2021'), -- sifra 123, id 1
('student','Petar','Petrovic','pero','$2a$10$GQHFBBU1gG8y0W5uT7zFsO90TayiQ7ALfMlWlipIttUCKIalP0qli',TRUE, 'R2-456-2021');    -- id 2

insert into users (user_type,first_name,last_name,username,password,enabled)
values
('admin','Marta','Martic','martaa','$2a$10$GwmzvbZUxoDR.4tlCLauve7C7qKtDR1q15OcFKCGM4FcT/f5q/H1.',TRUE),      -- id 3
('teacher','Eva','Ras','eva','$2a$10$nzYuqq4AvIAH42I.f.jQDuL0247GwUFU5yF2SiSl.o5qFeU4z95DC',TRUE),            -- id 4
('teacher','sdkfs','adsa','ddd','$2a$10$nzYuqq4AvIAH42I.f.jQDuL0247GwUFU5yF2SiSl.o5qFeU4z95DC',TRUE);         -- id 5


insert into user_authority (user_id, authority_id)
values (1,1),(2,1),(3,3),(4,2),(5,2) ;

insert into domains (title)
values
('Prvi domen'),
('nebino');

insert into domain_problems (description, title, domain_id)
values
('Opis prvog domenskog problema', 'Prvi domenski problem', 1),
('Opis drugog domenskog problema', 'Drugi domenski problem', 1),
('Opis treceg domenskog problema', 'Treci domenski problem', 1),
('2',	'2',	2),
('4',	'4',	2),
('3',	'3',	2),
('1',	'1',	2),
('8',	'8',	2),
('6',	'6',	2),
('5',	'5',	2),
('9',	'9',	2),
('7',	'7',	2);

insert into knowledge_space_node (coordinate_x, coordinate_y, height, width, domain_problem_id)
values
(10, 10, 150, 150, 1),
(390, 10, 150, 150, 2),
(175, 160, 150, 150, 3),
(575.5,	56,	150,	150,	9),
(297.5,	111,	150,	150,	12),
(570.5,	289,	150,	150,	7),
(911.5,	-28,	150,	150,	10),
(74.5,	-6,	150,	150,	6),
(-141.5,	-3,	150,	150,	5),
(297.5,	-99,	150,	150,	4),
(555.5,	-200,	150,	150,	8),
(1161.5,	-20,	150,	150,	11),
(1059.5,	-245,	150,	150,	5),
(709.5,	169,	150,	150,	12),
(465.5,	-347,	150,	150,	6),
(-148.5,	111,	150,	150,	4),
(227.5,	36,	150,	150,	11),
(236.5,	315,	150,	150,	10),
(469.5,	170,	150,	150,	8),
(76.5,	-258,	150,	150,	7),
(1133.5,	9,	150,	150,	9);

insert into knowledge_spaces (created_at, title, domain_id)
values
(NOW(), 'Prvi prostor znanja za prvi domen', 1),
('2021-12-15 01:12:21.233',	'Prostor znanja za drugi domen',	2),
('2021-12-15 01:12:41.475',	'iita prostor za drugi domen',	2);

insert into links (end_domain_problem_id, knowledge_space_id, start_domain_problem_id)
values
(3, 1, 1),
(2, 1, 3),
(1, 1, 2),
(4,	2,	5),
(6,	2,	5),
(7,	2,	6),
(5,	2,	8),
(8,	2,	9),
(10,	2,	8),
(11,	2,	10),
(7,	2,	11),
(7,	2,	4),
(12,	2,	7),
(15,	3,	17),
(20,	3,	16),
(19,	3,	16),
(15,	3,	16),
(13,	3,	14),
(21,	3,	14),
(18,	3,	16),
(19,	3,	17),
(13,	3,	17);

insert into courses (description, name, teacher_id, domain_id)
values
('Prvi kurs koji polazu polaznici, predmet kursa je prepoznavanje', 'Prvi kurs PK', 4, 1), --id 1
('Drugi kurs koji polazu studenti, predmet kursa je klasifikacija', 'Drugi kurs PK', 4, 2); --id 2

insert into tests(title, course_id)
values
('Prvi test', 1); -- id 1

insert into questions (is_randomize, text, test_id, domain_problem_id)
values
(false, 'Koji odgovor je tacan?', 1, 1); -- id 1

insert into answers (image_path, is_correct, text, question_id, accuracy)
values
(null, false, 'prvi', 1, -100),    -- id 1
(null, false, 'drugi', 1, -100),   -- id 2
(null, true, 'treci', 1, 100);    -- id 3

insert into students_courses (completed, course_id, student_id, student_index)
values
(false, 1, 1,'R2-123-2021'),
(true, 1, 2,'R2-456-2021');

