package model;

/**
 * Domain Model Class for Game.
 * Represents a game with a name and a database ID.
 */
public class Game {
    private String name; // The name of the game
    private int id; // The database ID

    /**
     * Default constructor for Game.
     * This constructor is private to prevent direct instantiation without parameters.
     */
    private Game() {
    } 

    /**
     * Overloaded constructor for Game.
     * Initializes the game with a specific name and ID.
     *
     * @param _name The name of the game.
     * @param _id The database ID of the game.
     */
    public Game(String _name, int _id) {
        this.id = _id;
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
     * Returns a string representation of the object for debugging purposes.
     *
     * @return A string representing the object's ID.
     */
    public String dbgMeAsStr() {
        return "Game: name " + this.name + " id " + this.id;
    }

    /**
     * Prints a string representation of the object for debugging purposes.
     */
    public void dbgMe() {
        System.out.println(this.dbgMeAsStr());
    }

}
