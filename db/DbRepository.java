package db;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import model.Player;
import model.Game;
import model.Match;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;

import javax.swing.JOptionPane;

/**
 * Handles database operations.
 */
public class DbRepository implements DbInterface {
    private Connection connection;

    /**
     * Constructs a DbRepository with the specified database connection.
     *
     * @param connection the database connection
     */
    public DbRepository(Connection connection) {
        this.connection = connection;
    }

    /**
     * Retrieves a list of players whose names start with the specified prefix.
     *
     * @param prefix the prefix to search for
     * @return a list of players matching the prefix
     */
    @Override
    public List<Player> getPlayers(String prefix) {
        String query = "SELECT `ID`, `NAME`, `SCORE` FROM `Player` WHERE `NAME` LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, prefix + "%");
            ResultSet rs = stmt.executeQuery();
            List<Player> players = new ArrayList<>();
            while (rs.next()) {
                players.add(new Player(rs.getString("NAME"), rs.getInt("SCORE"), rs.getLong("ID")));
            }
            return players;
        } catch (SQLException e) {
            handleException(e);
            return new ArrayList<>();
        }
    }

    /**
     * Retrieves a list of the top 10 players ordered by score in descending order.
     *
     * @return a list of the top 10 players
     */
    @Override
    public List<Player> getTopPlayers() {
        String query = "SELECT `ID`, `NAME`, `SCORE` FROM `Player` ORDER BY `SCORE` DESC LIMIT 10";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            List<Player> players = new ArrayList<>();
            while (rs.next()) {
                players.add(new Player(rs.getString("NAME"), rs.getInt("SCORE"), rs.getLong("ID")));
            }
            return players;
        } catch (SQLException e) {
            handleException(e);
            return new ArrayList<>();
        }
    }

    /**
     * Retrieves a list of all games.
     *
     * @return a list of games
     */
    @Override
    public List<Game> getGames() {
        String query = "SELECT `ID`, `NAME` FROM `GAME`";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            List<Game> games = new ArrayList<>();
            while (rs.next()) {
                games.add(new Game(rs.getString("NAME"), rs.getLong("ID")));
            }
            return games;
        } catch (SQLException e) {
            handleException(e);
            return new ArrayList<>();
        }
    }

    /**
     * Creates a new player with the specified nickname.
     *
     * @param _nickName the nickname of the new player
     * @return the created player
     * @throws Exception if a SQL integrity constraint is violated
     */
    @Override
    public Player createPlayer(String _nickName) throws Exception {
        String query = "INSERT INTO `Player` (`ID`, `NAME`, `SCORE`) VALUES (?, ?, 0)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            long newId = generateNewId();
            stmt.setLong(1, newId);
            stmt.setString(2, _nickName);
            stmt.executeUpdate();
            return new Player(_nickName, 0, newId);
        } catch(SQLIntegrityConstraintViolationException e) {
            throw new Exception("constraint nickName unique violated");      
        }catch (SQLException e) {
            handleException(e);
            return null;
        }
    }

    /**
     * Renames an existing player.
     *
     * @param _plyr the player to rename
     * @param _nickName the new nickname for the player
     * @return the renamed player
     * @throws Exception if a SQL integrity constraint is violated
     */
    @Override
    public Player renamePlayer(Player _plyr, String _nickName) throws Exception {
        String query = "UPDATE `Player` SET `NAME` = ? WHERE `ID` = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, _nickName);
            stmt.setLong(2, _plyr.getId());
            stmt.executeUpdate();
            _plyr.setNickName(_nickName);
            return _plyr;
        } catch(SQLIntegrityConstraintViolationException e) {
            throw new Exception("constraint nickName unique violated");      
        } catch (SQLException e) {
            handleException(e);
            return null;
        }
    }

    /**
     * Updates the score of an existing player.
     *
     * @param _player the player whose score is to be updated
     * @param _score the new score for the player
     * @return the updated player
     */
    @Override
    public Player updatePlayerScore(Player _player, int _score) {
        String query = "UPDATE `Player` SET `SCORE` = ? WHERE `ID` = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, _score);
            stmt.setLong(2, _player.getId());
            stmt.executeUpdate();
            _player.setScore(_score);
            return _player;
        } catch (SQLException e) {
            handleException(e);
            return null;
        }
    }

    /**
     * Records the result of a match between two players.
     *
     * @param _player1 the first player
     * @param _player2 the second player
     * @param _game the game played
     * @param _matchScorePlayer1 the score of the first player
     * @param _matchScorePlayer2 the score of the second player
     * @return the recorded match
     */
    @Override
    public Match informMatchResult(Player _player1, Player _player2, Game _game, int _matchScorePlayer1, int _matchScorePlayer2) {
        String query = "INSERT INTO `MATCH` (`ID`, `DATE`, `PLAYER1`, `PLAYER2`, `GAME`, `scorePlayer1`, `scorePlayer2`) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            long newId = generateNewId();
            LocalDateTime now = LocalDateTime.now();
            stmt.setLong(1, newId);
            stmt.setTimestamp(2, Timestamp.valueOf(now));
            stmt.setLong(3, _player1.getId());
            stmt.setLong(4, _player2.getId());
            stmt.setLong(5, _game.getId());
            stmt.setInt(6, _matchScorePlayer1);
            stmt.setInt(7, _matchScorePlayer2);
            stmt.executeUpdate();
            return new Match(_player1, _player2, _game, _matchScorePlayer1, _matchScorePlayer2, now, newId);
        } catch (SQLException e) {
            handleException(e);
            return null;
        }
    }

    /**
     * Generates a new unique ID.
     *
     * @return a new unique ID
     */
    private long generateNewId() {
        // Implement a method to generate a new unique ID
        return System.currentTimeMillis(); // Example implementation
    }

    /**
     * Handles SQL exceptions by showing a Java notification using Swing.
     *
     * @param e the SQL exception to handle
     */
    private void handleException(SQLException e) {
        // Show a Java notification using Swing
        JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
