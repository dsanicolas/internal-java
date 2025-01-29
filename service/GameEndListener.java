package service;

import model.Player;

/**
 * Interface for handling the end of a game.
 * This interface defines the method that must be implemented to handle game end events.
 */
public interface GameEndListener {
    /**
     * Method to be called when the game ends.
     *
     * @param winner the player who won the game, or null if it was a draw.
     */
    void onGameEnd(Player winner);
}

