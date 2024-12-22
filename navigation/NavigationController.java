package navigation;

import view.*;
import db.DbMockup;
import model.Game;
import model.Player;
import utils.AppState;

/**
 * NavigationController manages the transitions between different views in the application.
 * It handles the logic for navigating from one view to another based on user actions.
 */
public class NavigationController {

    // Database mockup for data operations
    private DbMockup db;

    // Views managed by the controller
    private HomeView homeView;
    private PlayerSelectionView playerSelectionView;
    private GameView gameView;
    private PlayerEditView playerEditView;
    private PlayerCreateView playerCreateView;
    private ResultView resultView;
    private AppState appState;

    /**
     * Constructs the NavigationController with the given database mockup.
     *
     * @param db the database mockup used for data operations
     * @param appState the state of the app
     */
    public NavigationController(DbMockup db, AppState appState) {
        this.db = db;
        this.appState = appState;
        this.homeView = new HomeView(this.db, this.appState, this);
    }

    /**
     * Displays the HomeView.
     */
    public void showHomeView() {
        homeView.render();
    }

    /**
     * Displays the PlayerSelectionView.
     */
    public void showPlayerSelectionView() {
        if (this.playerSelectionView == null) {
            this.playerSelectionView = new PlayerSelectionView(this.db, this.appState, this);
        }
        this.playerSelectionView.render();
    }

    /**
     * Displays the GameView for the given players and game.
     */
    public void showGameView() {
        if (this.gameView == null) {
            this.gameView = new GameView(this.db, appState.getPlayer1(), appState.getPlayer2(), appState.getGame(), this.appState, this);
        }
        this.gameView.render();
    }

    /**
     * Displays the PlayerEditView for the given player.
     *
     * @param player the player to be edited
     * @param isForPlayer1 true if editing player 1, false if editing player 2
     */
    public void showPlayerEditView(Player player, boolean isForPlayer1) {
        if (this.playerEditView == null) {
            this.playerEditView = new PlayerEditView(this.db, player, isForPlayer1, this);
        }
        this.playerEditView.render();
    }

    /**
     * Displays the PlayerCreateView for creating a new player.
     *
     * @param isForPlayer1 true if creating player 1, false if creating player 2
     */
    public void showPlayerCreateView(boolean isForPlayer1) {
        if (this.playerCreateView == null) {
            this.playerCreateView = new PlayerCreateView(this.db, isForPlayer1, this);
        }
        this.playerCreateView.render();
    }

    /**
     * Displays the ResultView for the given players, game, and scores.
     *
     * @param _matchScorePlayer1 the score of the first player
     * @param _matchcorePlayer2 the score of the second player
     * @param _winner the player who won the game, null for null match
     */
    public void showResultView(int _matchScorePlayer1, int _matchcorePlayer2, Player _winner) {
        if (this.resultView == null) {
            this.resultView = new ResultView(this.appState.getPlayer1(), this.appState.getPlayer2(), this.appState.getGame(), _matchScorePlayer1, _matchcorePlayer2, _winner, this);
        }
        this.resultView.render();
    }

    /**
     * Navigates to the HomeView.
     */
    public void navigateToHomeView() {
        showHomeView();
    }

    /**
     * Navigates to the PlayerSelectionView for the given game.
     */
    public void navigateToPlayerSelectionView() {
        showPlayerSelectionView();
    }

    /**
     * Displays the PlayerSelectionView with a pre-selected player 1.
     *
     * @param player1 the pre-selected player 1
     */
    public void navigateToPlayerSelectionViewWithPlayer1(Player player1) {
        playerSelectionView = new PlayerSelectionView(this.db, this.appState, this);
        playerSelectionView.setPlayer1(player1);
        playerSelectionView.render();
    }

    /**
     * Displays the PlayerSelectionView with a pre-selected player 2.
     *
     * @param player2 the pre-selected player 2
     */
    public void navigateToPlayerSelectionViewWithPlayer2(Player player2) {
        playerSelectionView = new PlayerSelectionView(this.db, this.appState, this);
        playerSelectionView.setPlayer2(player2);
        playerSelectionView.render();
    }

    /**
     * Navigates to the GameView for the given players and game.
     */
    public void navigateToGameView() {
        showGameView();
    }

    /**
     * Navigates to the PlayerEditView for the given player.
     *
     * @param player the player to be edited
     * @param isForPlayer1 true if editing player 1, false if editing player 2
     */
    public void navigateToPlayerEditView(Player player, boolean isForPlayer1) {
        showPlayerEditView(player, isForPlayer1);
    }

    /**
     * Navigates to the PlayerCreateView for creating a new player.
     *
     * @param isForPlayer1 true if creating player 1, false if creating player 2
     */
    public void navigateToPlayerCreateView(boolean isForPlayer1) {
        showPlayerCreateView(isForPlayer1);
    }

    /**
     * Navigates to the ResultView for the given players, game, and scores.
     *
     * @param scorePlayer1 the score of the first player
     * @param scorePlayer2 the score of the second player
     * @param winner the player who won the game
     */
    public void navigateToResultView(int scorePlayer1, int scorePlayer2, Player winner) {
        showResultView(scorePlayer1, scorePlayer2, winner);
    }
}
