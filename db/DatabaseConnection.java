package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Handles the database connection.
 * MySQL connector download: https://dev.mysql.com/downloads/connector/j/5.0.html
 */
public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/TwoPlayersGame";
    private static final String USER = "root";
    private static final String PASSWORD = "Agora123";

    /**
     * Establishes and returns a connection to the database.
     *
     * @return a Connection object to the database
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
