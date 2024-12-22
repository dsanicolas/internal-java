package utils;

import model.Player;
import model.Game;

public class AppStateTest {
    public static void main(String[] args) {
        AppStateTest test = new AppStateTest();
        test.testGetSetPlayer1();
        test.testGetSetPlayer2();
        test.testGetSetGame();
        test.testReset();
    }

    public void testGetSetPlayer1() {
        AppState appState = new AppState();
        Player player1 = new Player("Player1", 1);
        appState.setPlayer1(player1);
        assert appState.getPlayer1() == player1 : "testGetSetPlayer1 failed";
        System.out.println("testGetSetPlayer1 passed");
    }

    public void testGetSetPlayer2() {
        AppState appState = new AppState();
        Player player2 = new Player("Player2", 2);
        appState.setPlayer2(player2);
        assert appState.getPlayer2() == player2 : "testGetSetPlayer2 failed";
        System.out.println("testGetSetPlayer2 passed");
    }

    public void testGetSetGame() {
        AppState appState = new AppState();
        Game game = new Game("Tic Tac Toe", 1);
        appState.setGame(game);
        assert appState.getGame() == game : "testGetSetGame failed";
        System.out.println("testGetSetGame passed");
    }

    public void testReset() {
        AppState appState = new AppState();
        appState.setPlayer1(new Player("Player1", 1));
        appState.setPlayer2(new Player("Player2", 2));
        appState.setGame(new Game("Tic Tac Toe", 1));
        appState.reset();
        assert appState.getPlayer1() == null : "testReset failed for player1";
        assert appState.getPlayer2() == null : "testReset failed for player2";
        assert appState.getGame() == null : "testReset failed for game";
        System.out.println("testReset passed");
    }
}
