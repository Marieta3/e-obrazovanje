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
('Prvi domen');

insert into domain_problems (title, description, domain_id)
values
('Prvi domenski problem', 'Opis prvog domenskog problema', 1),
('Drugi domenski problem', 'Opis drugog domenskog problema', 1),
('Treci domenski problem', 'Opis treceg domenskog problema', 1);

insert into knowledge_space_node (domain_problem_id, height, width, coordinate_x, coordinate_y)
values
(1, 150, 150, 10, 10),
(2, 150, 150, 390, 10),
(3, 150, 150, 175, 160);

insert into knowledge_spaces (created_at, title, domain_id)
values
(NOW(), 'Prvi prostor znanja za prvi domen', 1);

insert into links (start_domain_problem_id, end_domain_problem_id, knowledge_space_id)
values
(1, 3, 1),
(3, 2, 1),
(2, 1, 1);

insert into courses (description, name, teacher_id, domain_id)
values
('Prvi kurs koji polazu polaznici, predmet kursa je prepoznavanje', 'Prvi kurs PK', 4, 1), --id 1
('Drugi kurs koji polazu studenti, predmet kursa je klasifikacija', 'Drugi kurs PK', 4, null); --id 2

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


