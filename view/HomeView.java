package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;

import java.util.List;
import java.util.Iterator;

import db.DbInterface;
import model.Player;
import model.Game;

import utils.AppState;
import navigation.NavigationController;

import view.BaseView;

/**
 * HomeView implements the view of the Home interface, allowing users to select a game
 * and view the top players.
 */
public class HomeView extends BaseView {

    // Dependencies and data models
    private DbInterface db; // Database used for fetching data
    private List<Game> games; // List of available games
    private Player[] topPlayers; // Array of top players
    private AppState appState; // Application state

    /**
     * Constructs the HomeView.
     *
     * @param _db the database used for fetching data
     * @param _appState the application state
     * @param _navigationController the navigation controller for managing view transitions
     */
    public HomeView(DbInterface _db, AppState _appState, NavigationController _navigationController) {
        super("HOME", _navigationController);
        this.db = _db;
        this.appState = _appState;
    }

    /**
     * Fills the list of available games by fetching from the database.
     */
    private void fillGames() { 
        games = db.getGames();
    }

    /**
     * Fills the array of top players by fetching from the database.
     */
    private void fillTopPlayers() { 
        List<Player> playerList = db.getTopPlayers();
        this.topPlayers = new Player[playerList.size()];
        playerList.toArray(this.topPlayers);
    }

    /**
     * Renders the HomeView interface, including game selection buttons and
     * a table displaying the top players.
     */
    @Override
    public void render() {
        this.fillGames();
        this.fillTopPlayers();

        // Set up the main frame
        this.setupMainFrame();

        // Now create a horizontal grid inside that frame with a left panel and a right one
        JPanel grid = new JPanel(new GridLayout(1, 2));
        this.mainFrame.add(grid);

        // Left panel
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        grid.add(leftPanel);

        JLabel text = new JLabel("Select Your Game \n And \n Start Playing");
        leftPanel.add(text);
        text.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Iterate over games and create buttons for each
        Iterator<Game> gameIterator = games.iterator();
        while (gameIterator.hasNext()) {
            Game game = gameIterator.next();
            JButton button = new JButton(game.getName().toLowerCase());
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("HomeView - Selected Game:" + game.dbgMeAsStr());
                        appState.setGame(game);
                        navigationController.navigateToPlayerSelectionView();
                    }
                });
            leftPanel.add(button);
        }

        // Right panel
        JPanel rightPanel = new JPanel();
        grid.add(rightPanel);

        // Data to be displayed in the JTable
        TableModel dataModel = new AbstractTableModel() {
            public int getColumnCount() {
                return 2;
            }

            public int getRowCount() {
                return topPlayers.length;
            }

            public Object getValueAt(int row, int col) {
                if (col == 0) {
                    return topPlayers[row].getNickName();
                }
                if (col == 1) {
                    return topPlayers[row].getScore();
                }
                return null;
            }
        };

        // Create and add the JTable to the right panel
        JTable scoresTable = new JTable(dataModel);
        rightPanel.add(scoresTable);

        // Make the frame visible
        this.mainFrame.setVisible(true);
    }
}
