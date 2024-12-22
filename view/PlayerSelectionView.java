package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import db.DbMockup;
import model.Game;
import model.Player;

import utils.AppState;
import navigation.NavigationController;

/**
 * PlayerSelectionView implements the view for selecting players.
 * Provides the interface for selecting two players and starting the game.
 */
public class PlayerSelectionView {

    private String title = "Player Selection"; // Title of the frame
    private JFrame mainFrame; // Main frame for the view
    private DbMockup db; // Database mockup for player operations
    private Player player1; // Player 1 object
    private Player player2; // Player 2 object
    private Player[] players; // Array of players fetched from the database
    private AppState appState; // Application state object
    private NavigationController navigationController; // Controller for managing view transitions

    /**
     * Constructor for PlayerSelectionView.
     * Initializes the database mockup and the navigation controller.
     *
     * @param _db The database mockup object.
     * @param _appState The application state object.
     * @param navigationController The navigation controller for managing view transitions.
     */
    public PlayerSelectionView(DbMockup _db, AppState _appState, NavigationController navigationController) {
        this.db = _db;
        this.appState = _appState;
        this.navigationController = navigationController;
    }

    public void setPlayer1(Player _plyr) {
        this.player1 = _plyr;
    }

    public void setPlayer2(Player _plyr) {
        this.player2 = _plyr;
    }

    /**
     * Fill the players array with players fetched from the database.
     */
    private void fillPlayers() {
        List<Player> plyrsList = db.getPlayers(""); // Fetch players from the database
        this.players = new Player[plyrsList.size()];
        plyrsList.toArray(this.players);
    }

    /**
     * Render the player selection view.
     * Creates and displays the GUI for selecting players.
     */
    public void render() {
        this.fillPlayers();

        // Destroy any previous frame
        this.destroyMainFrame();

        // Create the frame
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

        // Create a vertical grid inside the frame with a top panel and a bottom one
        JPanel grid = new JPanel(new GridLayout(2, 1));
        this.mainFrame.add(grid);

        // Top panel for player selection
        JPanel tpPnl = new JPanel();
        tpPnl.setLayout(new BoxLayout(tpPnl, BoxLayout.Y_AXIS));
        grid.add(tpPnl);

        // Button to start the game, initially disabled
        JButton startBtn = new JButton("START");
        startBtn.setEnabled(false);

        // Create player selectors for player 1 and player 2
        this.createPlayerSelector(true, tpPnl, startBtn);
        this.createPlayerSelector(false, tpPnl, startBtn);

        // Bottom panel for the start button
        JPanel bttmPnl = new JPanel();

        // Add listener to start button
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("PlayerSelectionView - Start with player 1 " + player1.dbgMeAsStr() + " and player2 " + player2.dbgMeAsStr());
                appState.setPlayer1(player1);
                appState.setPlayer2(player2);
                navigationController.navigateToGameView();
                destroyMainFrame();
            }
        });
        bttmPnl.add(startBtn);
        grid.add(bttmPnl);

        // Make the frame visible
        this.mainFrame.setVisible(true);
    }

    private void destroyMainFrame() {
        if (this.mainFrame != null) {
            this.mainFrame.setVisible(false);
            this.mainFrame.dispose();
        }
    }

    /**
     * Create a player selector for either player 1 or player 2.
     *
     * @param isPlayer1 True if this selector is for player 1, false for player 2.
     * @param pnl The panel to add this selector to.
     * @param startBtn The start button to enable/disable based on player selection.
     */
    private void createPlayerSelector(boolean isPlayer1, JPanel pnl, JButton startBtn) {
        // Create a horizontal panel for player, combo selector, new button
        JPanel plyrPnl = new JPanel();
        plyrPnl.setLayout(new BoxLayout(plyrPnl, BoxLayout.X_AXIS));

        // Create the label and add it to plyrPnl
        JLabel plyrTxt = new JLabel(isPlayer1 ? "Player 1" : "Player 2");
        plyrTxt.setAlignmentX(Component.CENTER_ALIGNMENT);
        plyrPnl.add(plyrTxt);

        // Create the selection combo and add it to plyrPnl
        JComboBox<Player> plyrCmb = new JComboBox<>(new DefaultComboBoxModel<>(this.players));
        plyrCmb.setPreferredSize(new Dimension(200, 25)); // Set standard size
        plyrCmb.setMaximumSize(new Dimension(200, 25)); // Ensure it doesn't resize beyond this
        plyrCmb.setAlignmentX(Component.CENTER_ALIGNMENT);
        plyrPnl.add(plyrCmb);

        // Set a renderer to decide which attributes of Player will be shown as title in the combo box (here Nickname)
        KeySelectionRenderer renderer = new KeySelectionRenderer(plyrCmb) {
            @Override
            public String getDisplayValue(Object value) {
                Player plyr = (Player) value;
                return plyr.getNickName();
            }
        };

        // Add a listener that catches the selection of the user, and assigns it to the adequate player
        // Also enables or disables the button based on whether the two players have been selected and are different
        plyrCmb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<Player> comboBox = (JComboBox<Player>) e.getSource();
                if (isPlayer1) {
                    player1 = (Player) comboBox.getSelectedItem();
                    System.out.println("PlayerSelectionView - Selected player 1:" + player1.dbgMeAsStr());
                } else {
                    player2 = (Player) comboBox.getSelectedItem();
                    System.out.println("PlayerSelectionView - Selected player 2:" + player2.dbgMeAsStr());
                }

                if (player1 != null && player2 != null && player1.getId() != player2.getId()) {
                    startBtn.setEnabled(true);
                } else {
                    startBtn.setEnabled(false);
                }
            }
        });

        // Set combo box default value
        this.setComboBoxToPlayer(plyrCmb, isPlayer1 ? this.player1 : this.player2);

        // Create the new and rename button and add it to plyrPnl
        JButton newPlyrBtn = new JButton("new");
        newPlyrBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton editPlyrBtn = new JButton("edit");
        newPlyrBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add listener to new player button
        newPlyrBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("PlayerSelectionView - New Player requested for " + (isPlayer1 ? "player1" : "player2"));
                navigationController.navigateToPlayerCreateView(isPlayer1);
            }
        });
        // Add listener to rename player button
        editPlyrBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("PlayerSelectionView - Edit Player requested for " + (isPlayer1 ? "player1" : "player2"));
                navigationController.navigateToPlayerEditView(isPlayer1 ? player1 : player2, isPlayer1);
            }
        });
        plyrPnl.add(newPlyrBtn);
        plyrPnl.add(editPlyrBtn);

        // Add the plyrPnl to the pnl
        pnl.add(plyrPnl);
    }

    private void setComboBoxToPlayer(JComboBox cmbo, Player plyr) {
        if (plyr == null) {
            cmbo.setSelectedIndex(0);
        } else {
            for (int i = 0; i < this.players.length; i++) {
                if (this.players[i].getId() == plyr.getId()) {
                    cmbo.setSelectedIndex(i);
                    break;
                }
            }
        }
    }
}
