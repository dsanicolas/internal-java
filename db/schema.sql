-- sudo mysql
CREATE SCHEMA TwoPlayersGame;

-- create user and passwd to access that db
CREATE USER 'root'@'localhost' IDENTIFIED BY 'Agora123';
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'Agora123';
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;

USE  TwoPlayersGame;

CREATE TABLE `GAME`
(
 `ID`   bigint NOT NULL ,
 `NAME` varchar(45) NOT NULL ,

PRIMARY KEY (`ID`),
 CONSTRAINT `Unique_Game_Name` UNIQUE (NAME) 
);

CREATE TABLE `Player`
(
 `ID`    bigint NOT NULL ,
 `NAME`  varchar(45) NOT NULL ,
 `SCORE` integer NOT NULL ,

PRIMARY KEY (`ID`),
CONSTRAINT `Unique_Player_Name` UNIQUE (NAME) 
);

CREATE TABLE `MATCH`
(
 `ID`           bigint NOT NULL ,
 `DATE`         datetime NOT NULL ,
 `PLAYER1`      bigint NOT NULL ,
 `PLAYER2`      bigint NOT NULL ,
 `GAME`         bigint NOT NULL ,
 `scorePlayer1` integer NOT NULL ,
 `scorePlayer2` integer NOT NULL ,

PRIMARY KEY (`ID`),
KEY `FK_1` (`PLAYER1`),
CONSTRAINT `FK_1` FOREIGN KEY (`PLAYER1`) REFERENCES `Player` (`ID`),
KEY `FK_2` (`PLAYER2`),
CONSTRAINT `FK_2` FOREIGN KEY (`PLAYER2`) REFERENCES `Player` (`ID`),
KEY `FK_3` (`GAME`),
CONSTRAINT `FK_3` FOREIGN KEY (`GAME`) REFERENCES `GAME` (`ID`)
);

commit;


