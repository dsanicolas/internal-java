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

import service.AbstractGame;
import service.GameEndListener;
import service.TicTacToe;
import service.Checkers;

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
    private AbstractGame gameService = null; //the implementation of the game

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
    
        int[] matchScore = this.gameService.getScore();

        Match match = this.db.informMatchResult(this.player1, this.player2, this.game, matchScore[0], matchScore[1]);

        navigationController.navigateToResultView(matchScore[0], matchScore[1], this.winner); 
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
        JPanel boardPanel = new JPanel(); //this will be the game board
        grid.add(boardPanel);

        // Status panel
        JLabel statusPanel = new JLabel("", JLabel.CENTER); //this will be the status line
        grid.add(statusPanel);
        
        // Start Game
        this.gameService = getGameService(this.game.getName(), boardPanel, statusPanel);
        this.gameService.startGame();         

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

    private AbstractGame getGameService(String gameName, JPanel boardPanel,JLabel statusPanel){
        if(gameName.equals("Tic Tac Toe")){
            return new TicTacToe(player1, player2,boardPanel, statusPanel, 
                new GameEndListener(){                        
                    public void onGameEnd(Player wnr){
                        winner = wnr;
                        onEndGame();
                        destroyMainFrame();
                    }
             });  
        }
        if(gameName.equals("Checkers")){
            return new Checkers(player1, player2,boardPanel, statusPanel, 
                new GameEndListener(){                        
                    public void onGameEnd(Player wnr){
                        winner = wnr;
                        onEndGame();
                        destroyMainFrame();
                    }
             });  
        }
        return null;
    }
}
