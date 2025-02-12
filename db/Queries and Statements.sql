-- Select the player's name and score from the Player table
SELECT `ID`,`NAME`,`SCORE`
FROM `Player`
ORDER BY `SCORE` DESC
LIMIT 10;

-- list all games
SELECT `ID`,`NAME`
FROM `GAME`;

-- find player with a name starting by a prefix
SET @prefix = 'prefix%';
SELECT `ID`, `NAME`, `SCORE`
FROM `Player`
WHERE `NAME` LIKE @prefix;

-- Insert a new player into the Player table
INSERT INTO `Player` (`ID`, `NAME`, `SCORE`)
VALUES (?, ?, ?);

-- Update the name of a player in the Player table
UPDATE `Player`
SET `NAME` = ?
WHERE `ID` = ?;

-- Insert a new match into the MATCH table
INSERT INTO `MATCH` (`ID`, `DATE`, `PLAYER1`, `PLAYER2`, `GAME`, `scorePlayer1`, `scorePlayer2`)
VALUES (?, ?, ?, ?, ?, ?, ?);
