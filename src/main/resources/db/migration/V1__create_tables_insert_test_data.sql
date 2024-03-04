
DROP TABLE IF EXISTS notes;
CREATE TABLE notes (id BIGSERIAL,
                    text varchar(5000),
                    publication_date TIMESTAMP,
                    change_date TIMESTAMP,
                    primary key (id));


INSERT INTO notes(text, publication_date, change_date)
         VALUES ('Первая тестовая запись в заметках', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

