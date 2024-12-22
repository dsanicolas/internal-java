package utils;

import model.Player;
import model.Game;

public class AppState {
    private Player player1;
    private Player player2;
    private Game game;

    /**
     * Gets the first player.
     * @return the first player.
     */
    public Player getPlayer1() {
        return player1;
    }

    /**
     * Sets the first player.
     * @param player1 the first player to set.
     */
    public void setPlayer1(Player player1) {
        this.player1 = player1;
        System.out.println(this.dbgMeAsStr());
    }

    /**
     * Gets the second player.
     * @return the second player.
     */
    public Player getPlayer2() {
        return player2;
    }

    /**
     * Sets the second player.
     * @param player2 the second player to set.
     */
    public void setPlayer2(Player player2) {
        this.player2 = player2;
        System.out.println(this.dbgMeAsStr());
    }

    /**
     * Gets the game.
     * @return the game.
     */
    public Game getGame() {
        return game;
    }

    /**
     * Sets the game.
     * @param game the game to set.
     */
    public void setGame(Game game) {
        this.game = game;
        System.out.println(this.dbgMeAsStr());
    }

    /**
     * Resets the state of the application.
     * Sets all fields to null.
     */
    public void reset() {
        this.player1 = null;
        this.player2 = null;
        this.game = null;
        System.out.println(this.dbgMeAsStr());
    }

    /**
    * debug    
    **/
    public String dbgMeAsStr(){
        return "AppState - player1: " +  (player1==null ? "null":player1.dbgMeAsStr()) + " player2:" +  (player2== null ? "null":player2.dbgMeAsStr()) + " game:" + (game==null ? "null":game.dbgMeAsStr());
    }
}

