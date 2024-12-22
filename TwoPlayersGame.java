import java.util.List;
import java.util.Iterator;

import view.HomeView;

import db.DbMockup;
import model.Player;
import model.Game;
import model.Match;

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

         DbMockup db = new DbMockup();

        List<Player> plyrs = db.getPlayers("prefix");
        List<Game> gms = db.getGames();
        
        Iterator<Player> plyrsItr = plyrs.iterator();
        while(plyrsItr.hasNext()){
            Player plyr = plyrsItr.next();
            plyr.dbgMe();        
        }

        Iterator<Game> gmsItr = gms.iterator();
        while(gmsItr.hasNext()){
            Game gm = gmsItr.next();
            gm.dbgMe(); 
        }
        
        Match aMtch = new Match(new Player("Nicolas",1),new Player("John",2),new Game("Tic Tac Toe",1), 1);
        aMtch.dbgMe();

    }

    /**
     * Runs the application in normal mode.
     * Initializes the database mockup and renders various views for the application.
     */
     static void runApplication() {
        HomeView.main(null);
    }
   
}

