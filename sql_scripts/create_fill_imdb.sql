/* Author: C. Schulze */
/* JDBC 2 - Aufgabe 1b */

/* Tabelle der Entit�t Genre */
CREATE TABLE Genre (
                       GenreID INTEGER NOT NULL,
                       Genre VARCHAR(30) NOT NULL,

                       CONSTRAINT pk_genre PRIMARY KEY (GenreID)
);
/* Tabelle der Entit�t Movie */
CREATE TABLE Movie (
                       MovieID INTEGER NOT NULL,
                       Title VARCHAR(60) NOT NULL,
                       Year INTEGER NOT NULL,
                       Type CHAR NOT NULL,

                       CONSTRAINT pk_movie PRIMARY KEY (MovieID)
);
/* Tabelle der Entit�t Genre */
CREATE TABLE PERSON (
                        PersonID INTEGER NOT NULL,
                        Name VARCHAR(30) NOT NULL,
                        Sex CHAR,

                        CONSTRAINT pk_person PRIMARY KEY (PersonID)
);

/* Tabelle MovieCharacter, Beziehung zwischen Movie und Person*/
CREATE TABLE MovieCharacter (
                                MovCharID INTEGER NOT NULL,
                                Character VARCHAR(30) NOT NULL,
                                Alias VARCHAR (30),
                                Position INTEGER,
                                Plays INTEGER NOT NULL,
                                hasCharacter INTEGER NOT NULL,

                                CONSTRAINT pk_movchar PRIMARY KEY (MovCharID),
                                CONSTRAINT fk_movchar_movie FOREIGN KEY (hasCharacter) REFERENCES Movie (MovieID) ON DELETE CASCADE,
                                CONSTRAINT fk_movchar_person FOREIGN KEY (plays) REFERENCES Person (PersonID) ON DELETE CASCADE
);


/* Tabelle hasGenre, Beziehung zwischen Movie und Genre */
CREATE TABLE hasGenre (
                          MovieID INTEGER NOT NULL,
                          GenreID INTEGER NOT NULL,

                          CONSTRAINT pk_hasgenre PRIMARY KEY (MovieID, GenreID),
                          CONSTRAINT fk_movie_hasgenre FOREIGN KEY (MovieID) REFERENCES Movie (MovieID) ON DELETE CASCADE,
                          CONSTRAINT fk_genre_hasgenre FOREIGN KEY (GenreID) REFERENCES Genre (GenreID) ON DELETE CASCADE
);


/* JDBC 2 - Aufgabe 1c */
ALTER TABLE Person ADD UNIQUE (Name);

ALTER TABLE Genre ADD UNIQUE (Genre);


/* JDBC 2 - Aufgabe 1d */
/* Sequenzen f�r jeweilige, gleichnamige Tabelle */
CREATE SEQUENCE genre_id START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE person_id START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE movie_id START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE movchar_id START WITH 1 INCREMENT BY 1;

/* Beispieldaten (gerne beliebig erweitern) */

INSERT INTO Genre VALUES (genre_id.NextVal,'Action');
INSERT INTO Genre VALUES (genre_id.NextVal,'Drama');
INSERT INTO Genre VALUES (genre_id.NextVal,'Horror');
INSERT INTO Genre VALUES (genre_id.NextVal,'Comedy');


INSERT INTO Person VALUES (person_id.NextVal,'Mark Meyer','M');
INSERT INTO Person VALUES (person_id.NextVal,'Laura Liese','W');
INSERT INTO Person VALUES (person_id.NextVal,'Kai Klausen','M');
INSERT INTO Person VALUES (person_id.NextVal,'Sofie Schultz','W');


INSERT INTO Movie VALUES (movie_id.NextVal,'Der Pate', 1972 ,'F');
INSERT INTO Movie VALUES (movie_id.NextVal,'PSYCHO', 1960 ,'F');
INSERT INTO Movie VALUES (movie_id.NextVal,'Inception', 2010 ,'F');
INSERT INTO Movie VALUES (movie_id.NextVal,'Game of Thrones', 2011 ,'S');
INSERT INTO Movie VALUES (movie_id.NextVal,'Breaking Bad', 2008 ,'S');


INSERT INTO hasGenre VALUES(1,2);
INSERT INTO hasGenre VALUES(2,3);
INSERT INTO hasGenre VALUES(3,1);
INSERT INTO hasGenre VALUES(4,2);


INSERT INTO MovieCharacter VALUES (movchar_id.NextVal,'Don Vito Corleone','Don', 1,1,1);
INSERT INTO MovieCharacter VALUES (movchar_id.NextVal,'Daenerys Targaryen','Daeni', 2,4,4);
COMMIT;
