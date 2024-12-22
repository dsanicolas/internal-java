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
 **/
public class TwoPlayersGame {
    
    /**
     * Main method for starting the application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
       
        Player aPlyr = new Player("Player1", 1);
        aPlyr.dbgMe();

        Game aGm = new Game("Tic Tac Toe", 1);
        aGm.dbgMe();
        
        Match aMtch = new Match(new Player("Nicolas",1),new Player("John",2),new Game("Tic Tac Toe",1), 1);
        aMtch.dbgMe();
    }
   
}

