package model;

import model.DbId;

/**
 * Domain Model Class for Game.
 * Represents a game with a name and a database ID.
 */
public class Game extends DbId {
    private String name; // The name of the game

    /**
     * Default constructor for Game.
     * This constructor is private to prevent direct instantiation without parameters.
     */
    private Game() {
        super(); // Call parent constructor
    } 

    /**
     * Overloaded constructor for Game.
     * Initializes the game with a specific name and ID.
     *
     * @param _name The name of the game.
     * @param _id The database ID of the game.
     */
    public Game(String _name, long _id) {
        super(_id); // Call parent constructor
        this.name = _name;
    } 

    /**
     * Sets the name of the game.
     *
     * @param _name The new name of the game.
     */
    public void setName(String _name) {
        this.name = _name;    
    }

    /**
     * Gets the name of the game.
     *
     * @return The current name of the game.
     */
    public String getName() {
        return this.name;    
    }

    /**
     * Returns a string representation of the game for debugging purposes.
     * Overrides the dbgMeAsStr method in DbId.
     *
     * @return A string representing the game's name and ID.
     */
    @Override
    public String dbgMeAsStr() {
        return "Game: name " + this.name + super.dbgMeAsStr(); // Call parent function
    } 
}
