DROP TABLE IF EXISTS classes CASCADE;
DROP TABLE IF EXISTS subjects CASCADE;
DROP TABLE IF EXISTS learners CASCADE;
DROP TABLE IF EXISTS ratings CASCADE;

CREATE TABLE IF NOT EXISTS classes
(
    class_id   BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    class_code   VARCHAR(10) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS subjects
(
    subject_id   BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    subject_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS learners
(
    learner_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    first_name  VARCHAR(255) NOT NULL,
    last_name   VARCHAR(255) NOT NULL,
    class_code  VARCHAR(10) REFERENCES classes (class_code)
);

CREATE TABLE IF NOT EXISTS ratings
(
    rating_id       BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    rating_data     DATE NOT NULL,
    rating_value    INT NOT NULL,
    subject_name    VARCHAR(255) NOT NULL REFERENCES subjects (subject_name),
    learner_id     BIGINT NOT NULL REFERENCES learners (learner_id)
);

INSERT INTO classes (class_code)
VALUES  ('1а'),
        ('1б'),
        ('1в'),
        ('2а'),
        ('2б');

INSERT INTO subjects (subject_name)
VALUES  ('Русский язык'),
        ('Литература'),
        ('Математика'),
        ('Чтение'),
        ('Изобразительное искусство');

INSERT INTO learners (first_name, last_name, class_code)
VALUES  ('Ксения', 'Урусова', '1а'),
        ('Леонид', 'Вергун', '1а'),
        ('Татьяна', 'Марченко', '1б'),
        ('Михаил', 'Потапов', '1б'),
        ('Екатерина', 'Назарова', '2а');

INSERT INTO ratings (rating_data, rating_value, subject_name, learner_id)
VALUES  ('01-09-2024', 5, 'Русский язык', 1),
        ('01-09-2024', 4, 'Математика', 3),
        ('01-09-2024', 5, 'Чтение', 5),
        ('01-09-2024', 4, 'Литература', 3),
        ('02-09-2024', 5, 'Чтение', 1),
        ('02-09-2024', 5, 'Чтение', 4),
        ('02-09-2024', 4, 'Изобразительное искусство', 4),
        ('02-09-2024', 3, 'Русский язык', 2);


