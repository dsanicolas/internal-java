package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import model.Player;
import model.Game;

/**
 * ResultView implements the view of the match result.
 * Displays the final scores of the players and the winner.
 **/
public class ResultView {

    private String title = "Result"; // Title of the frame
    private JFrame mainFrame; // Main frame for the view

    private Player player1; // Player 1 object
    private Player player2; // Player 2 object
    private Game game; // Game object
    private Player winner; // Winner of the match
    private int matchScorePlayer1; // Final score of player 1
    private int matchScorePlayer2; // Final score of player 2

    /**
     * Constructor for ResultView.
     * Initializes the view with players, game, scores, and winner.
     *
     * Preconditions:
     * - player1, player2, game must not be null.
     * - matchScorePlayer1 and matchScorePlayer2 are the end scores for the match.
     * - winner is null if the match is null, else it's the winner.
     *
     * @param _player1 The first player.
     * @param _player2 The second player.
     * @param _game The game object.
     * @param _matchScorePlayer1 The final score of player 1.
     * @param _matchScorePlayer2 The final score of player 2.
     * @param _winner The winner of the match (null if no winner).
     */
    public ResultView(Player _player1, Player _player2, Game _game, int _matchScorePlayer1, int _matchScorePlayer2, Player _winner) {
        this.player1 = _player1;
        this.player2 = _player2;
        this.game = _game;
        this.winner = _winner;
        this.matchScorePlayer1 = _matchScorePlayer1;
        this.matchScorePlayer2 = _matchScorePlayer2;
    }

    /**
     * Render the result view.
     * Creates and displays the GUI for showing the match results.
     */
    public void render() {
        this.destroyMainFrame();

        this.mainFrame = new JFrame(this.title);
        this.mainFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                destroyMainFrame();
            }
        });
        this.mainFrame.setSize(500, 300);
        this.mainFrame.setLocationRelativeTo(null);

        JPanel grid = new JPanel(new GridLayout(2, 1));
        this.mainFrame.add(grid);

        JPanel tpPnl = new JPanel();
        tpPnl.setLayout(new BoxLayout(tpPnl, BoxLayout.Y_AXIS));
        this.renderSomeText(tpPnl, this.winner == null ? "NULL MATCH :-( " : (this.winner.getNickName() + " WON !!"));
        this.renderSomeText(tpPnl, this.player1.getNickName() + " " + this.matchScorePlayer1 + "-" + this.matchScorePlayer2 + " " + this.player2.getNickName());
        grid.add(tpPnl);

        JPanel bttmPnl = new JPanel();
        bttmPnl.setLayout(new BoxLayout(bttmPnl, BoxLayout.Y_AXIS));
        this.renderPlayerScore(bttmPnl, this.player1);
        this.renderPlayerScore(bttmPnl, this.player2);
        grid.add(bttmPnl);

        JButton homeBtn = new JButton("Home");
        homeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Handle navigation to home view
                destroyMainFrame();
            }
        });
        bttmPnl.add(homeBtn);

        this.mainFrame.setVisible(true);
    }

    private void destroyMainFrame() {
        if (this.mainFrame != null) {
            this.mainFrame.setVisible(false);
            this.mainFrame.dispose();
        }
    }

    private void renderSomeText(JPanel _pnl, String _someText) {
        JLabel inrTxt = new JLabel(_someText);
        inrTxt.setAlignmentX(Component.CENTER_ALIGNMENT);
        _pnl.add(inrTxt);
    }

    private void renderPlayerScore(JPanel _pnl, Player player) {
        this.renderSomeText(_pnl, player.getNickName() + " - " + player.getScore());
    }
}
