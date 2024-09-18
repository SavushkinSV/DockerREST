DROP TABLE IF EXISTS class_rooms CASCADE;
DROP TABLE IF EXISTS subjects CASCADE;
DROP TABLE IF EXISTS learners CASCADE;
DROP TABLE IF EXISTS ratings CASCADE;
DROP TABLE IF EXISTS learner_ratings CASCADE;

CREATE TABLE IF NOT EXISTS class_rooms
(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    code VARCHAR (10) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS subjects
(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR (255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS learners
(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    first_name VARCHAR (255) NOT NULL,
    last_name VARCHAR (255) NOT NULL,
    class_id BIGINT REFERENCES class_rooms (id)
);

CREATE TABLE IF NOT EXISTS ratings
(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    data DATE NOT NULL,
    value INT NOT NULL,
    subject_name VARCHAR (255) NOT NULL REFERENCES subjects (name)
);

CREATE TABLE IF NOT EXISTS learner_ratings
(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    learner_id BIGINT REFERENCES learners (id),
    rating_id BIGINT REFERENCES ratings (id)
);

INSERT INTO class_rooms (code)
VALUES ('1а'),
       ('1б'),
       ('1в'),
       ('2а'),
       ('2б');

INSERT INTO subjects (name)
VALUES ('Русский язык'),
       ('Литература'),
       ('Математика'),
       ('Чтение'),
       ('Изобразительное искусство');

INSERT INTO learners (first_name, last_name, class_id)
VALUES ('Ксения', 'Урусова', 1),
       ('Леонид', 'Вергун', 1),
       ('Татьяна', 'Марченко', 2),
       ('Михаил', 'Потапов', 2),
       ('Екатерина', 'Назарова', 3),
       ('Test', 'Test', 3);

INSERT INTO ratings (data, value, subject_name)
VALUES ('01-09-2024', 5, 'Русский язык'),
       ('01-09-2024', 4, 'Математика'),
       ('01-09-2024', 5, 'Чтение'),
       ('01-09-2024', 4, 'Литература'),
       ('02-09-2024', 5, 'Чтение'),
       ('02-09-2024', 5, 'Чтение'),
       ('02-09-2024', 4, 'Изобразительное искусство'),
       ('02-09-2024', 3, 'Русский язык');

INSERT INTO learner_ratings (learner_id, rating_id)
VALUES (1, 1),
       (3, 2),
       (5, 3),
       (3, 4),
       (1, 5),
       (4, 6),
       (4, 7),
       (2, 8);