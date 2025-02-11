package service;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Player;

/**
 * Concrete implementation of the AbstractGame class for the Checkers game.
 * This class implements the specific game logic for Checkers.
 */
public class Checkers extends AbstractGame {
    private static final int BOARD_SIZE = 8;  // Size of the Checkers board
    private char[][] board = new char[BOARD_SIZE][BOARD_SIZE];  // Board to track moves
    private JButton[][] buttons = new JButton[BOARD_SIZE][BOARD_SIZE];  // Buttons representing the game board
    private int selectedPlayFrom = -1; // the origin of the next move
    private int player1Captures = 0;  // Count of captures for Player 1
    private int player2Captures = 0;  // Count of captures for Player 2

    /**
     * Constructor to initialize the Checkers game with players, panel, status label, and listener.
     *
     * @param player1 the first player
     * @param player2 the second player
     * @param panel   the game board panel
     * @param statusLabel the status label
     * @param gameEndListener the listener for handling game end events
     */
    public Checkers(Player player1, Player player2, JPanel panel, JLabel statusLabel, GameEndListener gameEndListener) {
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
                if ((i + j) % 2 != 0) {
                    if (i < 3) {
                        board[i][j] = 'X';  // Player 1's pieces
                    } else if (i > 4) {
                        board[i][j] = 'O';  // Player 2's pieces
                    } else {
                        board[i][j] = '-';  // Empty spaces
                    }
                } else {
                    board[i][j] = ' ';  // Non-playable spaces
                }

                final int position = i * BOARD_SIZE + j;
                buttons[i][j] = new JButton(String.valueOf(board[i][j]));
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 32));

                buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handlePlayerMove(position);
                    }
                });

                panel.add(buttons[i][j]);
            }
        }
    }

    
     /**
     * Handles a player's click on a cell
     *
     * @param position clicked by the player
     */
    private void handlePlayerMove(int position) {
        if (selectedPlayFrom == -1) {
            selectedPlayFrom = position;
        } else {
            int play_to = position;
            super.handlePlayerMove(selectedPlayFrom, play_to);
            selectedPlayFrom = -1;  // Reset after move
        }
    }


    /**
     * Updates the board with the current player's move and handles captures.
     *
     * @param play_from the move start made by the player
     * @param play_to the move end made by the player
     * @param isPlayer1 true if it's Player 1's turn, false otherwise
     * @param panel the game board panel
     */
    @Override
    protected void moveAndPrintBoard(int play_from, int play_to, boolean isPlayer1, JPanel panel) {
        int fromRow = play_from / BOARD_SIZE;
        int fromCol = play_from % BOARD_SIZE;
        int toRow = play_to / BOARD_SIZE;
        int toCol = play_to % BOARD_SIZE;

        // Handle captures
        if (Math.abs(fromRow - toRow) == 2 && Math.abs(fromCol - toCol) == 2) {
            int midRow = (fromRow + toRow) / 2;
            int midCol = (fromCol + toCol) / 2;
            board[midRow][midCol] = '-';
            buttons[midRow][midCol].setText(String.valueOf(board[midRow][midCol]));

            if (isPlayer1) {
                player1Captures++;
            } else {
                player2Captures++;
            }
        }

        board[toRow][toCol] = board[fromRow][fromCol];
        board[fromRow][fromCol] = '-';

        buttons[toRow][toCol].setText(String.valueOf(board[toRow][toCol]));
        buttons[fromRow][fromCol].setText(String.valueOf(board[fromRow][fromCol]));
    }
   
    
    /**
     * Checks if a player has won the game.
     *
     * @param player1 true if checking for Player 1's win, false for Player 2
     * @return true if the player has won, false otherwise
     */
    @Override
    boolean isWinner(boolean player1) {
        char opponentPiece = player1 ? 'O' : 'X';
        // Check if all opponent's pieces are captured
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == opponentPiece) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the game has ended in a draw.
     *
     * @return true if the game is a draw, false otherwise
     */
    @Override
    boolean isNullMatch() {
        // Check if no valid moves are available for both players
        return !hasValidMoves('X') && !hasValidMoves('O');
    }

    /**
     * Checks if a move is valid.
     *
     * @param play_from the move start made by the player
     * @param play_to the move end made by the player
     * @return true if the move is valid, false otherwise
     */
    @Override
    boolean isValidMove(int play_from, int play_to) {
        int fromRow = play_from / BOARD_SIZE;
        int fromCol = play_from % BOARD_SIZE;
        int toRow = play_to / BOARD_SIZE;
        int toCol = play_to % BOARD_SIZE;

        if (board[toRow][toCol] != '-') {
            return false;
        }

        int direction = (board[fromRow][fromCol] == 'X') ? 1 : -1;
        if (Math.abs(fromRow - toRow) == 1 && Math.abs(fromCol - toCol) == 1) {
            return toRow - fromRow == direction;
        }

        if (Math.abs(fromRow - toRow) == 2 && Math.abs(fromCol - toCol) == 2) {
            int midRow = (fromRow + toRow) / 2;
            int midCol = (fromCol + toCol) / 2;
            return board[midRow][midCol] == (board[fromRow][fromCol] == 'X' ? 'O' : 'X');
        }

        return false;
    }


    /**
     * Checks if there are any valid moves left for a player.
     *
     * @param piece the player's piece ('X' or 'O')
     * @return true if there are valid moves, false otherwise
     */
    private boolean hasValidMoves(char piece) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == piece) {
                    if (canMove(i, j, piece)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * Checks if a piece can move to a valid position.
     *
     * @param row the row of the piece
     * @param col the column of the piece
     * @param piece the player's piece ('X' or 'O')
     * @return true if the piece can move, false otherwise
     */
    private boolean canMove(int row, int col, char piece) {
        int direction = (piece == 'X') ? 1 : -1;
        // Check diagonal moves
        if (isValidPosition(row + direction, col - 1) && board[row + direction][col - 1] == '-') {
            return true;
        }
        if (isValidPosition(row + direction, col + 1) && board[row + direction][col + 1] == '-') {
            return true;
        }
        // Check captures
        if (isValidPosition(row + 2 * direction, col - 2) && board[row + direction][col - 1] == (piece == 'X' ? 'O' : 'X') && board[row + 2 * direction][col - 2] == '-') {
            return true;
        }
        if (isValidPosition(row + 2 * direction, col + 2) && board[row + direction][col + 1] == (piece == 'X' ? 'O' : 'X') && board[row + 2 * direction][col + 2] == '-') {
            return true;
        }
        return false;
    }

    /**
     * Checks if a position is within the bounds of the board.
     *
     * @param row the row of the position
     * @param col the column of the position
     * @return true if the position is valid, false otherwise
     */
    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE;
    }
}
