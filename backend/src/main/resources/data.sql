insert into authority (id, role) values (1,'ROLE_STUDENT'), (2,'ROLE_TEACHER'), (3,'ROLE_ADMIN');

insert into users (user_type,first_name,last_name,username,password,enabled)
values
('student','Marko','Markovic','mare021','$2a$10$kMLJl/fG/j6OHIPLKUC8fetWtt1cXv8fgJTOpOLRSQcotxsmyCDJy',TRUE), -- sifra 123, id 1
('student','Petar','Petrovic','pero','$2a$10$GQHFBBU1gG8y0W5uT7zFsO90TayiQ7ALfMlWlipIttUCKIalP0qli',TRUE),    -- id 2
('admin','Marta','Martic','martaa','$2a$10$GwmzvbZUxoDR.4tlCLauve7C7qKtDR1q15OcFKCGM4FcT/f5q/H1.',TRUE),      -- id 3
('teacher','Eva','Ras','eva','$2a$10$nzYuqq4AvIAH42I.f.jQDuL0247GwUFU5yF2SiSl.o5qFeU4z95DC',TRUE);            -- id 4

insert into user_authority (user_id, authority_id)
values (1,1),(2,1),(3,3),(4,2) ;

insert into courses (description, name, teacher_id)
values
('Prvi kurs koji polazu polaznici, predmet kursa je prepoznavanje', 'Prvi kurs PK', 4); --id 1

insert into tests(title, course_id)
values
('Prvi test', 1); -- id 1

insert into questions (is_randomize, points, text, test_id)
values
(false, 3, 'Koji odgovor je tacan?', 1); -- id 1

insert into answers (image_path, is_correct, text, question_id)
values
(null, false, 'prvi', 1),    -- id 1
(null, false, 'drugi', 1),   -- id 2
(null, true, 'treci', 1);    -- id 3