package db;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import model.Player;
import model.Game;
import model.Match;

/**
 * DbMockup Db MockUp simulate Database Operations
 * 
**/
public class DbMockupTest{


    /**
     * Main method to run the DbMockupTest.
     *
     * @param args Command line arguments.
     */
    public static void main(String args[]){
        DbMockup db = new DbMockup();

        List<Player> plyrs = db.getPlayers("prefix");
        List<Game> gms = db.getGames();
        
        Iterator<Player> plyrsItr = plyrs.iterator();
        while (plyrsItr.hasNext()) {
            Player plyr = plyrsItr.next();
            plyr.dbgMe();        
        }

        Iterator<Game> gmsItr = gms.iterator();
        while (gmsItr.hasNext()) {
            Game gm = gmsItr.next();
            gm.dbgMe(); 
        }

        Match aMtch = new Match(new Player("Nicolas", 1), new Player("John", 2), new Game("Tic Tac Toe", 1), 1);
        aMtch.dbgMe();    
    }
}
