import view.HomeView;
import view.PlayerSelectionView;
import view.PlayerCreateView;
import view.PlayerEditView;
import view.GameView;
import view.ResultView;

import db.DatabaseConnection;
import db.DbRepository;
import db.DbInterface;
import db.DbMockup;
import db.DbMockupTest;

import model.Player;
import model.Game;
import model.Match;
import model.ModelTest;

import utils.AppState; 
import utils.AppStateTest;

import java.sql.Connection;
import java.sql.SQLException;

import navigation.NavigationController; 
import navigation.NavigationControllerTest;

import javax.swing.JOptionPane;

// First Install Java (jre, jdk)
// Ubuntu:
//      sudo apt install default-jre
//      sudo apt install default-jdk
// https://www.java.com/en/download/
// check version java -version

/**
 * Main class for the TwoPlayersGame application.
 * 
 * Compilation:
 * javac -classpath .:/usr/share/java/mysql-connector-java-9.2.0.jar TwoPlayersGame.java
 * java -classpath .:/usr/share/java/mysql-connector-java-9.2.0.jar TwoPlayersGame
 * 
 * Running:
 * java classpath .:/usr/share/java/mysql-connector-java-9.2.0.jar ./ TwoPlayersGame
 * 
 * Running Tests:
 * java classpath .:/usr/share/java/mysql-connector-java-9.2.0.jar ./ TwoPlayersGame --test
*
 * Use DB Mockup:
 * java -classpath ./ TwoPlayersGame --mockup
 **/
public class TwoPlayersGame {
    
    /**
     * Main method for starting the application.
     * Checks if the application should run in test mode or normal mode.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        boolean isTestMode = false;
        boolean useMockup = false;

        for (String arg : args) {
            if ("--test".equalsIgnoreCase(arg)) {
                isTestMode = true;
                break;
            }
            if ("--mockup".equalsIgnoreCase(arg)) {
                useMockup= true;
                break;
            }
        }

        if (isTestMode) {
            System.out.println("TwoPlayersGame - Test mode activated.");
            runTests();
        } else {
            System.out.println("TwoPlayersGame - Normal mode activated.");
            runApplication(useMockup);
        }
    } 

    /**
     * Runs the application in test mode.
     * Initializes the database mockup and runs various tests on the models.
     */
    static void runTests() {

        ModelTest.main(null);
        DbMockupTest.main(null);
        AppStateTest.main(null);
        NavigationControllerTest.main(null);

    }

    /**
     * Runs the application in normal mode.
     * Initializes the database mockup and renders various views for the application.
     */
    static void runApplication(boolean useMockup) {
        DbInterface db = null;
        if(!useMockup){
            try {
                Connection connection = DatabaseConnection.getConnection();
                db = new DbRepository(connection);
            }catch (Exception e) {
                e.printStackTrace();
                // Show a Java notification using Swing
                JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            db = new DbMockup();
        }
        AppState state = new AppState();
        NavigationController nav = new NavigationController(db, state);
        nav.showHomeView();
    }

}

