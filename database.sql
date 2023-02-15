create DATABASE IF NOT EXISTS orderManagement_database;
USE orderManagement_database;

CREATE TABLE IF NOT EXISTS CLIENT(
	id int unique auto_increment primary key,
	nume char(40),
    varsta int,
	buget int);
    
CREATE TABLE IF NOT EXISTS PRODUS(
	id int unique auto_increment primary key,
    NUME CHAR(30),
    PRET INT,
    STOC INT);

CREATE TABLE IF NOT EXISTS COMANDA(
	id int unique auto_increment primary key,
    ID_CLIENT INT,
    ID_PRODUS INT,
    CANTITATE INT,
    FOREIGN KEY(ID_CLIENT) REFERENCES CLIENT(ID),
    FOREIGN KEY(ID_PRODUS) REFERENCES PRODUS(ID));
    
INSERT INTO CLIENT(NUME, VARSTA, BUGET) VALUES('POP IOANA', 34, 260);
    
   
   