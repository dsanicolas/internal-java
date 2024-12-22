package model;

public class ModelTest {
    public static void main(String[] args) {
        testPlayerClass();
        testGameClass();
        testMatchClass();
        System.out.println("All tests passed.");
    }

    private static void testPlayerClass() {
        // Test Player Constructor
        Player player = new Player("Player1", 1);
        assert player.getNickName().equals("Player1") : "Player nickname not set correctly";
        assert player.getId() == 1 : "Player ID not set correctly";
        assert player.getScore() == 0 : "Player score should default to 0";

        // Test Setters and Getters
        player.setNickName("NewPlayer");
        player.setScore(100);
        assert player.getNickName().equals("NewPlayer") : "Player nickname setter failed";
        assert player.getScore() == 100 : "Player score setter failed";

        // Test dbgMeAsStr
        String debugString = player.dbgMeAsStr();
        assert debugString.contains("Player: name NewPlayer") : "dbgMeAsStr does not contain updated nickname";
        assert debugString.contains("score 100") : "dbgMeAsStr does not contain updated score";
    }

    private static void testGameClass() {
        // Test Game Constructor
        Game game = new Game("Tic Tac Toe", 1);
        assert game.getName().equals("Tic Tac Toe") : "Game name not set correctly";
        assert game.getId() == 1 : "Game ID not set correctly";

        // Test Setters and Getters
        game.setName("Checkers");
        assert game.getName().equals("Checkers") : "Game name setter failed";

        // Test dbgMeAsStr
        String debugString = game.dbgMeAsStr();
        assert debugString.contains("Game: name Checkers") : "dbgMeAsStr does not contain updated name";
    }

    private static void testMatchClass() {
        // Test Match Constructor
        Player player1 = new Player("Player1", 1);
        Player player2 = new Player("Player2", 2);
        Game game = new Game("Tic Tac Toe", 1);
        Match match = new Match(player1, player2, game, 1);

        assert match.getPlayer1().equals(player1) : "Player1 not set correctly in Match";
        assert match.getPlayer2().equals(player2) : "Player2 not set correctly in Match";
        assert match.getGame().equals(game) : "Game not set correctly in Match";
        assert match.getId() == 1 : "Match ID not set correctly";

        // Test Setters and Getters for Scores
        match.setScorePlayer1(10);
        match.setScorePlayer2(20);
        assert match.getScorePlayer1() == 10 : "Score for Player1 not set correctly";
        assert match.getScorePlayer2() == 20 : "Score for Player2 not set correctly";

        // Test dbgMeAsStr
        String debugString = match.dbgMeAsStr();
        assert debugString.contains("scorePlayer1 10") : "dbgMeAsStr does not contain scorePlayer1";
        assert debugString.contains("scorePlayer2 20") : "dbgMeAsStr does not contain scorePlayer2";
    }
}
