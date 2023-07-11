drop TABLE IF EXISTS Beer CASCADE;
DROP TABLE IF EXISTS Distributor CASCADE;
DROP TABLE IF EXISTS BD CASCADE;

CREATE TABLE Beer
(
    Beer_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    Name varchar(255)
);

CREATE TABLE Distributor
(
    Distributor_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    Name varchar(255)
);

CREATE TABLE BD
(
    Beer_id INT,
    Distributor_id INT,
    FOREIGN KEY (Beer_id) REFERENCES beer (Beer_id),
    FOREIGN KEY (Distributor_id) REFERENCES Distributor (Distributor_id),
    UNIQUE (Beer_id, Distributor_id)
);
ALTER TABLE BD
  ADD CONSTRAINT PK_BD PRIMARY KEY (Beer_id, Distributor_id);


