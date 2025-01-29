package service;

import javax.swing.*;
import java.awt.*;
import model.Player;

/**
 * Abstract class that represents the common functionality for a two-player game.
 * Concrete subclasses must implement specific game rules and behaviors.
 */
public abstract class AbstractGame {
    protected boolean isPlayer1 = true;  // Flag to track which player's turn it is
    private Player player1;              // The first player
    private Player player2;              // The second player
    private JPanel panel;                // Game board panel
    private JLabel statusLabel;          // Label to display current player's turn and status
    private GameEndListener gameEndListener; // Listener to notify when the game ends

    /**
     * Constructor for initializing the game.
     *
     * @param player1        the first player
     * @param player2        the second player
     * @param panel          the game board panel
     * @param statusLabel    the label for displaying the current player's turn
     * @param gameEndListener the listener for handling game end events
     */
    protected AbstractGame(Player player1, Player player2, JPanel panel, JLabel statusLabel, GameEndListener gameEndListener) {
        this.player1 = player1;
        this.player2 = player2;
        this.statusLabel = statusLabel;
        this.panel = panel;
        this.gameEndListener = gameEndListener;
    }

    /**
     * Abstract method to start the game.
     * Must be implemented in a subclass to define the game's start behavior.
     *
     * @param panel the game board panel
     */
    abstract protected void onStartGame(JPanel panel);

    /**
     * Abstract method to process a player's move and update the board.
     * Must be implemented in a subclass to handle how the board is updated.
     *
     * @param play    the move made by the player
     * @param isPlayer1 indicates if it is Player 1's turn
     * @param panel   the game board panel
     */
    abstract protected void moveAndPrintBoard(int play, boolean isPlayer1, JPanel panel);

    /**
     * Abstract method to validate a move.
     * Must be implemented in a subclass to define how moves are validated.
     *
     * @param play the move to be validated
     * @return true if the move is valid, false otherwise
     */
    abstract boolean isValidMove(int play);

    /**
     * Abstract method to check if a player has won.
     * Must be implemented in a subclass to define the winning conditions.
     *
     * @param player1 true if checking for Player 1's win, false for Player 2
     * @return true if the player has won, false otherwise
     */
    abstract boolean isWinner(boolean player1);

    /**
     * Abstract method to check if the match is a draw.
     * Must be implemented in a subclass to define the draw condition.
     *
     * @return true if the match is a draw, false otherwise
     */
    abstract boolean isNullMatch();

    /**
     * Starts the game by calling the onStartGame method.
     */
    public void startGame() {
        onStartGame(this.panel);
    }

    /**
     * Handles a player's move, updates the board, checks for a winner or draw,
     * and switches turns.
     *
     * @param play the move made by the player
     */
    protected void handlePlayerMove(int play) {
        if (isValidMove(play)) {
            moveAndPrintBoard(play, isPlayer1, this.panel);

            // Check for winner
            if (isWinner(isPlayer1)) {
                gameEndListener.onGameEnd(isPlayer1 ? player1 : player2);
                return;
            }

            // Check for winner for the other player
            if (isWinner(!isPlayer1)) {
                gameEndListener.onGameEnd(!isPlayer1 ? player1 : player2);
                return;
            }

            // Check for draw
            if (isNullMatch()) {
                gameEndListener.onGameEnd(null);
                return;
            }

            // Switch player
            isPlayer1 = !isPlayer1;
            statusLabel.setText((isPlayer1 ? player1.getNickName() : player2.getNickName()) + "'s turn");
        } else {
            statusLabel.setText("Invalid move!" + (isPlayer1 ? player1.getNickName() : player2.getNickName()) + ", try again !");
        }
    }
}

