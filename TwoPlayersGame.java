import view.HomeView;
import view.PlayerSelectionView;
import view.PlayerCreateView;
import view.PlayerEditView;
import view.GameView;
import view.ResultView;

import db.DbMockup;
import db.DbMockupTest;

import model.Player;
import model.Game;
import model.Match;
import model.ModelTest;

import utils.AppState; 
import utils.AppStateTest;


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
 * javac -classpath ./ TwoPlayersGame.java
 * 
 * Running:
 * java -classpath ./ TwoPlayersGame
 * 
 * Running Tests:
 * java -classpath ./ TwoPlayersGame --test
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

        for (String arg : args) {
            if ("--test".equalsIgnoreCase(arg)) {
                isTestMode = true;
                break;
            }
        }

        if (isTestMode) {
            System.out.println("TwoPlayersGame - Test mode activated.");
            runTests();
        } else {
            System.out.println("TwoPlayersGame - Normal mode activated.");
            runApplication();
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
    }

    /**
     * Runs the application in normal mode.
     * Initializes the database mockup and renders various views for the application.
     */
    static void runApplication() {
        DbMockup db = new DbMockup();
        AppState state = new AppState();

        HomeView hmVw = new HomeView(db,state);
        hmVw.render();

        PlayerSelectionView psVw = new PlayerSelectionView(db,state);
        psVw.render();

        GameView gmVw = new GameView(db, new Player("Nicolas", 1), new Player("John", 2), new Game("Tic Tac Toe", 1), state);
        gmVw.render();

        ResultView resWinVw = new ResultView(new Player("Nicolas", 1), new Player("John", 2), new Game("Tic Tac Toe", 1), 5, 3, new Player("Nicolas", 1));
        resWinVw.render();

        ResultView resNulVw = new ResultView(new Player("Nicolas", 1), new Player("John", 2), new Game("Tic Tac Toe", 1), 5, 5, null);
        resNulVw.render();

        PlayerCreateView pcVw = new PlayerCreateView(db, true);
        pcVw.render();

        PlayerEditView peVw = new PlayerEditView(db, new Player("Nicolas", 1), true);
        peVw.render();
    }

}

