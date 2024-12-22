package navigation;

import view.*;
import db.DbMockup;
import model.Game;
import model.Player;

import utils.AppState;

/**
 * Unit tests for the NavigationController class.
 * This class uses basic Java features to test the navigation logic.
 */
public class NavigationControllerTest {

    // Mock objects for dependencies
    private DbMockup db; // Mock database object
    private NavigationController navigationController; // Navigation controller to be tested
    private HomeView homeViewMock; // Mock HomeView
    private PlayerSelectionView playerSelectionViewMock; // Mock PlayerSelectionView
    private GameView gameViewMock; // Mock GameView
    private PlayerEditView playerEditViewMock; // Mock PlayerEditView
    private PlayerCreateView playerCreateViewMock; // Mock PlayerCreateView
    private ResultView resultViewMock; // Mock ResultView
    private AppState appState; // Application state object

    /**
     * Main method to run the tests.
     * Executes all test methods to verify navigation logic.
     */
    public static void main(String[] args) {
        NavigationControllerTest test = new NavigationControllerTest();
        test.testNavigateToHomeView();
        test.testNavigateToPlayerSelectionView();
        test.testNavigateToGameView();
        test.testNavigateToPlayerEditView();
        test.testNavigateToPlayerCreateView();
        test.testNavigateToResultView();
    }

    /**
     * Sets up the test environment before each test.
     * Initializes mock objects and the NavigationController with overridden methods.
     */
    public void setUp() {
        // Create mock objects
        this.db = new DbMockup();
        this.appState = new AppState();
        this.appState.setPlayer1(new Player("Player1", 1));
        this.appState.setPlayer2(new Player("Player2", 2));
        this.appState.setGame(new Game("Tic Tac Toe", 1));
        
        homeViewMock = new HomeView(db, this.appState, null) {
            @Override
            public void render() {
                System.out.println("HomeView rendered");
            }
        };
        
        playerSelectionViewMock = new PlayerSelectionView(db, this.appState, null) {
            @Override
            public void render() {
                System.out.println("PlayerSelectionView rendered");
            }
        };
        
        gameViewMock = new GameView(db, new Player("Nicolas", 1), new Player("John", 2), new Game("Tic Tac Toe", 1), this.appState, null) {
            @Override
            public void render() {
                System.out.println("GameView rendered");
            }
        };
        
        playerEditViewMock = new PlayerEditView(db, new Player("Nicolas", 1), true, null) {
            @Override
            public void render() {
                System.out.println("PlayerEditView rendered");
            }
        };
        
        playerCreateViewMock = new PlayerCreateView(db, true, null) {
            @Override
            public void render() {
                System.out.println("PlayerCreateView rendered");
            }
        };
        
        resultViewMock = new ResultView(this.appState.getPlayer1(), this.appState.getPlayer2(), this.appState.getGame(), 6, 5, this.appState.getPlayer1(), null) {
            @Override
            public void render() {
                System.out.println("ResultView rendered");
            }
        };

        // Initialize the NavigationController with overridden methods to use mocks
        navigationController = new NavigationController(this.db, this.appState) {
            @Override
            public void showHomeView() {
                homeViewMock.render();
            }

            @Override
            public void showPlayerSelectionView() {
                playerSelectionViewMock.render();
            }

            @Override
            public void showGameView() {
                gameViewMock.render();
            }

            @Override
            public void showPlayerEditView(Player player, boolean isForPlayer1) {
                playerEditViewMock.render();
            }

            @Override
            public void showPlayerCreateView(boolean isForPlayer1) {
                playerCreateViewMock.render();
            }

            @Override
            public void showResultView(int scorePlayer1, int scorePlayer2, Player winner) {
                resultViewMock.render();
            }
        };
    }

    /**
     * Tests the navigation to the HomeView.
     * Verifies that the HomeView's render method is called.
     */
    public void testNavigateToHomeView() {
        setUp();
        navigationController.navigateToHomeView();
    }

    /**
     * Tests the navigation to the PlayerSelectionView.
     * Verifies that the PlayerSelectionView's render method is called.
     */
    public void testNavigateToPlayerSelectionView() {
        setUp();
        navigationController.navigateToPlayerSelectionView();
    }

    /**
     * Tests the navigation to the GameView.
     * Verifies that the GameView's render method is called.
     */
    public void testNavigateToGameView() {
        setUp();
        navigationController.navigateToGameView();
    }

    /**
     * Tests the navigation to the PlayerEditView.
     * Verifies that the PlayerEditView's render method is called.
     */
    public void testNavigateToPlayerEditView() {
        setUp();
        Player player = new Player("Player", 1);
        navigationController.navigateToPlayerEditView(player, true);
    }

    /**
     * Tests the navigation to the PlayerCreateView.
     * Verifies that the PlayerCreateView's render method is called.
     */
    public void testNavigateToPlayerCreateView() {
        setUp();
        navigationController.navigateToPlayerCreateView(true);
    }

    /**
     * Tests the navigation to the ResultView.
     * Verifies that the ResultView's render method is called.
     */
    public void testNavigateToResultView() {
        setUp();
        Player player1 = new Player("Player1", 1);
        Player player2 = new Player("Player2", 2);
        navigationController.navigateToResultView(3, 2, player1);
    }
}
