package db;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import model.Player;
import model.Game;
import model.Match;

public interface DbInterface{
    
    /**
     * Retrieves a list of players.
     *
     * @param prefix The prefix to filter players by name.
     * @return A list of players.
     */
    public List<Player> getPlayers(String prefix);

    /**
     * Retrieves a list of top players.
     *
     * @return A list of top players.
     */
    public List<Player> getTopPlayers();

    /**
     * Retrieves a list of games.
     *
     * @return A list of games.
     */
    public List<Game> getGames();

    /**
     * Creates a new player.
     *
     * @param _nickName The nickname of the new player.
     * @return The newly created player.
     * @throws Exception If the nickname is not valid or already exists.
     */
    public Player createPlayer(String _nickName) throws Exception;

    /**
     * Renames an existing player.
     *
     * @param _plyr The player to rename.
     * @param _nickName The new nickname for the player.
     * @return The renamed player.
     * @throws Exception If the nickname is not valid or already exists.
     */
    public Player renamePlayer(Player _plyr, String _nickName) throws Exception;
    /**
     * Updates the score of a player.
     *
     * @param _player The player whose score is to be updated.
     * @param _score The new score for the player.
     * @return The updated player.
     */
    public Player updatePlayerScore(Player _player, int _score);
    /**
     * Informs the result of a match.
     *
     * @param _player1 The first player in the match.
     * @param _player2 The second player in the match.
     * @param _game The game being played.
     * @param _matchScorePlayer1 The score of the first player.
     * @param _matchScorePlayer2 The score of the second player.
     * @return The match with the results.
     */
    public Match informMatchResult(Player _player1, Player _player2, Game _game, int _matchScorePlayer1, int _matchScorePlayer2);

}
