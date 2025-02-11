package service;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Player;

/**
 * Concrete implementation of the AbstractGame class for the Tic-Tac-Toe game.
 * This class implements the specific game logic for Tic-Tac-Toe.
 */
public class TicTacToe extends AbstractGame {
    private static final int BOARD_SIZE = 3;      // Size of the Tic-Tac-Toe board
    private char[][] mask = { { '-', '-', '-' }, { '-', '-', '-' }, { '-', '-', '-' } };  // Board mask to track moves
    private int countPlay = 0;  // Counter to track the number of plays made
    private JButton[][] buttons = new JButton[BOARD_SIZE][BOARD_SIZE];  // Buttons representing the game board

    /**
     * Constructor to initialize the TicTacToe game with players, panel, status label, and listener.
     *
     * @param player1 the first player
     * @param player2 the second player
     * @param panel   the game board panel
     * @param statusLabel the status label
     * @param gameEndListener the listener for handling game end events
     */
    public TicTacToe(Player player1, Player player2, JPanel panel, JLabel statusLabel, GameEndListener gameEndListener) {
        super(player1, player2, panel, statusLabel, gameEndListener);
    }

    /**
     * Sets up the game board when the game starts.
     * Creates buttons and adds action listeners for player moves.
     *
     * @param panel the game board panel
     */
    @Override
    public void onStartGame(JPanel panel) {
        panel.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE, 5, 5));

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                final int play = i * BOARD_SIZE + j;
                buttons[i][j] = new JButton("?");
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 32));

                buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handlePlayerMove(0,play);
                    }
                });

                panel.add(buttons[i][j]);
            }
        }
    }

    /**
     * Updates the board with the current player's move and disables the button after the move.
     *
     *  @param play_from the move start made by the player, NOT USED FOR THAT GAME!
     *  @param play_to the move end made by the player 
     * @param isPlayer1 true if it's Player 1's turn, false otherwise
     * @param panel the game board panel
     */
    @Override
    protected void moveAndPrintBoard(int play_from, int play, boolean isPlayer1, JPanel panel) {
        int row = play / BOARD_SIZE;
        int col = play % BOARD_SIZE;
        mask[row][col] = isPlayer1 ? 'X' : 'O';
        countPlay++;

        buttons[row][col].setText(String.valueOf(mask[row][col]));
        buttons[row][col].setEnabled(false); // Disable button after move
    }

    /**
     * Checks if a player has won the game.
     *
     * @param player1 true if checking for Player 1's win, false for Player 2
     * @return true if the player has won, false otherwise
     */
    @Override
    boolean isWinner(boolean player1) {
        return isWinner(player1 ? 'X' : 'O');
    }

    /**
     * Checks if the game has ended in a draw.
     *
     * @return true if the game is a draw, false otherwise
     */
    @Override
    boolean isNullMatch() {
        return countPlay == BOARD_SIZE * BOARD_SIZE;
    }

    /**
     * Checks if a move is valid.
     *
     *  @param play_from the move start made by the player, NOT USED FOR THAT GAME!
     *  @param play the move end made by the player 
     * @return true if the move is valid, false otherwise
     */
    @Override
    boolean isValidMove(int play_from, int play) {
        int row = play / BOARD_SIZE;
        int col = play % BOARD_SIZE;
        return mask[row][col] == '-';
    }

    /**
     * Checks if a given character forms a winning combination on the board.
     *
     * @param c the character ('X' or 'O') representing the player's move
     * @return true if the player has won, false otherwise
     */
    private boolean isWinner(char c) {
        // Check rows
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (mask[i][0] == c && mask[i][1] == c && mask[i][2] == c) {
                return true;
            }
        }

        // Check columns
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (mask[0][i] == c && mask[1][i] == c && mask[2][i] == c) {
                return true;
            }
        }

        // Check diagonals
        boolean isC = true;
        for (int i = 0; i < BOARD_SIZE; i++) {
            isC = (mask[i][i] == c) && isC;
        }
        if (isC) {
            return true;
        }

        isC = true;
        for (int i = 0; i < BOARD_SIZE; i++) {
            isC = (mask[BOARD_SIZE - i - 1][i] == c) && isC;
        }
        return isC;
    }

    /**
    * return score for player 1 and 2
    **/
    public int[] getScore(){
        if (isWinner('X')){
            return new int[]{countPlay/2,0};
        }  
        if (isWinner('O')){
            return new int[]{0,countPlay/2};
        } 
        return  new int[]{0,0};  
    }
    
}

