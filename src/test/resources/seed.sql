drop TABLE IF EXISTS Beer CASCADE;
DROP TABLE IF EXISTS Distributor CASCADE;
DROP TABLE IF EXISTS BD CASCADE;

CREATE TABLE Beer
(
    Beer_id int auto_increment PRIMARY KEY,
    Name varchar(255)
);

CREATE TABLE Distributor
(
    Distributor_id int auto_increment PRIMARY KEY,
    Name varchar(255)
);

CREATE TABLE BD
(
    Beer_id INT NOT NULL,
    Distributor_id INT NOT NULL,
    FOREIGN KEY (Beer_id) REFERENCES beer (Beer_id) ON DELETE CASCADE,
    FOREIGN KEY (Distributor_id) REFERENCES Distributor (Distributor_id) ON DELETE CASCADE,
    UNIQUE (Beer_id, Distributor_id)
);
ALTER TABLE BD
  ADD CONSTRAINT PK_BD PRIMARY KEY (Beer_id, Distributor_id);

INSERT into Beer (Name)
VALUES ('Bud');
INSERT into Beer (Name)
VALUES ('Heineken');
INSERT into Beer (Name)
VALUES ('Kolos');
INSERT into Beer (Name)
VALUES ('Sven');

INSERT InTO Distributor (Name)
VALUES ('World Beer');
INSERT InTO Distributor (Name)
VALUES ('Bryansk Beer');

INSERT InTO BD (Beer_id, Distributor_id)
VALUES (1, 1);
INSERT InTO BD (Beer_id, Distributor_id)
VALUES (2, 1);
INSERT InTO BD (Beer_id, Distributor_id)
VALUES (3, 2);
INSERT InTO BD (Beer_id, Distributor_id)
VALUES (4, 2);
INSERT InTO BD (Beer_id, Distributor_id)
VALUES (3, 1);

