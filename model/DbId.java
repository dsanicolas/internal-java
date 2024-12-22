package model;

/**
 * DbId is the base class for all elements with a database ID.
 * This class also includes some debug functions.
 */
public class DbId {
    
    private int id; // The database ID

    /**
     * Default constructor for DbId.
     * This constructor is protected to prevent direct instantiation.
     */
    protected DbId() {
    }

    /**
     * Overloaded constructor for DbId.
     * Initializes the object with a specific ID.
     *
     * @param _id The database ID.
     */
    protected DbId(int _id) {
        this.id = _id;
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
     * You should override this function to provide your own (polymorphic) behavior.
     *
     * @return A string representing the object's ID.
     */
    public String dbgMeAsStr() {
        return " id " + this.id;
    }

    /**
     * Prints a string representation of the object for debugging purposes.
     */
    public void dbgMe() {
        System.out.println(this.dbgMeAsStr());
    }
}
