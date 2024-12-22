package model;

import java.time.LocalDateTime;

import model.DbId;
import model.Player;
import model.Game;

/**
 * Domain Model Class for Match.
 * Represents a match with players, game, scores, and a database ID.
 */
public class Match {
    private LocalDateTime date; // The date and time of the match
    private Player player1; // The first player
    private Player player2; // The second player
    private Game game; // The game being played
    private int scorePlayer1; // The score of the first player
    private int scorePlayer2; // The score of the second player
    private int id; // The database ID

    /**
     * Constructor for Match.
     * Initializes the match with two players, a game, and an ID.
     *
     * @param _player1 The first player.
     * @param _player2 The second player.
     * @param _game The game being played.
     * @param _id The database ID of the match.
     */
    public Match(Player _player1, Player _player2, Game _game, int _id) {
        this.id = _id;
        this.player1 = _player1;
        this.player2 = _player2;
        this.game = _game;
    }

    /**
     * Gets the first player.
     *
     * @return The first player.
     */
    public Player getPlayer1() {
        return this.player1;    
    }

    /**
     * Gets the second player.
     *
     * @return The second player.
     */
    public Player getPlayer2() {
        return this.player2;    
    }

    /**
     * Gets the game being played.
     *
     * @return The game.
     */
    public Game getGame() {
        return this.game;    
    }

    /**
     * Sets the score of the first player.
     *
     * @param _score The new score of the first player.
     */
    public void setScorePlayer1(int _score) {
        this.scorePlayer1 = _score;    
    }

    /**
     * Gets the score of the first player.
     *
     * @return The current score of the first player.
     */
    public int getScorePlayer1() {
        return this.scorePlayer1;    
    }

    /**
     * Sets the score of the second player.
     *
     * @param _score The new score of the second player.
     */
    public void setScorePlayer2(int _score) {
        this.scorePlayer2 = _score;    
    }

    /**
     * Gets the score of the second player.
     *
     * @return The current score of the second player.
     */
    public int getScorePlayer2() {
        return this.scorePlayer2;    
    }

    /**
     * Sets the database ID.
     *
     * @param _id The new database ID.
     */
    public void setId(int _id) {
        this.id = _id;
    }

    /**
     * Gets the database ID.
     *
     * @return The current database ID.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns a string representation of the match for debugging purposes.
     *
     * @return A string representing the match details and IDs.
     */
    public String dbgMeAsStr() {
        return "Match: " +
            (this.player1 == null ? "null" : this.player1.dbgMeAsStr()) + " " +
            (this.player2 == null ? "null" : this.player2.dbgMeAsStr()) + " " +
            (this.game == null ? "null" : this.game.dbgMeAsStr()) + " " +
            " scorePlayer1 " + this.scorePlayer1 +
            " scorePlayer2 " + this.scorePlayer2 + "id " + this.id;
    } 

    /**
     * Prints a string representation of the object for debugging purposes.
     */
    public void dbgMe() {
        System.out.println(this.dbgMeAsStr());
    }
}
