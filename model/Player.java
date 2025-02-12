package model;

import model.DbId;

/**
 * Domain Model Class for Player.
 * Represents a player with a nickname, score, and database ID.
 */
public class Player extends DbId {
    private String nickName; // The nickname of the player
    private int score; // The score of the player

    /**
     * Constructor for Player.
     * Initializes the player with a specific nickname and ID.
     *
     * @param _nickName The nickname of the player.
     * @param _id The database ID of the player.
     */
    public Player(String _nickName, long _id) {
        super(_id);
        this.nickName = _nickName;
    } 

    /**
     * Constructor for Player.
     * Initializes the player with a specific nickname and ID.
     *
     * @param _nickName The nickname of the player.
     * @param score The score of the player.
     * @param _id The database ID of the player.
     */
    public Player(String _nickName, int score, long _id) {
        super(_id);
        this.nickName = _nickName;
        this.score = score;
    } 


    /**
     * Sets the nickname of the player.
     *
     * @param _nickName The new nickname of the player.
     */
    public void setNickName(String _nickName) {
        this.nickName = _nickName;    
    }

    /**
     * Gets the nickname of the player.
     *
     * @return The current nickname of the player.
     */
    public String getNickName() {
        return this.nickName;    
    }

    /**
     * Sets the score of the player.
     *
     * @param _score The new score of the player.
     */
    public void setScore(int _score) {
        this.score = _score;    
    }

    /**
     * Gets the score of the player.
     *
     * @return The current score of the player.
     */
    public int getScore() {
        return this.score; 
    }

    /**
     * Returns a string representation of the player for debugging purposes.
     * Overrides the dbgMeAsStr method in DbId.
     *
     * @return A string representing the player's nickname, score, and ID.
     */
    @Override
    public String dbgMeAsStr() {
       return "Player: nickName " + this.nickName + " score " +  this.score + super.dbgMeAsStr(); // Call parent function 
    } 
}

