package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;

import db.DbMockup;

import model.Match;
import model.Player;
import model.Game;

import utils.AppState;
import navigation.NavigationController;

import view.BaseView;

/**
 * GameView is responsible for rendering the game interface, managing player interactions,
 * and handling game logic, such as scoring and player turns.
 */
public class GameView extends BaseView {

    // Dependencies and models
    private DbMockup db; // Database mockup used for persistence
    private Player player1; // The first player
    private Player player2; // The second player
    private Player winner; // The winner of the game
    private Game game; // The game being played
    private AppState appState; // Application state
    private boolean isPlayer1Turn = true; // Flag to track if it's player 1's turn

    /**
     * Constructs the GameView.
     *
     * @param _db the database mockup used for persistence
     * @param _player1 the first player
     * @param _player2 the second player
     * @param _game the game being played
     * @param _appState the application state
     * @param _navigationController the navigation controller for managing view transitions
     *
     * Preconditions: _player1, _player2, and _game must not be null.
     */
    public GameView(DbMockup _db, Player _player1, Player _player2, Game _game, AppState _appState, NavigationController _navigationController) {
        super("GAME", _navigationController);
        this.player1 = _player1;
        this.player2 = _player2;
        this.game = _game;
        this.db = _db;
        this.appState = _appState;
    }
   
    /**
     * Handles the end of the game by updating the winner's score and
     * informing the database of the match result. Currently includes a simple
     * scoring algorithm.
     */
    private void onEndGame() {
        if (this.winner != null) {
            if (this.winner.getId() == this.player1.getId()) {
                this.player1 = this.db.updatePlayerScore(this.player1, this.player1.getScore() + 1);
            }
            if (this.winner.getId() == this.player2.getId()) {
                this.player2 = this.db.updatePlayerScore(this.player2, this.player2.getScore() + 1);
            }
        }
    
        // TODO: game score integration
        int matchScorePlayer1 = (int)(Math.random() * 100);
        int matchScorePlayer2 = (int)(Math.random() * 100);

        Match match = this.db.informMatchResult(this.player1, this.player2, this.game, matchScorePlayer1, matchScorePlayer2);

        navigationController.navigateToResultView(matchScorePlayer1, matchScorePlayer2, null); // TODO: SCORE AND WINNER FOR EACH GAME
    }

    /**
     * Handles the leave action by deducting a point from the player who is
     * currently playing.
     */
    private void onLeave() {
        if (isPlayer1Turn) {
            this.player1 = db.updatePlayerScore(this.player1, Math.max(0, this.player1.getScore() - 1));
        } else {
            this.player2 = db.updatePlayerScore(this.player2, Math.max(0, this.player2.getScore() - 1));        
        }
    }

    /**
     * Renders the game interface, including the game board, turn prompt,
     * and control buttons.
     */
    @Override
    public void render() {

        // Set up the main frame
        this.setupMainFrame();

        // Now create a horizontal grid inside that frame with 3 panels: board, turn, leave 
        JPanel grid = new JPanel(new GridLayout(3, 1));
        this.mainFrame.add(grid);

        // Board panel
        JPanel boardPanel = new JPanel();
        JLabel gameBoard = new JLabel("<html><table><tr><td>2</td><td>2</td><td>2</td></tr><tr><td>2</td><td>2</td><td>2</td></tr><td>2</td><td>2</td><td>2</td></tr></table></html>");
        boardPanel.add(gameBoard);
        grid.add(boardPanel);

        // Turn panel
        JPanel turnPanel = new JPanel();
        JLabel promptForPlay = new JLabel(player1.getNickName() + " - please enter your next move");
        turnPanel.add(promptForPlay);
        JButton playButton = new JButton("play");
        playButton.addActionListener(
            new ActionListener() {
                
                // TODO: end game integration, for now we simulate it
                int count = 0;                

                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("GameView - Play Button pressed by " + (isPlayer1Turn ? player1.dbgMeAsStr() : player2.dbgMeAsStr()));
                    isPlayer1Turn = !isPlayer1Turn;
                    promptForPlay.setText((isPlayer1Turn ? player1.getNickName() : player2.getNickName()) + " - please enter your next move");
                    
                    // TODO: end game integration, for now we simulate it                   
                    this.count++;
                    if (this.count > 5) {
                        onEndGame();    
                        destroyMainFrame();            
                    }
                }
            });
        turnPanel.add(playButton);
        grid.add(turnPanel);

        // Leave panel
        JPanel leavePanel = new JPanel();
        JButton leaveButton = new JButton("leave");
        leaveButton.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("GameView - Leave Button pressed by " + (isPlayer1Turn ? player1.dbgMeAsStr() : player2.dbgMeAsStr()));
                    onLeave();    // Perform leave operations
                    navigationController.navigateToHomeView();
                    destroyMainFrame();
                }
            });
        leavePanel.add(leaveButton);
        grid.add(leavePanel);

        // Make the frame visible
        this.mainFrame.setVisible(true);
    }
}
