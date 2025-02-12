package db;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import model.Player;
import model.Game;
import model.Match;

/**
 * DbMockup Db MockUp simulate Database Operations
 * 
**/
public class DbMockup implements DbInterface{

    private List<Player> players; // List of all players
    private List<Player> topPlayers; // List of top players

    public DbMockup(){
        this.players = new ArrayList<Player>();
        this.players.add(new Player("Nicolas", 1));
        this.players.add(new Player("Robert", 2));
        this.players.add(new Player("Frank", 3));
        this.players.add(new Player("John", 4));
        this.topPlayers = new ArrayList<Player>();
        this.topPlayers.add(new Player("Mon", 10));
        this.topPlayers.add(new Player("Julia", 24));
        this.topPlayers.add(new Player("Eva", 33));
        this.topPlayers.add(new Player("John", 44));
    }
    
    /**
     * Retrieves a list of players.
     *
     * @param prefix The prefix to filter players by name.
     * @return A list of players.
     */
    @Override
    public List<Player> getPlayers(String prefix){
        System.out.println("DbMockup - getPlayers success"); 
        return this.players;
    }

    /**
     * Retrieves a list of top players.
     *
     * @return A list of top players.
     */
    @Override
    public List<Player> getTopPlayers(){
        System.out.println("DbMockup - getTopPlayers success"); 
        return this.topPlayers;
    }

    /**
     * Retrieves a list of games.
     *
     * @return A list of games.
     */
    @Override
    public List<Game> getGames(){
        List<Game> list = new ArrayList<Game>();
        list.add(new Game("Tic Tac Toe", 1));
        list.add(new Game("Checkers", 2));
        System.out.println("DbMockup - getGames success"); 
        return list;
    }

    /**
     * Creates a new player.
     *
     * @param _nickName The nickname of the new player.
     * @return The newly created player.
     * @throws Exception If the nickname is not valid or already exists.
     */
    @Override
    public Player createPlayer(String _nickName) throws Exception{
        // Simulate random exception in case of nickname duplication
        if(Math.random() < 0.5){
            throw new Exception("constraint nickName unique violated");       
        }
        Player nwPlyr = new Player(_nickName, this.players.size()); // Simulate creation of a new player with an ID equal to the list size
        this.players.add(nwPlyr); // Add it to our player list
        return nwPlyr; 
    }

    /**
     * Renames an existing player.
     *
     * @param _plyr The player to rename.
     * @param _nickName The new nickname for the player.
     * @return The renamed player.
     * @throws Exception If the nickname is not valid or already exists.
     */
    @Override
    public Player renamePlayer(Player _plyr, String _nickName) throws Exception{
        // Simulate random exception in case of nickname duplication
        if(Math.random() < 0.5){
            throw new Exception("constraint nickName unique violated");       
        }
        _plyr.setNickName(_nickName);
        
        // Also update our list of players to make the mockup more realistic
        Iterator<Player> itr = this.players.iterator();
        while (itr.hasNext()) {
            Player plyr = itr.next();
            if (plyr.getId() == _plyr.getId()) {
                plyr.setNickName(_nickName);
                break;
            }
        }
        System.out.println("DbMockup - renamePlayer:" + _plyr.dbgMeAsStr()); 
        return _plyr; 
    }

    /**
     * Updates the score of a player.
     *
     * @param _player The player whose score is to be updated.
     * @param _score The new score for the player.
     * @return The updated player.
     */
    @Override
    public Player updatePlayerScore(Player _player, int _score){
        Player updated = new Player(_player.getNickName(), _player.getId());
        updated.setScore(_score);
        System.out.println("DbMockup - updatePlayerScore match:" + _player.dbgMeAsStr()); 
        return updated; // Simulate update of a new player with a random ID    
    }

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
    @Override
    public Match informMatchResult(Player _player1, Player _player2, Game _game, int _matchScorePlayer1, int _matchScorePlayer2){
        // Simulate creation of a match in db
        Match match = new Match(_player1, _player2, _game, (int)(Math.random() * 100)); // Simulate creation of a new match with a random ID
        match.setScorePlayer1(_matchScorePlayer1);
        match.setScorePlayer2(_matchScorePlayer2);
        System.out.println("DbMockup - informMatchResult match:" + match.dbgMeAsStr()); 
        return match;
    }

}
